package com.ly.example.myapplication2.rx;

import android.os.SystemClock;

import com.ly.example.myapplication2.mvp.RequestImp2;
import com.ly.example.myapplication2.mvp.contact.BaseView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper(final RequestImp2 requestImp) {
        return commentsBeanObservable -> commentsBeanObservable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> SystemClock.sleep(300))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    if (requestImp != null) {
                        requestImp.onShowLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (requestImp != null) {
                        requestImp.onLoadingDismiss();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> rxSchedulerHelperByView(final BaseView view) {
        return commentsBeanObservable -> commentsBeanObservable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> SystemClock.sleep(300))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    if (view != null) {
                        view.onLoadingShow();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (view != null) {
                        view.onLoadingDismiss();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {
        return rxSchedulerHelper(null);
    }

}
