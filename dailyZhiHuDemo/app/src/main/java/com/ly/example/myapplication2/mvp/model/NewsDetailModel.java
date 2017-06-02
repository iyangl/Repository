package com.ly.example.myapplication2.mvp.model;

import android.os.SystemClock;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.imodel.INewsDetailModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


public class NewsDetailModel implements INewsDetailModel {

    @Override
    public void loadNewsDetail(int newsId, final RequestImp<NewsDetailBean> requestImp) {
        ApiFactory.getApi().newsDetail(newsId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //延迟请求是因为customProvider类对象需要延迟获取，
                        //网速好时，返回值会在获取到对象之前返回，造成NPE
                        SystemClock.sleep(500);
                    }
                })
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

    @Override
    public void loadStoryExtra(int newsId, final RequestImp<ExtraBean> requestImp) {
        ApiFactory.getApi().storyExtra(newsId)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //延迟请求是因为customProvider类对象需要延迟获取，
                        //网速好时，返回值会在获取到对象之前返回，造成NPE
                        SystemClock.sleep(500);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExtraBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(ExtraBean extraBean) {
                        requestImp.onSuccess(extraBean);
                    }
                });
    }

    @Override
    public void voteStory(int newsId, int data, final RequestImp<ExtraBean> requestImp) {
        ApiFactory.getApi().voteStory(newsId, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExtraBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(ExtraBean extraBean) {
                    }
                });
    }

    @Override
    public void collectStory(int newsId, boolean collect, final RequestImp<ExtraBean> requestImp) {
        if (collect) {
            ApiFactory.getApi().collectStory(newsId, null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ExtraBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            requestImp.onError(e);
                        }

                        @Override
                        public void onNext(ExtraBean extraBean) {
                        }
                    });
        } else {
            ApiFactory.getApi().collectStory(newsId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ExtraBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            requestImp.onError(e);
                        }

                        @Override
                        public void onNext(ExtraBean extraBean) {
                        }
                    });
        }

    }
}
