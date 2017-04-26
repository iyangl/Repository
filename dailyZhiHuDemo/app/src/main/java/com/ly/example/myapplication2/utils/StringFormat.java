package com.ly.example.myapplication2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormat {

    public static String formatNewsDate(String date) {
        Date date1 = new Date();
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(date1);
        if (yyyyMMdd.equals(date)) {
            date = "今日热闻";
        }
        return date;
    }
}
