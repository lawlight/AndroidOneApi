package top.onehundred.android.onekit.demo.activities;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import top.onehundred.android.onekit.demo.R;
import top.onehundred.android.onekit.ok;

public class IntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        ButterKnife.bind(this);
        setTitle("IntentKit");
    }

    @OnClick({R.id.btn_web, R.id.btn_map, R.id.btn_phone, R.id.btn_sms, R.id.btn_email, R.id.btn_contact, R.id.btn_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_web:
                ok.intentKit(this).showWebPage("http://www.baidu.com");
                break;
            case R.id.btn_map:
                ok.intentKit(this).showMap(120.19, 36.04);
                break;
            case R.id.btn_phone:
                ok.intentKit(this).showPhoneCall("18661938928");
                break;
            case R.id.btn_sms:
                ok.intentKit(this).sendSMS("13625327263", "你好，短信测试~");
                break;
            case R.id.btn_email:
                ok.intentKit(this).sendEMail("278253968@qq.com", "邮件测试", "你好，邮件测试");
                break;
            case R.id.btn_contact:
                ok.intentKit(this).showContacts();
                break;
            case R.id.btn_wechat:
                try{
                    ok.intentKit(this).toWeChat();
                }catch (ActivityNotFoundException ex){
                    ok.dialogKit(this).showShortToast("未安装微信");
                }
                break;
        }
    }
}
