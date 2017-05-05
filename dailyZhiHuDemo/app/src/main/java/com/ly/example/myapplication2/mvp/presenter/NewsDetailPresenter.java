package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.NewsDetailModel;
import com.ly.example.myapplication2.mvp.model.imodel.INewsDetailModel;
import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;

public class NewsDetailPresenter {

    private INewsDetailView iNewsDetailView;
    private INewsDetailModel iNewsDetailModel;

    public NewsDetailPresenter(INewsDetailView iNewsDetailView) {
        this.iNewsDetailView = iNewsDetailView;
        iNewsDetailModel = new NewsDetailModel();
    }

    public void loadNewsDetail(int newsId) {
        iNewsDetailModel.loadNewsDetail(newsId, new RequestImp<NewsDetailBean>() {
            @Override
            public void onSuccess(NewsDetailBean data) {
                iNewsDetailView.loadNewsDetailSuccess(data);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
