package com.ly.example.myapplication2.mvp.model;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.imodel.INewsDetailModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsDetailModel implements INewsDetailModel {

    @Override
    public void loadNewsDetail(int newsId, final RequestImp<NewsDetailBean> requestImp) {
        ApiFactory.getApi().newsDetail(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetailBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(NewsDetailBean newsDetailBean) {
                        if (newsDetailBean != null) {
                            requestImp.onSuccess(newsDetailBean);
                        }
                    }
                });
    }
}
