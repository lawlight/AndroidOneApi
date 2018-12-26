package top.onehundred.android.oneapi;

import android.content.Context;

public interface IOneAPI {

    Context getContext();

    void setTest(boolean test);

    void setDebug(boolean debug);

    String getHostname();

    String getUrl();

    void putInputs() throws Exception;

    void putHeader(String key, String value);

    void putParam(String key, Object value);

    /**
     *
     * @param mediaType text/plain
     *                  application/json
     *                  application/javascript
     *                  application/xml
     *                  text/
     * @param raw
     */
    void putRaw(String mediaType, String raw);

    void get(OneAPIListener listener);

    void post(OneAPIListener listener);

    void delete(OneAPIListener listener);

    void put(OneAPIListener listener);

    void cancel();

    void setCacheTime(int cacheTime);

    void clearCache();

    String filterOutput(String output) throws Exception;

    void parseOutput(String output) throws Exception;

}
