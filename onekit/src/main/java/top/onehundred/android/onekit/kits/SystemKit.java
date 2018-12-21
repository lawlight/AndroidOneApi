package top.onehundred.android.onekit.kits;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;

import java.util.Locale;
import java.util.TimeZone;

import top.onehundred.android.onekit.ok;

/**
 * 系统信息工具类
 */
public class SystemKit {

    private static SystemKit systemKit;
    public static SystemKit getInstance(){
        if(systemKit == null){
            synchronized (SystemKit.class){
                if(systemKit == null){
                    systemKit = new SystemKit();
                }
            }
        }
        return systemKit;
    }

    /**
     * get android phone system version
     *
     * @return phone system version
     */
    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * get phone model
     *
     * @return phone model
     */
    public String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * get phone brand
     *
     * @return phone brand
     */
    public String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * get android id
     * @return
     */
    public String getAndroidId() {
        return Settings.Secure.getString(ok.app().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * get serial
     * @return
     */
    public String getSerial(){
        return android.os.Build.SERIAL;
    }

    /**
     * get system language code
     * @return
     */
    public String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * get local country code
     * @return
     */
    public String getCountry(){
        Resources res = ok.app().getResources();
        Configuration config = res.getConfiguration();
        return config.locale.getCountry();
    }

    /**
     * 获取当前时区
     * @return
     */
    public String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;

    }

}
