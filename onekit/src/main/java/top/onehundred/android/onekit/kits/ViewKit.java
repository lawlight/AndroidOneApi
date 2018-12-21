package top.onehundred.android.onekit.kits;

/**
 * 视图工具类
 */
public class ViewKit {

    private static ViewKit viewKit;
    public static ViewKit getInstance() {
        if(viewKit == null){
            synchronized (ViewKit.class){
                if(viewKit == null){
                    viewKit = new ViewKit();
                }
            }
        }
        return viewKit;
    }
}
