package com.ly.example.myapplication2.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
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

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import timber.log.Timber;


public class CommentsActivity extends BaseActivity implements ICommentsView {

    private ActivityCommentsBinding binding;
    private CommentsAdapter commentsAdapter;
    private ExtraBean extraBean;
    private int newsId;
    private boolean isLoading;
    private CommentsPresenter commentsPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isUnFold;

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

        initOnClick();
    }

    private boolean shouldSmooth;

    private void initOnClick() {
        commentsAdapter.setOnItemClickListener(new OnItemClickListener<CommentsBean.CommentBean>() {
            @Override
            public void onClick(View view, final CommentsBean.CommentBean... positions) {
                if (positions[0] == null) {
                    //打开关闭短评
                    isUnFold = !view.isSelected();
                    view.setSelected(isUnFold);
                    if (isUnFold) {
                        commentsPresenter.loadShortComments(newsId);
                        shouldSmooth = true;
                    } else {
                        //取消可能存在的正在进行的网络请求
                        clearAllSubscriptions();
                        commentsAdapter.clearShortComments();
                        binding.commentRecycler.smoothScrollToPosition(0);
                    }
                } else {
                    final CommentsBean.CommentBean commentBean = positions[0];
                    final Boolean voted = commentBean.getVoted();
                    List<String> choices = new ArrayList<>();
                    choices.add(voted ? "取消赞同" : "赞同");
                    choices.add("举报");
                    choices.add("复制");
                    choices.add("回复");
                    StyledDialog.buildIosSingleChoose(choices, new MyItemDialogListener() {
                        @Override
                        public void onItemClick(CharSequence charSequence, int i) {
                            switch (i) {
                                case 0:
                                    commentsAdapter.notifySelectedItem(!voted,
                                            voted ? commentBean.getLikes() - 1 : commentBean.getLikes() + 1);
                                    commentsPresenter.voteComment(commentBean.getId(), voted);
                                    break;
                                case 1:
                                    StyledDialog.buildIosAlert(getString(R.string.report_title),
                                            getString(R.string.report_msg), new MyDialogListener() {
                                                @Override
                                                public void onFirst() {
                                                }

                                                @Override
                                                public void onSecond() {
                                                }
                                            })
                                            .setBtnText(getString(R.string.cancel), getString(R.string.report_confirm))
                                            .setCancelable(true, true).show();
                                    break;
                                case 2:
                                    // 将文本内容放到系统剪贴板里。
                                    ClipboardManager cm = (ClipboardManager) getSystemService(
                                            Context.CLIPBOARD_SERVICE);
                                    cm.setText(commentBean.getContent());
                                    Toast.makeText(CommentsActivity.this, R.string.clipboard_success,
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case 3:
                                    goToReplyActivity(commentBean);
                                    break;
                            }
                        }
                    }).setCancelable(true, true).show();
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
        mLinearLayoutManager = (LinearLayoutManager) binding.commentRecycler
                .getLayoutManager();
        binding.commentRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                int itemCount = commentsAdapter.getItemCount();
                if (lastVisibleItemPosition + 2 == itemCount) {
                    if (!isLoading) {
                        if (isUnFold) {
                            if (extraBean.getShort_comments() == 0 ||
                                    extraBean.getShort_comments() == commentsAdapter.getShortCommentsCount()) {
                                return;
                            }
                            commentsPresenter.loadMoreShortComments(newsId, commentsAdapter.getLastShortCommentId());
                        } else {
                            if (extraBean.getLong_comments() == 0 ||
                                    extraBean.getLong_comments() == commentsAdapter.getLongCommentsCount()) {
                                return;
                            }
                            commentsPresenter.loadMoreLongComments(newsId, commentsAdapter.getLastLongCommentId());
                        }
                        isLoading = true;
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initToolbar() {
        binding.commentToolbar.toolbar.setTitle(extraBean.getComments() + getString(R.string.count_comments));
        binding.commentToolbar.toolbar.setTitleTextColor(Color.WHITE);
        binding.commentToolbar.toolbar.setNavigationIcon(R.drawable.back_alpha);
        binding.commentToolbar.toolbar.inflateMenu(R.menu.toolbar_comments);
        binding.commentToolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                goToReplyActivity(null);
                return true;
            }
        });
        binding.commentToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void goToReplyActivity(CommentsBean.CommentBean commentBean) {
        Intent intent = new Intent(this, ReplyActivity.class);
        if (commentBean != null) {
            intent.putExtra(Constant.Intent_Extra.REPLY_COMMENT, commentBean);
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUnFold) {
            commentsPresenter.loadShortComments(newsId);
        } else {
            commentsPresenter.loadLongComments(newsId);
        }
    }

    @Override
    public void loadLongComments(CommentsBean commentBean, boolean isClear) {
        isLoading = false;
        commentsAdapter.addLongComments(commentBean.getComments(), isClear);
    }

    @Override
    public void loadShortComments(CommentsBean commentBean, boolean isClear) {
        isLoading = false;
        commentsAdapter.addShortComments(commentBean.getComments(), isClear);
        if (shouldSmooth) {
            shouldSmooth = false;
            int i = commentsAdapter.getLongCommentsCount() + 1;
            mLinearLayoutManager.scrollToPositionWithOffset(i, 0);
        }
    }

    @Override
    public void onError(Throwable e) {
        isLoading = false;
        ToastUtil.showErrorMsg(e.getMessage());
    }

    @Override
    public void onAddSubscription(Subscription subscription) {
        addSubscription(subscription);
    }

    @Override
    public void onShowLoading() {
        showLoading();
    }

    @Override
    public void onLoadingDismiss() {
        dismiss();
    }
}
