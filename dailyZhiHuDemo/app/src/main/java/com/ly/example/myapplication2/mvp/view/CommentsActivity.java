package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.adapter.CommentsAdapter;
import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.databinding.ActivityCommentsBinding;
import com.ly.example.myapplication2.utils.Constant;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private CommentsAdapter commentsAdapter;
    private ExtraBean extraBean;
    private int newsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        extraBean = (ExtraBean) getIntent().getSerializableExtra(Constant.Intent_Extra.NEWS_STORY_EXTRA);
        newsId = getIntent().getIntExtra(Constant.Intent_Extra.NEWS_ID, 0);
        Timber.i("extra : %s", extraBean);

        initView();
        initEvent();
    }

    private void initView() {
        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        commentsAdapter = new CommentsAdapter();
        binding.commentRecycler.setAdapter(commentsAdapter);
    }

    private void initEvent() {
        ApiFactory.getApi().longComments(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CommentsBean commentsBean) {
                        commentsAdapter.addAll(commentsBean.getComments());
                    }
                });
    }
}
