package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    private boolean isLoading;

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
        initToolbar();
        initRecyclerView();
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
                        commentsAdapter.addLongComments(commentsBean.getComments(), true);
                    }
                });

        ApiFactory.getApi().shortComments(newsId)
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
                        commentsAdapter.addShortComments(commentsBean.getComments(), true);
                    }
                });
    }

    private void initRecyclerView() {
        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.commentRecycler.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        commentsAdapter = new CommentsAdapter(extraBean.getLong_comments(), extraBean.getShort_comments());
        binding.commentRecycler.setAdapter(commentsAdapter);
        binding.commentRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == commentsAdapter.getItemCount()) {
                    if (!isLoading) {
                        isLoading = true;

                    }
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initToolbar() {
        binding.commentToolbar.toolbar.setTitle(extraBean.getComments() + "条点评");
        binding.commentToolbar.toolbar.setTitleTextColor(Color.WHITE);
        binding.commentToolbar.toolbar.setNavigationIcon(R.drawable.back_alpha);
        binding.commentToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
