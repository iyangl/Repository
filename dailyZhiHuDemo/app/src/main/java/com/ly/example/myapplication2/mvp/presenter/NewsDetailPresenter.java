package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.apibean.ExtraBean;
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
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }

    public void loadStoryExtra(int newsId) {
        iNewsDetailModel.loadStoryExtra(newsId, new RequestImp<ExtraBean>() {
            @Override
            public void onSuccess(ExtraBean data) {
                if (data != null) {
                    iNewsDetailView.loadStoryExtra(data);
                }
            }

            @Override
            public void onError(Throwable e) {
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }

    public void voteStory(int newsId, int data) {
        iNewsDetailModel.voteStory(newsId, data, new RequestImp<ExtraBean>() {
            @Override
            public void onSuccess(ExtraBean data) {

            }

            @Override
            public void onError(Throwable e) {
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }
}
