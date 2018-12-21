package top.onehundred.android.onekit.kits;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;

import top.onehundred.android.onekit.ok;

/**
 * 手机状态工具包<br />
 * 需要权限 Manifest.permission.ACCESS_NETWORK_STATE
 */
public class PhoneStateKit {

    private static PhoneStateKit phoneStateKit;

    public static PhoneStateKit getInstance() {
        if (phoneStateKit == null) {
            synchronized (PhoneStateKit.class) {
                if (phoneStateKit == null) {
                    phoneStateKit = new PhoneStateKit();
                }
            }
        }
        return phoneStateKit;
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public boolean isConnected() {

        ConnectivityManager connectivity = (ConnectivityManager) ok.app().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public boolean isWifi() {
        ConnectivityManager cm = (ConnectivityManager) ok.app().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 电池状态类
     */
    public class BatteryState {
        public int percent = 0;
        public int level;
        public int scale;
        public int status;
        public int plugged;
        public int health;
        public int voltage;
        public double temperature;
    }

    /**
     *  电池充电状态
     *  public static final int BATTERY_STATUS_UNKNOWN = 1;  未知状态
     *  public static final int BATTERY_STATUS_CHARGING = 2; 充电中
     *  public static final int BATTERY_STATUS_DISCHARGING = 3; 放电中
     *  public static final int BATTERY_STATUS_NOT_CHARGING = 4; 未纯电
     *  public static final int BATTERY_STATUS_FULL = 5; 电满状态
     */
    /**
     * 电池充电方式
     *  public static final int BATTERY_PLUGGED_USB = 2; USB充电
     *  public static final int BATTERY_PLUGGED_WIRELESS = 4; 无线充电
     *  public static final int BATTERY_PLUGGED_AC = 1; AC充电，交流电充电
     */
    /**
     *  public static final int BATTERY_HEALTH_UNKNOWN = 1;    健康状态未知
     *  public static final int BATTERY_HEALTH_GOOD = 2;    健康
     *  public static final int BATTERY_HEALTH_OVERHEAT = 3;  过热
     *  public static final int BATTERY_HEALTH_DEAD = 4;    没电
     *  public static final int BATTERY_HEALTH_OVER_VOLTAGE = 5;  过电压
     *  public static final int BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6; 未定义错误
     *  public static final int BATTERY_HEALTH_COLD = 7; 冷却
     */

    /**
     * 获取电池状态
     * @return
     */
    public BatteryState getBatteryState() {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent intent = ok.app().registerReceiver(null, iFilter);
        BatteryState bs = new BatteryState();
        bs.level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);      //获得当前电量
        bs.scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);         //获得总电量
        bs.status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);       //电池充电状态
        bs.plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);        //电池充电方式
        bs.health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);      //电池健康状况
        bs.voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);    //电池电压(mv)
        bs.temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0; //电池温度(数值)，默认获取的非摄氏温度值，需做一下运算转换

        if (bs.level > 0 && bs.scale > 0) {
            bs.percent = (bs.level * 100) / bs.scale;
        }
        return bs;
    }

}
