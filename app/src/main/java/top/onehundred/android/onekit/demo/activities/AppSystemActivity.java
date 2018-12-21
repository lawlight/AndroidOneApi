package top.onehundred.android.onekit.demo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.onehundred.android.onekit.demo.R;
import top.onehundred.android.onekit.kits.PhoneStateKit;
import top.onehundred.android.onekit.ok;

public class AppSystemActivity extends AppCompatActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_system);
        ButterKnife.bind(this);
        setTitle("AppKit & SystemKit");

        tvInfo.append("app名称: " + ok.appKit().getAppName() + "\n");
        tvInfo.append("app版本: " + ok.appKit().getVersionName() + "(build " + ok.appKit().getVersionCode() + ")\n");
        tvInfo.append("包名: " + ok.appKit().getPackageName() + "\n\n");

        tvInfo.append("设备品牌: " + ok.systemKit().getDeviceBrand() + "\n");
        tvInfo.append("设备型号:" + ok.systemKit().getDeviceModel() + "\n");
        tvInfo.append("系统版本: " + ok.systemKit().getSystemVersion() + "\n\n");
        tvInfo.append("android id: " + ok.systemKit().getAndroidId() + "\n");
        tvInfo.append("serial: " + ok.systemKit().getSerial() + "\n");

        tvInfo.append("语言: " + ok.systemKit().getLanguage() + "\n");
        tvInfo.append("地区: " + ok.systemKit().getCountry() + "\n\n");

        tvInfo.append("网络状态: " + (ok.phoneStateKit().isConnected() ? "网络已连接，" : "网络未连接，") + (ok.phoneStateKit().isWifi() ? "wifi连接。" : "无wifi连接。") + "\n\n");

        tvInfo.append("屏幕分辨率：" + ok.screenKit().getScreenWidth() + " x " + ok.screenKit().getScreenHeight() + "\n");
        tvInfo.append("状态栏高度：" + ok.screenKit().getStatusBarHeight() + "\n\n");

        PhoneStateKit.BatteryState bs = ok.phoneStateKit().getBatteryState();
        tvInfo.append("电池使用率: " + bs.percent + "%\n");
        tvInfo.append("电池已使用：" + bs.level + "\n");
        tvInfo.append("电池总电量：" + bs.scale + "\n");
        tvInfo.append("电池充电状态：" + bs.status + "\n");
        tvInfo.append("电池充电方式：" + bs.plugged + "\n");
        tvInfo.append("电池健康状态：" + bs.health + "\n");
        tvInfo.append("电池电压：" + bs.voltage + "mV\n");
        tvInfo.append("电池温度：" + bs.temperature + "℃\n");
    }
}
