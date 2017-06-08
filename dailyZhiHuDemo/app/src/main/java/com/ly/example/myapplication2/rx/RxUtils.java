package com.ly.example.myapplication2.rx;

import android.os.SystemClock;

import com.ly.example.myapplication2.mvp.RequestImp2;
import com.ly.example.myapplication2.mvp.contact.BaseView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper(final RequestImp2 requestImp) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> commentsBeanObservable) {
                return commentsBeanObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                SystemClock.sleep(300);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                if (requestImp != null) {
                                    requestImp.onShowLoading();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(new Action0() {
                            @Override
                            public void call() {
                                if (requestImp != null) {
                                    requestImp.onLoadingDismiss();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> rxSchedulerHelperByView(final BaseView view) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> commentsBeanObservable) {
                return commentsBeanObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                SystemClock.sleep(300);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                if (view != null) {
                                    view.onLoadingShow();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(new Action0() {
                            @Override
                            public void call() {
                                if (view != null) {
                                    view.onLoadingDismiss();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return rxSchedulerHelper(null);
    }

}
