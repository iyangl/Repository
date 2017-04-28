package com.ly.example.myapplication2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringFormat {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static String formatNewsDate(String date) {
        String days;
        String week = "";
        Date date1 = new Date();
        String yyyyMMdd = sdf.format(date1);
        if (yyyyMMdd.equals(date)) {
            days = "今日热闻";
            return days;
        } else {
            days = date.substring(4, 6) + "月" + date.substring(6) + "日";
        }
        try {
            Date parse = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            week = getWeek(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return days + " " + week;
        }
    }

    public static String getDateDaysBefore(int days) {
        Date date = new Date();
        return getDateDaysBefore(date, days);
    }

    private static String getDateDaysBefore(Date date, int days) {
        long beforeTime = date.getTime() - (long) days * 24 * 60 * 60 * 1000;
        date.setTime(beforeTime);
        return sdf.format(date);
    }

    public static String getTomorrowDate(String date) {
        try {
            Date parseDate = sdf.parse(date);
            date = getDateDaysBefore(parseDate, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return date;
        }
    }

    /*获取星期几*/
    public static String getWeek(Calendar cal) {
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }

}
