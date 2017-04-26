package com.ly.example.myapplication2.mvp;

public interface RequestImp<T> {

    void onSuccess(T data);

    void onError(Throwable e);

}
