package com.ly.example.myapplication2.mvp.view.iview;

public interface ISplashView {

    void toMainActivity(Long seconds, boolean needUpdate);

    void loadLaunchImages(String imageUrl);
}
