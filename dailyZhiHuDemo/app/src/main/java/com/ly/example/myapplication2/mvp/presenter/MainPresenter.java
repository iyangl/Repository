package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CreativesBean;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;

import java.util.List;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter {

    private IMainView iMainView;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void prefetchLaunchImages(boolean has_launch_images) {
        if (has_launch_images) {
            return;
        }

        ApiFactory.getApi().prefetchLaunchImages("1920*1080")
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<CreativesBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("prefetchLaunchImages__onError: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(List<CreativesBean> creativesBeen) {

                    }
                });
    }
}
