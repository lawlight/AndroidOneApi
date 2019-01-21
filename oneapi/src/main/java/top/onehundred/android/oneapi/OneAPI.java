package top.onehundred.android.oneapi;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class OneAPI implements IOneAPI, APIListener {

    public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    private static final int TIMEOUT = 10;

    private static final int CODE_ERROR = -1;

    private static OkHttpClient mOkHttpClient;
    private Call call;

    private APIListener listener;

    private boolean isTest = false;

    private boolean isDebug = true;

    private boolean isCancel = false;

    private int timeout = TIMEOUT;


    private boolean isMultipartForm;

    private String mediaType;
    private String raw;

    //参数
    private HashMap<String, Object> mParams = new HashMap<>();
    private HashMap<String, String> mHeaders = new HashMap<>();

    public OneAPI() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
    }

    @Override
    public void setTest(boolean test) {
        this.isTest = test;
    }

    @Override
    public void setDebug(boolean debug) {
        this.isDebug = debug;
    }

    protected abstract String getHostname();

    protected abstract String getUrl();

    protected abstract void putInputs();

    public void putHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    protected void putField(String key, Object value) {
        mParams.put(key, value);
        if (value instanceof File) {
            isMultipartForm = true;
        }
    }

    protected void putFields(Map fields){
        mParams.putAll(fields);
    }

    /**
     *
     * @param mediaType text/plain
     *                  application/json
     *                  application/javascript
     *                  application/xml
     *                  text/
     * @param raw
     */
    protected void putRaw(String mediaType, String raw) {
        this.mediaType = mediaType;
        this.raw = raw;
    }

    @Override
    public void get(APIListener listener) {
        if (doStart(listener)) {
            Request request = new Request.Builder()
                    .url(getGetUrl())
                    .get()
                    .build();
            call(request);
        }
    }

    @Override
    public void post(APIListener listener) {
        if (doStart(listener)) {
            Request.Builder builder = new Request.Builder();
            builder.tag(new Date().getTime());
            builder.url(getHostname() + getUrl());
            builder.headers(getHeaders());
            if (isMultipartForm) {
                builder.post(getMultipartBody());
            } else if (raw != null && mediaType != null) {
                builder.post(getRawBody());
            } else {
                builder.post(getRequestBody());
            }
            call(builder.build());
        }
    }

    @Override
    public void delete(APIListener listener) {
        if (doStart(listener)) {
            Request.Builder builder = new Request.Builder();
            builder.url(getHostname() + getUrl());
            builder.headers(getHeaders());
            FormBody rb = (FormBody) getRequestBody();
            if (rb.size() == 0) {
                builder.delete();
            } else {
                builder.delete(rb);
            }
            call(builder.build());
        }
    }

    @Override
    public void put(APIListener listener) {
        if (doStart(listener)) {
            Request request = new Request.Builder()
                    .url(getHostname() + getUrl())
                    .headers(getHeaders())
                    .put(getRequestBody())
                    .build();
            call(request);
        }
    }

    /**
     * 开始连接的一些准备工作
     *
     * @param listener
     */
    private boolean doStart(APIListener listener) {
        this.listener = listener;
        this.isCancel = false;
        this.isMultipartForm = false;

        mHeaders.clear();
        mParams.clear();

        try {
            //加载入参
            log("inputs:");
            putInputs();
        } catch (Exception e) {
            e.printStackTrace();
            onError(CODE_ERROR, "putInputs Exception:" + e.getMessage());
            return false;
        }
        if (isTest) {
            //测试方法
            log("test mode");
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        parseOutput(null);
                        onSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(CODE_ERROR, "test mode parseOutput exception：" + e.getMessage());
                    }
                }
            }.start();
            return false;
        }
        return true;
    }

    @Override
    public void cancel() {
        log("cancel request!");
        this.isCancel = true;
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
        mOkHttpClient.newBuilder().connectTimeout(timeout, TimeUnit.SECONDS);
    }

    protected String filterOutput(String output) throws Exception {
        return output;
    }

    protected abstract void parseOutput(String output) throws Exception;

    private String getGetUrl() {
        String paramsStr = "";
        Iterator iter = mParams.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if (entry.getValue() == null) {
                continue;
            }
            String val = entry.getValue().toString();
            paramsStr += key + "=" + val + (iter.hasNext() ? "&" : "");
        }
        String url = getHostname() + getUrl();
        if (!"".equals(paramsStr)) {
            if (url.contains("?")) {
                url = url + "&" + paramsStr;
            } else {
                url = url + "?" + paramsStr;
            }
        }
        return url;
    }

    private RequestBody getRequestBody() {
        log("fields:");
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter = mParams.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if (entry.getValue() == null) {
                continue;
            }
            String val = entry.getValue().toString();
            builder.add(key, val);
            log(key + ":" + val.toString());
        }
        return builder.build();
    }

    private RequestBody getMultipartBody() {
        log("parts:");
        Iterator iter = mParams.entrySet().iterator();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            Object val = entry.getValue();
            if (val == null) {
                continue;
            }
            if (val instanceof File) {
                //文件字段
                File file = new File(val.toString());
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                builder.addFormDataPart(key, file.getName(), fileBody);
            } else {
                //普通字段
                builder.addFormDataPart(key, val.toString());
            }
            log(key + ":" + val.toString());
        }
        return builder.build();
    }

    private RequestBody getRawBody() {
        MediaType mt = MediaType.parse(mediaType);
        RequestBody body = RequestBody.create(mt, raw);
        log("raw:" + raw);
        return body;
    }

    /**
     * 获取请求头
     *
     * @return
     */
    private Headers getHeaders() {
        log("headers:");
        Headers.Builder builder = new Headers.Builder();
        Iterator iter = mHeaders.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            if (entry.getValue() == null) {
                continue;
            }
            String val = entry.getValue().toString();
            builder.add(key, val);
            log(key + ":" + val.toString());
        }
        return builder.build();
    }

    private void call(Request request) {
        log("开始请求：" + request.toString());
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(CODE_ERROR, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String output = response.body().string();
                log("请求返回：" + output);
                try {
                    output = filterOutput(output);
                    parseOutput(output);
                    onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(CODE_ERROR, e.getMessage());
                }
            }
        });

    }

    /**
     * 输出log
     *
     * @param log
     */
    private void log(String log) {
        if (isDebug) {
            Log.d("OneApi", log);
        }
    }

    @Override
    public void onSuccess() {
        if (!isCancel) {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onError(int code, String message) {
        isCancel = true;
        handler.obtainMessage(0, code, 0, message).sendToTarget();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (listener == null) {
                return;
            }
            if (msg.what == 1) {
                listener.onSuccess();
            } else {
                String errorMessage = msg.obj == null ? "" : msg.obj.toString();
                listener.onError(msg.arg1, errorMessage);
            }
        }
    };
}
