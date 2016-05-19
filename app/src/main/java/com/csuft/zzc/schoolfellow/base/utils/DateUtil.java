package com.csuft.zzc.schoolfellow.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangzhi on 16/4/27.
 */
public class DateUtil {
    public static final long DAY_TIME = 3600 * 24 * 1000;
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static SimpleDateFormat yearMountDayFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");

    public static String dataFormatByDefault(String timeStampStr) {
        long timeStamp = 0;
        try {
            timeStamp = Long.parseLong(timeStampStr);
            return format.format(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String dataFormatByDefault(long timeStamp) {
        return format.format(timeStamp);
    }


    public static String showTime(String timeStampStr) {
        long timeStamp = 0;
        try {
            timeStamp = Long.parseLong(timeStampStr);
            return showTime(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String showTime(long timeStamp) {
        Ymd timeStampYmd = longToYmd(timeStamp);
        Ymd nowYmd = longToYmd(System.currentTimeMillis());
        return showtime(timeStampYmd, nowYmd);

    }

    public static String showtime(Ymd ymd1, Ymd ymd2) {
        int year1 = ymd1.year;
        int month1 = ymd1.month;
        int day1 = ymd1.day;
        long time1 = ymd1.timeStamp;
        int year2 = ymd2.year;
        int month2 = ymd2.month;
        int day2 = ymd2.day;
        long time2 = ymd2.timeStamp;

        if (year1 != year2 || month1 != month2 || Math.abs(day2 - day1) > 1) {
            // 直接显示日期
            return yearMountDayFormat.format(time1);
        } else if (Math.abs(day2 - day1) == 1) {
            //显示昨天
            return "昨天";
        } else {
            //显示小时
            return hourMinuteFormat.format(time1);
        }


    }

    public static Ymd longToYmd(long times) {
        Date date = new Date(times);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        return new Ymd(times, year, month, day);
    }

    static class Ymd {

        public Ymd(long timeStamp, int year, int month, int day) {
            this.timeStamp = timeStamp;
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public long timeStamp;
        public int year;
        public int month;
        public int day;
    }

}
