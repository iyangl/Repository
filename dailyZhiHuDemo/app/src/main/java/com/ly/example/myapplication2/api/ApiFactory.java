package com.ly.example.myapplication2.api;

public class ApiFactory {

    private static ZhiHuDailyApi zhihuDailyApi;

    public static ZhiHuDailyApi getApi() {
        if (zhihuDailyApi == null) {
            synchronized (ApiFactory.class) {
                if (zhihuDailyApi == null) {
                    zhihuDailyApi = ServiceGenerator.createService(ZhiHuDailyApi.class);
                }
            }
        }
        return zhihuDailyApi;
    }
}
