package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.databinding.ActivityNewsDetailBinding;
import com.ly.example.myapplication2.mvp.presenter.NewsDetailPresenter;
import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;
import com.ly.example.myapplication2.utils.Constant;


public class NewsDetailActivity extends AppCompatActivity implements INewsDetailView {
    private ActivityNewsDetailBinding binding;
    private NewsDetailPresenter newsDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        initToolbar();

        int newsId = getIntent().getIntExtra(Constant.Intent_Extra.NEWS_ID, -1);
        if (newsId == -1) {
            return;
        }
        newsDetailPresenter = new NewsDetailPresenter(this);
    }

    private void initToolbar() {
        binding.newsDetailToolbar.toolbar.setTitle("");
    }
}
