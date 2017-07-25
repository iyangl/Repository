package com.ly.example.myapplication2.mvp.model;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.ReplyBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.RequestImp2;
import com.ly.example.myapplication2.mvp.model.imodel.ICommentsModel;
import com.ly.example.myapplication2.rx.RxUtils;

import rx.Subscriber;
import rx.Subscription;


public class CommentsModel implements ICommentsModel {

    @Override
    public void loadLongComments(int newsId, final RequestImp2<CommentsBean> requestImp) {
        Subscription subscription = ApiFactory.getApi().longComments(newsId)
                .compose(RxUtils.rxSchedulerHelper(requestImp))
                .subscribe(new Subscriber<CommentsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(CommentsBean commentsBean) {
                        requestImp.onSuccess(commentsBean);
                    }
                });
        requestImp.onAddSubscription(subscription);
    }

    @Override
    public void loadMoreLongComments(int newsId, int lastCommentId, final RequestImp<CommentsBean> requestImp) {
        ApiFactory.getApi().longCommentsBefore(newsId, lastCommentId)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Subscriber<CommentsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(CommentsBean commentsBean) {
                        requestImp.onSuccess(commentsBean);
                    }
                });
    }

    @Override
    public void loadShortComments(int newsId, final RequestImp2<CommentsBean> requestImp) {
        Subscription subscription = ApiFactory.getApi().shortComments(newsId)
                .compose(RxUtils.rxSchedulerHelper(requestImp))
                .subscribe(new Subscriber<CommentsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(CommentsBean commentsBean) {
                        requestImp.onSuccess(commentsBean);
                    }
                });
        requestImp.onAddSubscription(subscription);
    }

    @Override
    public void loadMoreShortComments(int newsId, int lastCommentId, final RequestImp<CommentsBean> requestImp) {
        ApiFactory.getApi().shortCommentsBefore(newsId, lastCommentId)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Subscriber<CommentsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(CommentsBean commentsBean) {
                        requestImp.onSuccess(commentsBean);
                    }
                });
    }

    @Override
    public void voteComment(int newsId, Boolean voted, final RequestImp<ExtraBean> requestImp) {
        if (voted) {
            ApiFactory.getApi().voteComment(newsId)
                    .compose(RxUtils.rxSchedulerHelper())
                    .subscribe(new Subscriber<ExtraBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            requestImp.onError(e);
                        }

                        @Override
                        public void onNext(ExtraBean extraBean) {
                            requestImp.onSuccess(extraBean);
                        }
                    });
        } else {
            ApiFactory.getApi().voteComment(newsId, null)
                    .compose(RxUtils.rxSchedulerHelper())
                    .subscribe(new Subscriber<ExtraBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            requestImp.onError(e);
                        }

                        @Override
                        public void onNext(ExtraBean extraBean) {
                            requestImp.onSuccess(extraBean);
                        }
                    });
        }
    }

    @Override
    public void deleteComment(int commentId, final RequestImp<ReplyBean> requestImp) {
        ApiFactory.getApi().deleteComment(commentId)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Subscriber<ReplyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(ReplyBean replyBean) {
                        requestImp.onSuccess(replyBean);
                    }
                });
    }
}
