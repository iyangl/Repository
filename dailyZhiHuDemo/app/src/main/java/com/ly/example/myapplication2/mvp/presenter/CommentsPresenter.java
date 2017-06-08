package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.ReplyBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.RequestImp2;
import com.ly.example.myapplication2.mvp.model.CommentsModel;
import com.ly.example.myapplication2.mvp.model.imodel.ICommentsModel;
import com.ly.example.myapplication2.mvp.view.iview.ICommentsView;

import rx.Subscription;

public class CommentsPresenter {

    private ICommentsModel iCommentsModel;
    private ICommentsView iCommentsView;

    public CommentsPresenter(ICommentsView iCommentsView) {
        this.iCommentsView = iCommentsView;
        iCommentsModel = new CommentsModel();
    }

    public void loadLongComments(int newsId) {
        iCommentsModel.loadLongComments(newsId, new RequestImp2<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                iCommentsView.loadLongComments(data, true);
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }

            @Override
            public void onAddSubscription(Subscription subscription) {
                iCommentsView.onAddSubscription(subscription);
            }

            @Override
            public void onShowLoading() {
                iCommentsView.onShowLoading();
            }

            @Override
            public void onLoadingDismiss() {
                iCommentsView.onLoadingDismiss();
            }
        });
    }

    public void loadMoreLongComments(int newsId, int lastCommentId) {
        if (lastCommentId == 0) {
            return;
        }
        iCommentsModel.loadMoreLongComments(newsId, lastCommentId, new RequestImp<CommentsBean>() {
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

    public void loadShortComments(int newsId) {
        iCommentsModel.loadShortComments(newsId, new RequestImp2<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                iCommentsView.loadShortComments(data, true);
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }

            @Override
            public void onAddSubscription(Subscription subscription) {
                iCommentsView.onAddSubscription(subscription);
            }

            @Override
            public void onShowLoading() {
                iCommentsView.onShowLoading();
            }

            @Override
            public void onLoadingDismiss() {
                iCommentsView.onLoadingDismiss();
            }
        });
    }

    public void loadMoreShortComments(int newsId, int lastCommentId) {
        if (lastCommentId == 0) {
            return;
        }
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

    public void voteComment(int id, Boolean voted) {
        iCommentsModel.voteComment(id, voted, new RequestImp<ExtraBean>() {
            @Override
            public void onSuccess(ExtraBean data) {

            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }
        });
    }

    public void deleteOwnComment(int comment_id) {
        iCommentsModel.deleteComment(comment_id, new RequestImp<ReplyBean>() {
            @Override
            public void onSuccess(ReplyBean data) {
                
            }

            @Override
            public void onError(Throwable e) {
                iCommentsView.onError(e);
            }
        });
    }

    public void replyComment(int newsId, String content, String share_to, int reply_to) {
        iCommentsModel.replyComment(newsId, content, share_to, reply_to, new RequestImp<ReplyBean>() {
            @Override
            public void onSuccess(ReplyBean data) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
