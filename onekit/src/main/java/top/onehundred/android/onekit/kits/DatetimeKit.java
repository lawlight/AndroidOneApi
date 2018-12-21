package top.onehundred.android.onekit.kits;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 日期与时间工具类
 */
public class DatetimeKit {

    private static String defaultFormat = "yyyy-MM-dd HH:mm:ss";

    private static DatetimeKit datetimeKit;

    public static DatetimeKit getInstance() {
        if (datetimeKit == null) {
            synchronized (DatetimeKit.class) {
                if (datetimeKit == null) {
                    datetimeKit = new DatetimeKit();
                }
            }
        }
        return datetimeKit;
    }

    /**
     * 获取当前时间时间戳
     *
     * @return
     */
    public long getTime() {
        return new Date().getTime();
    }

    /**
     * 获取秒单位时间戳
     *
     * @return
     */
    public long getUnixTime() {
        return new Date().getTime() / 1000L;
    }

    /**
     * Date对象转String
     *
     * @param date   时间，默认为当前时间
     * @param format 格式，默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String date2Str(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        if (format == null) {
            format = defaultFormat;
        }
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        return simpleFormat.format(date);
    }

    /**
     * @param text
     * @param format 格式，默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public Date str2Date(String text, String format) {
        if (format == null) {
            format = defaultFormat;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param calendar
     * @param format   格式，默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String calendar2Str(Calendar calendar, String format) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return date2Str(calendar.getTime(), format);
    }

    /**
     * 时间字符串转为Calendar对象
     *
     * @param text
     * @param format 格式，默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public Calendar str2Calendar(String text, String format) {
        Date date = str2Date(text, format);
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Calendar转Date
     *
     * @param calendar
     * @return
     */
    public Date calendar2Date(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.getTime();
    }

    /**
     * Date转Calendar
     *
     * @param date
     * @return
     */
    public Calendar date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 格式化时间字符串
     *
     * @param text 时间文本
     * @param from 原格式
     * @param to   目标格式
     * @return
     */
    public String formatDatetime(String text, String from, String to) {
        SimpleDateFormat fromSDF = new SimpleDateFormat(from);
        SimpleDateFormat toSDK = new SimpleDateFormat(to);
        try {
            Date date = fromSDF.parse(text);
            return toSDK.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return text;
        }
    }

    /**
     * 获取指定月份的天数
     *
     * @param year  年份
     * @param month 月份
     * @return 对应天数
     */
    public int daysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 判断是否闰年
     * @param year 年份
     * @return 是否闰年
     */
    public Boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    /**
     * 获取输入时间的时间段描述：<br/>
     * 1分钟内显示刚刚<br/>
     * 1小时内显示XX分钟前<br/>
     * 1天内显示XX小时前<br/>
     * 超过一天按照format格式显示日期
     *
     * @param date   时间，如果date传null，返回空字符串
     * @param format 格式，默认为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getTimeDesc(Date date, String format) {
        if (date == null) {
            return "";
        }
        Date nowDate = new Date();
        long diff = (nowDate.getTime() - date.getTime()) / 1000;
        if (diff < 60) {
            return "刚刚";
        }
        if (diff < 3600) {
            return (diff / 60) + "分钟前";
        }
        if (diff < 24 * 3600) {
            return (diff / 3600) + "小时前";
        }
        SimpleDateFormat returnSdf = new SimpleDateFormat(format);
        return returnSdf.format(date);
    }

    /**
     * 显示系统日期选择对话框
     *
     * @param context
     * @param calendar          默认日期
     * @param onDateSetListener 回调监听
     */
    public void showDatePickerDialog(Context context, Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        DatePickerDialog dialog = new DatePickerDialog(context, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    /**
     * 显示系统时间选择对话框
     *
     * @param context
     * @param calendar          默认时间
     * @param onTimeSetListener 回调监听
     */
    public void showTimePickerDialog(Context context, Calendar calendar, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        TimePickerDialog dialog = new TimePickerDialog(context, onTimeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

}
