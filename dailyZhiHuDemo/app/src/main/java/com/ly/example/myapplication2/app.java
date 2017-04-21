package com.ly.example.myapplication2;

import android.app.Application;

public class app extends Application {

    private static app app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static app getInstance() {
        return app;
    }
}
