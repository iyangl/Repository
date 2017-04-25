package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.mvp.model.MainModel;
import com.ly.example.myapplication2.mvp.model.imodel.IMainModel;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter {

    private IMainView iMainView;
    private IMainModel iMainModel;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        this.iMainModel = new MainModel();
    }

    public void prefetchLaunchImages(boolean need_prefetch_images) {
        if (!need_prefetch_images) {
            return;
        }

        ApiFactory.getApi().prefetchLaunchImages("1920*1080")
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CreativesListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("prefetchLaunchImages__onError: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(CreativesListBean creativesBeen) {
                        Timber.i("creativesBeen : %s", creativesBeen);
                        iMainModel.savePrefetchLaunchImages(creativesBeen);
                    }
                });
    }
}
