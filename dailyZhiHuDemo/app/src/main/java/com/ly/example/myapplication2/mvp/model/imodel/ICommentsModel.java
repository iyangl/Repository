package com.ly.example.myapplication2.mvp.model.imodel;

import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.mvp.RequestImp;

public interface ICommentsModel {

    void loadLongComments(int newsId, RequestImp<CommentsBean> requestImp);

    void loadMoreLongComments(int newsId, int lastCommentId, RequestImp<CommentsBean> requestImp);

    void loadShortComments(int newsId, RequestImp<CommentsBean> requestImp);

    void loadMoreShortComments(int newsId, int lastCommentId, RequestImp<CommentsBean> requestImp);
}
