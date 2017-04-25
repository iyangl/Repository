package com.ly.example.myapplication2;

import android.app.Application;

import timber.log.Timber;

public class app extends Application {

    private static app app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initTimber();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static app getInstance() {
        return app;
    }
}
