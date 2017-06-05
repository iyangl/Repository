package com.ly.example.myapplication2.mvp.view.iview;

import com.ly.example.myapplication2.api.apibean.CommentsBean;

public interface ICommentsView {

    void loadLongComments(CommentsBean commentBean, boolean isClear);

    void loadShortComments(CommentsBean commentBean, boolean isClear);

    void onError(Throwable e);

}
