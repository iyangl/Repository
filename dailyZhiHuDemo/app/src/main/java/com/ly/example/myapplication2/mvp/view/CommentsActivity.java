package com.ly.example.myapplication2.mvp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.adapter.CommentsAdapter;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.databinding.ActivityCommentsBinding;
import com.ly.example.myapplication2.mvp.presenter.CommentsPresenter;
import com.ly.example.myapplication2.mvp.view.iview.ICommentsView;
import com.ly.example.myapplication2.utils.CommonUtils;
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
        if (extraBean == null) {
            finish();
        }
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
        commentsAdapter.setOnItemClickListener((view, positions) -> {
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
                showCommentDialog(positions[0]);
            }
        });
    }

    private void showCommentDialog(final CommentsBean.CommentBean commentBean) {
        final Boolean voted = commentBean.getVoted();
        List<String> choices = new ArrayList<>();
        final Boolean own = commentBean.getOwn();
        if (own) {
            choices.add("删除");
            choices.add("复制");
        } else {
            choices.add(voted ? "取消赞同" : "赞同");
            choices.add("举报");
            choices.add("复制");
            choices.add("回复");
        }

        StyledDialog.buildIosSingleChoose(choices, new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i) {
                    case 0:
                        if (own) {
                            commentsPresenter.deleteOwnComment(commentBean.getId());
                            commentsAdapter.delete(commentBean);
                        } else {
                            commentsAdapter.notifySelectedItem(!voted,
                                    voted ? commentBean.getLikes() - 1 : commentBean.getLikes() + 1);
                            commentsPresenter.voteComment(commentBean.getId(), voted);
                        }
                        break;
                    case 1:
                        if (own) {
                            CommonUtils.cliptext(commentBean.getContent());
                            return;
                        }
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
                        CommonUtils.cliptext(commentBean.getContent());
                        break;
                    case 3:
                        goToReplyActivity(commentBean);
                        break;
                }
            }
        }).setCancelable(true, true).show();
    }

    private void initRecyclerView() {
        binding.commentRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.commentRecycler.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //关闭更新某条item时闪烁动画，不关闭会导致更新某条item时，所复用的view改变
        ((DefaultItemAnimator)binding.commentRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
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
        binding.commentToolbar.toolbar.setOnMenuItemClickListener(item -> {
            goToReplyActivity(null);
            return true;
        });
        binding.commentToolbar.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void goToReplyActivity(CommentsBean.CommentBean commentBean) {
        Intent intent = new Intent(this, ReplyActivity.class);
        intent.putExtra(Constant.Intent_Extra.NEWS_ID, newsId);
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
