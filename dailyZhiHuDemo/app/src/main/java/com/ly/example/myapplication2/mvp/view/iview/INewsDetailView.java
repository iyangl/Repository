package com.ly.example.myapplication2.mvp.view.iview;

import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;

public interface INewsDetailView {

    void loadNewsDetailSuccess(NewsDetailBean newsDetailBean);

    void loadStoryExtra(ExtraBean extraBean);

    void onErrorLoad(Throwable e);
}
