package com.ly.example.myapplication2.api;

public class ApiFactory {

    private static ZhihuDailyApi zhihuDailyApi;

    public static ZhihuDailyApi getApi() {
        if (zhihuDailyApi == null) {
            synchronized (ApiFactory.class) {
                if (zhihuDailyApi == null) {
                    zhihuDailyApi = ServiceGenerator.createService(ZhihuDailyApi.class);
                }
            }
        }
        return zhihuDailyApi;
    }
}
