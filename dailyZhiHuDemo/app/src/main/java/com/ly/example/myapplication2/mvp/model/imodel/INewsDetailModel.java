package com.ly.example.myapplication2.mvp.model.imodel;

import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;

public interface INewsDetailModel {

    void loadNewsDetail(int newsId, RequestImp<NewsDetailBean> requestImp);

    void loadStoryExtra(int newsId, RequestImp<ExtraBean> requestImp);

    void voteStory(int newsId, int data, RequestImp<ExtraBean> requestImp);

    void collectStory(int newsId, boolean collect, RequestImp<ExtraBean> requestImp);
}
