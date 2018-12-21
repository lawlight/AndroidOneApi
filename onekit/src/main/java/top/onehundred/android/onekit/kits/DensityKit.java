package top.onehundred.android.onekit.kits;

import android.content.Context;
import android.util.TypedValue;

import top.onehundred.android.onekit.ok;

/**
 * 像素密度工具类
 */
public class DensityKit {

    private static DensityKit densityKit;

    public static DensityKit getInstance(){
        if(densityKit == null){
            synchronized (DensityKit.class){
                if(densityKit == null){
                    densityKit = new DensityKit();
                }
            }
        }
        return densityKit;
    }

    /**
     * 获取dp密度值
     * @return
     */
    public float getDensity(){
        return ok.app().getResources().getDisplayMetrics().density;
    }

    /**
     * 获取字体sp密度值
     * @return
     */
    public float getScaleDensity(){
        return ok.app().getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * dp转px
     *
     * @param dpVal dp值
     * @return px值
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, ok.app().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal sp值
     * @return px值
     */
    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, ok.app().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal px值
     * @return dp值
     */
    public float px2dp(float pxVal) {
        return (pxVal / getDensity());
    }

    /**
     * px转sp
     *
     * @param pxVal px值
     * @return sp值
     */
    public float px2sp(float pxVal) {
        return (pxVal / getScaleDensity());
    }
}
