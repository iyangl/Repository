package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.databinding.ActivityNewsDetailBinding;
import com.ly.example.myapplication2.mvp.presenter.NewsDetailPresenter;
import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;
import com.ly.example.myapplication2.utils.Constant;


public class NewsDetailActivity extends AppCompatActivity implements INewsDetailView {
    private ActivityNewsDetailBinding binding;
    private NewsDetailPresenter newsDetailPresenter;
    private WebView webView;

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
        newsDetailPresenter.loadNewsDetail(newsId);
        initWebView();
    }

    private void initWebView() {
        webView = binding.wvNewsDetail;
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //先阻塞加载图片
        webSettings.setBlockNetworkImage(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                webSettings.setBlockNetworkImage(false);
                //判断webview是否加载了，图片资源
                if (!webSettings.getLoadsImagesAutomatically()) {
                    //设置wenView加载图片资源
                    webSettings.setLoadsImagesAutomatically(true);
                }
                super.onPageFinished(view, url);
            }
        });

    }

    private void initToolbar() {
        binding.newsDetailToolbar.toolbar.setTitle("");
    }

    @Override
    public void loadNewsDetailSuccess(NewsDetailBean newsDetailBean) {
        //        binding.wvNewsDetail.loadData(newsDetailBean.getBody(), "text/html; charset=UTF-8", null);

        binding.wvNewsDetail.loadData(newsDetailBean, this);
        //        binding.wvNewsDetail.loadUrl(newsDetailBean.getShare_url());
    }

}
