package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.ReplyBean;
import com.ly.example.myapplication2.mvp.contact.ReplyContact;
import com.ly.example.myapplication2.rx.RxUtils;
import com.ly.example.myapplication2.utils.GsonUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

public class ReplyPresenter {

    private ReplyContact.View view;
    private ReplyContact.Model model;

    public ReplyPresenter(ReplyContact.View view) {
        this.view = view;
        model = (newsId, reply) -> ApiFactory.getApi().replyComment(newsId, reply);
    }

    public void replyComment(int newsId, ReplyBean replyBean) {
        RequestBody reply = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                GsonUtil.GsonString(replyBean));
        model.replyComment(newsId, reply)
                .compose(RxUtils.rxSchedulerHelperByView(view))
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
