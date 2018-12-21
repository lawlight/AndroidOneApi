package top.onehundred.android.onekit.kits;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;

/**
 * 系统应用跳转工具包
 */
public class IntentKit {

    Context context;

    private static IntentKit intentKit;
    public static IntentKit getInstance(Context context){
        if(intentKit == null){
            synchronized (IntentKit.class){
                if(intentKit == null){
                    intentKit = new IntentKit();
                }
            }
        }
        intentKit.context = context;
        return intentKit;
    }

    /**
     * 显示网页
     *
     * @param url 网页url
     */
    public void showWebPage(String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 显示地图
     *
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void showMap(double longitude, double latitude) {
        Uri uri = Uri.parse("geo:" + longitude + "," + latitude);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }

    /**
     * 拨打电话（直接拨打）<br/>
     * 需要android.permission.CALL_PHONE权限，建议使用showPhoneCall，只显示拨号界面，不直接拨打
     *
     * @param telNum 电话号码
     */
    public void makePhoneCall(String telNum) {
        Uri uri = Uri.parse("tel:" + telNum);
        Intent it = new Intent(Intent.ACTION_CALL, uri);
        context.startActivity(it);
    }

    /**
     * 调用拨号程序
     *
     * @param telNum 电话号码
     */
    public void showPhoneCall(String telNum) {
        Uri uri = Uri.parse("tel:" + telNum);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }

    /**
     * 发送短信（打开短信应用）
     *
     * @param text 短信内容
     */
    public void sendSMS(String text) {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.putExtra("sms_body", text);
        it.setType("vnd.android-dir/mms-sms");
        context.startActivity(it);
    }

    /**
     * 发送短信
     *
     * @param receiver 收信人
     * @param text     短信内容
     */
    public void sendSMS(String receiver, String text) {
        Uri uri = Uri.parse("smsto:" + receiver);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", text);
        context.startActivity(it);
    }

    /**
     * 发送电子邮件（调用系统邮件应用）
     *
     * @param reciver 收件人
     * @param subject 主题
     * @param body    正文
     */
    public void sendEMail(String reciver, String subject, String body) {
        String[] recivers = new String[]{reciver};
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("plain/text");
        myIntent.putExtra(Intent.EXTRA_EMAIL, recivers);
        myIntent.putExtra(Intent.EXTRA_CC, "");
        myIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        myIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(myIntent);
    }

    /**
     * 显示联系人应用
     */
    public void showContacts() {
        Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        context.startActivity(intent);
    }

    /**
     * 跳转到系统设置界面
     */
    public void showSettings(){
        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    /**
     * 跳转到微信
     */
    public void toWeChat() throws ActivityNotFoundException{
        Intent intent = new Intent(Intent.ACTION_MAIN);
        ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        context.startActivity(intent);
    }
}
