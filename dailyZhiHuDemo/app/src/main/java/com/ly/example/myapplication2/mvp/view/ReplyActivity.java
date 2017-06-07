package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.databinding.ActivityReplyBinding;
import com.ly.example.myapplication2.mvp.contact.ReplyContact;
import com.ly.example.myapplication2.utils.Constant;


public class ReplyActivity extends BaseActivity implements ReplyContact.View {
    private ActivityReplyBinding binding;
    private CommentsBean.CommentBean mCommentBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reply);

        mCommentBean = (CommentsBean.CommentBean) getIntent()
                .getSerializableExtra(Constant.Intent_Extra.REPLY_COMMENT);

        initView();
        initData();
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        StringBuffer title = new StringBuffer();
        if (mCommentBean == null) {
            title.append(getString(R.string.comment_write));
        } else {
            title.append(getString(R.string.reply)).append(mCommentBean.getAuthor());
        }
        binding.commentToolbar.toolbar.setTitle(title);
        binding.commentToolbar.toolbar.setTitleTextColor(Color.WHITE);
        binding.commentToolbar.toolbar.setNavigationIcon(R.drawable.back_alpha);
        binding.commentToolbar.toolbar.inflateMenu(R.menu.toolbar_comment_publish);
        binding.commentToolbar.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
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

    private void initData() {

    }


    @Override
    public void onLoadingShow() {

    }

    @Override
    public void onLoadingDismiss() {

    }
}
