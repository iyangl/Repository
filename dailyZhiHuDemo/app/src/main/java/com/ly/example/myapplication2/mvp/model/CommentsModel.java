package com.ly.example.myapplication2.mvp.model;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.imodel.ICommentsModel;
import com.ly.example.myapplication2.rx.RxUtils;

import rx.Subscriber;


public class CommentsModel implements ICommentsModel {

    @Override
    public void loadLongComments(int newsId, final RequestImp<CommentsBean> requestImp) {
        ApiFactory.getApi().longComments(newsId)
                .compose(RxUtils.<CommentsBean>rxSchedulerHelper())
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
    public void loadMoreLongComments(int newsId, int lastCommentId, final RequestImp<CommentsBean> requestImp) {
        ApiFactory.getApi().longCommentsBefore(newsId, lastCommentId)
                .compose(RxUtils.<CommentsBean>rxSchedulerHelper())
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
    public void loadShortComments(int newsId, final RequestImp<CommentsBean> requestImp) {
        ApiFactory.getApi().shortComments(newsId)
                .compose(RxUtils.<CommentsBean>rxSchedulerHelper())
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
    public void loadMoreShortComments(int newsId, int lastCommentId, final RequestImp<CommentsBean> requestImp) {
        ApiFactory.getApi().shortCommentsBefore(newsId, lastCommentId)
                .compose(RxUtils.<CommentsBean>rxSchedulerHelper())
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
}
