package top.onehundred.android.oneapi;

public interface IOneAPI {

    void setTest(boolean test);

    void setDebug(boolean debug);

    void setTimeout(int timeout);

    void get(APIListener listener);

    void post(APIListener listener);

    void delete(APIListener listener);

    void put(APIListener listener);

    void cancel();

}
