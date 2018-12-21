package top.onehundred.android.onekit;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import top.onehundred.android.onekit.kits.AppKit;
import top.onehundred.android.onekit.kits.ColorKit;
import top.onehundred.android.onekit.kits.DatetimeKit;
import top.onehundred.android.onekit.kits.DensityKit;
import top.onehundred.android.onekit.kits.DialogKit;
import top.onehundred.android.onekit.kits.EncryptKit;
import top.onehundred.android.onekit.kits.IntentKit;
import top.onehundred.android.onekit.kits.ListKit;
import top.onehundred.android.onekit.kits.PhoneStateKit;
import top.onehundred.android.onekit.kits.ScreenKit;
import top.onehundred.android.onekit.kits.StringKit;
import top.onehundred.android.onekit.kits.SystemKit;
import top.onehundred.android.onekit.kits.ViewKit;

/**
 * onekit 工具库的导航类，可以从此类调用，也可以单独使用每个工具包
 */
public class ok {

    private static Application mApplication;

    /**
     * 初始化，使用前必须调用
     * @param application
     */
    public static void init(Application application){
        mApplication = application;
    }

    /**
     * 获取当前Application
     * @return
     */
    public static Application app(){
        return mApplication;
    }

    /**
     * App信息工具包
     * @return
     */
    public static AppKit appKit() {
        return AppKit.getInstance();
    }

    /**
     * 颜色工具包
     * @return
     */
    public static ColorKit colorKit() { return ColorKit.getInstance(); }

    /**
     * 日期时间工具包
     * @return
     */
    public static DatetimeKit datetimeKit() {
        return DatetimeKit.getInstance();
    }

    /**
     * 像素密度工具包
     * @return
     */
    public static DensityKit densityKit() {
        return DensityKit.getInstance();
    }

    /**
     * 对话框工具包
     * @param context
     * @return
     */
    public static DialogKit dialogKit(Context context) {
        return DialogKit.getInstance(context);
    }

    /**
     * 加密工具包
     * @return
     */
    public static EncryptKit encryptKit() {
        return EncryptKit.getInstance();
    }

    /**
     * 系统应用工具包
     * @param context
     * @return
     */
    public static IntentKit intentKit(Context context){
        return IntentKit.getInstance(context);
    }

    /**
     * 列表操作工具包
     * @return
     */
    public static ListKit listKit(){
        return ListKit.getInstance();
    }

    /**
     * 手机状态工具包
     * @return
     */
    public static PhoneStateKit phoneStateKit() {
        return PhoneStateKit.getInstance();
    }

    /**
     * 屏幕工具包
     * @return
     */
    public static ScreenKit screenKit() {
        return ScreenKit.getInstance();
    }
    /**
     * 字符串工具包
     * @return
     */
    public static StringKit stringKit(){
        return StringKit.getInstance();
    }

    /**
     * 系统信息工具包
     * @return
     */
    public static SystemKit systemKit() {
        return SystemKit.getInstance();
    }

    /**
     * 视图工具包
     * @return
     */
    public static ViewKit viewKit(){
        return ViewKit.getInstance();
    }


}
