package top.onehundred.android.oneapi.demo;

import android.app.Application;

import top.onehundred.android.oneapi.OneAPI;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneAPI.init(this);
    }
}
