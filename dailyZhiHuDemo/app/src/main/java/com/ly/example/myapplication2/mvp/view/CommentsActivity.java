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
import android.widget.Toast;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.adapter.CommentsAdapter;
import com.ly.example.myapplication2.adapter.OnItemClickListener;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.databinding.ActivityCommentsBinding;
import com.ly.example.myapplication2.mvp.presenter.CommentsPresenter;
import com.ly.example.myapplication2.mvp.view.iview.ICommentsView;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.ToastUtil;

import timber.log.Timber;


public class CommentsActivity extends AppCompatActivity implements ICommentsView {

    private ActivityCommentsBinding binding;
    private CommentsAdapter commentsAdapter;
    private ExtraBean extraBean;
    private int newsId;
    private boolean isLoading;
    private CommentsPresenter commentsPresenter;

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
        commentsPresenter = new CommentsPresenter(this);
        commentsPresenter.loadLongComments(newsId);

        initOnClick();
    }

    private void initOnClick() {
        commentsAdapter.setOnItemClickListener(new OnItemClickListener<CommentsBean.CommentBean>() {
            @Override
            public void onClick(View view, CommentsBean.CommentBean... positions) {
                if (positions[0] == null) {
                    //打开关闭短评
                    LinearLayoutManager layoutManager = (LinearLayoutManager) binding.commentRecycler
                            .getLayoutManager();
                    boolean isSelected = !view.isSelected();
                    view.setSelected(isSelected);
                    if (isSelected) {
                        commentsPresenter.loadShortComments(newsId);
                        int i = commentsAdapter.getLongCommentsCount() + 1;
                        binding.commentRecycler.smoothScrollToPosition(i);
                    } else {
                        commentsAdapter.clearShortComments();
                        binding.commentRecycler.smoothScrollToPosition(0);
                    }
                } else {
                    Toast.makeText(CommentsActivity.this, positions[0].getAuthor(), Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void loadLongComments(CommentsBean commentBean, boolean isClear) {
        commentsAdapter.addLongComments(commentBean.getComments(), isClear);
    }

    @Override
    public void loadShortComments(CommentsBean commentBean, boolean isClear) {
        commentsAdapter.addShortComments(commentBean.getComments(), isClear);
    }

    @Override
    public void onError(Throwable e) {
        ToastUtil.showErrorMsg(e.getMessage());
    }
}
