package top.onehundred.android.onekit.kits;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

public class DialogKit {

    private static DialogKit dialogKit;

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private Toast mToast;

    public static DialogKit getInstance(Context context) {
        if (dialogKit == null) {
            synchronized (DialogKit.class){
                if(dialogKit == null){
                    dialogKit = new DialogKit();
                }
            }
        }
        dialogKit.mContext = context;
        return dialogKit;
    }

    /**
     * 显示简单的确认对话框，按钮无回调
     *
     * @param title 标题
     * @param text  文本
     */
    public void showCommitDialog(String title, String text) {
        if (mContext == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(text).setPositiveButton("确定", null).show();
    }

    /**
     * 显示简单的确认对话框，按钮点击后关闭activity
     *
     * @param title 标题
     * @param text  文本
     */
    public void showCommitDialogWithFinish(String title, String text) {
        if (mContext == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title).setMessage(text).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mContext instanceof Activity) {
                    ((Activity) mContext).finish();
                }
            }
        }).show();
    }

    /**
     * 显示短时间的Toast
     *
     * @param text 文本
     */
    public void showShortToast(String text) {
        if (mContext == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示进度对话框
     *
     * @param message 消息
     */
    public void showProgressDialog(String message) {
        showProgressDialog("", message, false);
    }

    /**
     * 显示进度对话框
     *
     * @param title      标题
     * @param message    消息
     * @param cancelable 是否可以取消
     */
    public void showProgressDialog(String title, String message, boolean cancelable) {
        if (mContext == null) {
            return;
        }
        dismissProgressDialog();
        mProgressDialog = ProgressDialog.show(mContext, title, message);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setCanceledOnTouchOutside(cancelable);
    }

    /**
     * 关闭进度对话框
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
