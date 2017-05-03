package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;

public class NewsDetailPresenter {

    private INewsDetailView iNewsDetailView;

    public NewsDetailPresenter(INewsDetailView iNewsDetailView) {
        this.iNewsDetailView = iNewsDetailView;
    }
}
