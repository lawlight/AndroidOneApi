package top.onehundred.android.oneapi;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
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
import top.onehundred.android.oneapi.utils.ACache;

public class OneAPI implements IOneAPI, OneAPIListener {

    public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    private static final int TIMEOUT = 10;

    private static final int CODE_ERROR = -1;

    private static Context context;

    private static OkHttpClient mOkHttpClient;
    private Call call;

    private OneAPIListener listener;

    private boolean isTest = false;

    private boolean isDebug = true;

    private boolean isCancel = false;

    private String cacheMark;
    private int cacheTime = 0;

    private boolean isMultipartForm;

    private String mediaType;
    private String raw;

    //参数
    private HashMap<String, Object> mParams = new HashMap<>();
    private HashMap<String, String> mHeaders = new HashMap<>();

    public OneAPI() {
        this.context = context;
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    public static void init(Application application) {
        OneAPI.context = application;
    }

    @Override
    public void setTest(boolean test) {
        this.isTest = test;
    }

    @Override
    public void setDebug(boolean debug) {
        this.isDebug = debug;
    }

    @Override
    public String getHostname() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void putInputs() throws Exception {

    }

    @Override
    public void putHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    @Override
    public void putParam(String key, Object value) {
        mParams.put(key, value);
        if (value instanceof File) {
            isMultipartForm = true;
        }
    }

    @Override
    public void putRaw(String mediaType, String raw) {
        this.mediaType = mediaType;
        this.raw = raw;
    }

    @Override
    public void get(OneAPIListener listener) {
        if (doStart(listener)) {
            Request request = new Request.Builder()
                    .url(getGetUrl())
                    .get()
                    .build();
            //尝试从缓存中读取
            if (cacheMark != null) {
                String data = ACache.get(getContext()).getAsString(cacheMark);
                if (data != null) {
                    log("read from cache: " + data);
                    try {
                        parseOutput(data);
                        onSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError(CODE_ERROR, "cache parseOutput exception：" + e.getMessage());
                    }
                    return;
                }
            }
            call(request);
        }
    }

    @Override
    public void post(OneAPIListener listener) {
        if (doStart(listener)) {
            Request.Builder builder = new Request.Builder();
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
    public void delete(OneAPIListener listener) {
        if (doStart(listener)) {
            Request.Builder builder = new Request.Builder();
            builder.url(getHostname() + getUrl());
            builder.headers(getHeaders());
            FormBody rb = getRequestBody();
            if (rb.size() == 0) {
                builder.delete();
            } else {
                builder.delete(rb);
            }
            call(builder.build());
        }
    }

    @Override
    public void put(OneAPIListener listener) {
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
    private boolean doStart(OneAPIListener listener) {
        this.listener = listener;
        this.isCancel = false;
        this.isMultipartForm = false;

        this.cacheMark = null;

        mHeaders.clear();
        mParams.clear();

        try {
            //加载入参
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
        this.isCancel = true;
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public void clearCache() {
        ACache aCache = ACache.get(getContext());
        aCache.remove(this.getClass().getName());
    }

    @Override
    public String filterOutput(String output) throws Exception {
        return output;
    }

    @Override
    public void parseOutput(String output) throws Exception {

    }

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
        //以url为缓存的标志
        if (cacheTime > 0) {
            cacheMark = url;
        }
        return url;
    }

    private FormBody getRequestBody() {
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
        return body;
    }

    /**
     * 获取请求头
     *
     * @return
     */
    private Headers getHeaders() {
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
        }
        return builder.build();
    }

    private void call(Request request) {
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(CODE_ERROR, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String output = response.body().string();
                try {
                    output = filterOutput(output);
                    if (cacheMark != null) {
                        log("save cache for " + cacheTime + " second!");
                        ACache.get(getContext()).put(cacheMark, output, cacheTime);
                    }
                    parseOutput(output);
                    onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
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
        handler.obtainMessage(0, code, 0, message);
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
