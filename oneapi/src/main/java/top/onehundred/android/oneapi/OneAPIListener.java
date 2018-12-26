package top.onehundred.android.oneapi;

public interface OneAPIListener {

    void onSuccess();

    void onError(int code, String message);

}
