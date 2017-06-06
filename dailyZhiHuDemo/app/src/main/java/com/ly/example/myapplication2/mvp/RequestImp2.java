package com.ly.example.myapplication2.mvp;

import rx.Subscription;

public interface RequestImp2<T> {

    void onSuccess(T data);

    void onError(Throwable e);

    void onAddSubscription(Subscription subscription);

    void onShowLoading();

    void onLoadingDismiss();
}
