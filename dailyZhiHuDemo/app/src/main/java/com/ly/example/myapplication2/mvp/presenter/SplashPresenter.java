package com.ly.example.myapplication2.mvp.presenter;

import android.text.TextUtils;

import com.ly.example.myapplication2.api.apibean.CreativesBean;
import com.ly.example.myapplication2.mvp.model.SplashModel;
import com.ly.example.myapplication2.mvp.model.imodel.ISplashModel;
import com.ly.example.myapplication2.mvp.view.iview.ISplashView;

import timber.log.Timber;

public class SplashPresenter {

    private static final String TAG = "SplashPresenter";
    public static final String SPLASH_IMAGE = "splash_image";

    private ISplashView splashView;
    private ISplashModel iSplashModel;

    public SplashPresenter(ISplashView splashView) {
        this.splashView = splashView;
        iSplashModel = new SplashModel();
    }

    public void prefetchLaunchImages() {
        Timber.tag(TAG).i("prefetchLaunchImages");
        String imageUrl = "";
        boolean needUpdate = false;
        Long seconds = 0L;
        CreativesBean creativesBean = iSplashModel.hasLaunchImages();
        if (creativesBean != null) {
            needUpdate = iSplashModel.needUpdateLaunchImages(creativesBean);
            imageUrl = creativesBean.getUrl();
            seconds = 3000L;
        }
        if (!TextUtils.isEmpty(imageUrl)) {
            splashView.loadLaunchImages(imageUrl);
        }
        splashView.toMainActivity(seconds, needUpdate);
    }

}
