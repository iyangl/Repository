package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.CommentsModel;
import com.ly.example.myapplication2.mvp.model.imodel.ICommentsModel;
import com.ly.example.myapplication2.mvp.view.iview.ICommentsView;

public class CommentsPresenter {

    private ICommentsModel iCommentsModel;
    private ICommentsView iCommentsView;

    public CommentsPresenter(ICommentsView iCommentsView) {
        this.iCommentsView = iCommentsView;
        iCommentsModel = new CommentsModel();
    }

    public void loadLongComments(int newsId) {
        iCommentsModel.loadLongComments(newsId, new RequestImp<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                iCommentsView.loadLongComments(data, false);
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }
        });
    }

    public void loadMoreLongComments(int newsId, int lastCommentId) {
        iCommentsModel.loadMoreLongComments(newsId, lastCommentId, new RequestImp<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                iCommentsView.loadLongComments(data, true);
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }
        });
    }

    public void loadShortComments(int newsId) {
        iCommentsModel.loadShortComments(newsId, new RequestImp<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                iCommentsView.loadShortComments(data, true);
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }
        });
    }

    public void loadMoreShortComments(int newsId, int lastCommentId) {
        iCommentsModel.loadMoreShortComments(newsId, lastCommentId, new RequestImp<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                iCommentsView.loadShortComments(data, false);
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }
        });
    }

}
