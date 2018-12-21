package top.onehundred.android.onekit.kits;

import android.content.SyncContext;
import android.text.TextUtils;

/**
 * 字符串工具类
 */
public class StringKit {

    private static StringKit stringKit;
    public static StringKit getInstance(){
        if(stringKit == null){
            synchronized (StringKit.class){
                if(stringKit == null){
                    stringKit = new StringKit();
                }
            }
        }
        return stringKit;
    }

    /**
     * 字符串是否为空或null
     * @param text
     * @return
     */
    public boolean isEmpty(String text){
        return TextUtils.isEmpty(text);
    }

    /**
     * 字符串是否不为空或null
     * @param text
     * @return
     */
    public boolean notEmpty(String text){
        return !isEmpty(text);
    }
}
