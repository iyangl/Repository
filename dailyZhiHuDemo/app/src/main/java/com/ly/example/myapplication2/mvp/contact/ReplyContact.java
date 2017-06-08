package com.ly.example.myapplication2.mvp.contact;

import com.ly.example.myapplication2.api.apibean.ReplyBean;

import rx.Observable;

public interface ReplyContact {

    public static interface Model {
        Observable<ReplyBean> replyComment(int newsId, String content, String share_to, int reply_to);
    }

    public static interface View extends BaseView {
        void onReplySuccess();

        void onError(Throwable e);
    }
}
