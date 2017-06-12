package com.ly.example.myapplication2.mvp.contact;

import com.ly.example.myapplication2.api.apibean.ReplyBean;

import okhttp3.RequestBody;
import rx.Observable;

public interface ReplyContact {

    public static interface Model {
        Observable<ReplyBean> replyComment(int newsId, RequestBody reply);
    }

    public static interface View extends BaseView {
        void onReplySuccess();

        void onError(Throwable e);
    }
}
