package com.baselib.util;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理类
 */
public class DateUtil {
    public static String format(Date date, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(date);
    }

    public static String workDateFormat(String date) {
        date = date.replace("T", " ");
        String str = date.contains("-") ? "yyyy-MM-dd HH:mm:ss" : "yyyy/MM/dd HH:mm";
        SimpleDateFormat t = new SimpleDateFormat(str);
        try {
            Date d = t.parse(date);
            return format(d, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            return "";
        }
    }

    public static long get7DayMilllis() {
        return 7 * 24 * 60 * 60 * 1000;
    }

    public static Date parse(String str, String patten) {

        Log.e("parseparse0", "==" + str + "==" + patten);
        try {
            DateFormat t = new SimpleDateFormat(patten);
            return (Date) t.parseObject(str);
        } catch (Exception e) {
            Log.e("parseparse,", e.toString() + "==" + str + "==" + patten);
            return new Date(System.currentTimeMillis() + 60000);
        }
    }

    public static Date defaultParse(String str) {
        String time = str.contains("-") ? "yyyy-MM-dd HH:mm" : "yyyy/MM/dd HH:mm";
        return parse(str, time);
    }

    public static String replaceT(String date) {
        String time = date.contains("-") ? "yyyy-MM-dd HH:mm" : "yyyy/MM/dd HH:mm";
        Date date1 = parse(date.replace("T", " "), time);
        return defaultFormat(date1);
    }

    public static long replaceLong(String date) {
        String time = date.contains("-") ? "yyyy-MM-dd HH:mm" : "yyyy/MM/dd HH:mm";
        Date date1 = parse(date.replace("T", " "), time);
        return date1.getTime();
    }

    public static String defaultFormat(Date date) {
        return format(date, "yyyy-MM-dd HH:mm");
    }

    /*时间格式转换*/
    public static String getTime(String dateStr) {
        if (TextUtils.isEmpty(dateStr))
            return "";
        if (dateStr.equals("0001-01-01T00:00:00") || dateStr.equals("1900-01-01T00:00:00") || dateStr.equals("1970-01-01T08:00:00") || dateStr.equals("1900/1/1 0:00:00")
                || dateStr.equals("1900/1/1T0:00:00") || dateStr.contains("1900/1/1 0:00:00")) {
            return "";
        }
        return DateUtil.replaceT(dateStr);
    }

    /**
     * 根据时间戳获取日期
     *
     * @param time t
     * @return
     */
    public static String getTime1(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    /**
     * 跟当前时间比较
     *
     * @param startTime
     * @return
     */
    public static String getTime2(String startTime, String index) {
        long end = System.currentTimeMillis();
        long start = replaceLong(startTime);
        Log.e("getTime1", end + "==" + start + "===" + (end - start) + "==" + index);
        Log.e("getTime2", getTime1(end) + "==" + getTime1(end + 60000));
        return end - start > 60000 ? getTime1(end) : getTime1(end + 60000);
    }
}
