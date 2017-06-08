package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.ReplyBean;
import com.ly.example.myapplication2.mvp.contact.ReplyContact;
import com.ly.example.myapplication2.rx.RxUtils;

import rx.Observable;
import rx.Subscriber;

public class ReplyPresenter {

    private ReplyContact.View view;
    private ReplyContact.Model model;

    public ReplyPresenter(ReplyContact.View view) {
        this.view = view;
        model = new ReplyContact.Model() {
            @Override
            public Observable<ReplyBean> replyComment(int newsId, String content, String share_to, int reply_to) {
                return ApiFactory.getApi().replyComment(newsId, content, share_to, reply_to);
            }
        };
    }

    public void replyComment(int newsId, String content, String share_to, Integer reply_to) {

        model.replyComment(newsId, content, share_to, reply_to)
                .compose(RxUtils.<ReplyBean>rxSchedulerHelperByView(view))
                .subscribe(new Subscriber<ReplyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e);
                    }

                    @Override
                    public void onNext(ReplyBean replyBean) {
                        view.onReplySuccess();
                    }
                });
    }
}
