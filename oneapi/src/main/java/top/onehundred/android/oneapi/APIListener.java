package top.onehundred.android.oneapi;

public interface APIListener {

    void onSuccess();

    void onError(int code, String message);

}
