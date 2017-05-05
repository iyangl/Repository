package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.databinding.ActivityNewsDetailBinding;
import com.ly.example.myapplication2.mvp.presenter.NewsDetailPresenter;
import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.ToastUtil;
import com.ly.example.myapplication2.widgets.BadgeActionProvider;
import com.ly.example.myapplication2.widgets.CustomWebView;


public class NewsDetailActivity extends AppCompatActivity implements INewsDetailView {
    private ActivityNewsDetailBinding binding;
    private NewsDetailPresenter mNewsDetailPresenter;
    private CustomWebView mWebView;
    private Toolbar mToolbar;
    private BadgeActionProvider commentActionProvider;
    private BadgeActionProvider praiseActionProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        initToolbar();

        int newsId = getIntent().getIntExtra(Constant.Intent_Extra.NEWS_ID, -1);
        if (newsId == -1) {
            return;
        }
        mNewsDetailPresenter = new NewsDetailPresenter(this);
        mNewsDetailPresenter.loadNewsDetail(newsId);
        initWebView();
    }

    private void initWebView() {
        mWebView = binding.wvNewsDetail;
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //先阻塞加载图片
        webSettings.setBlockNetworkImage(true);

        mWebView.setWebViewClient(new WebViewClient() {
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
        mToolbar = binding.newsDetailToolbar.toolbar;
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.back_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.this.finish();
            }
        });
        mToolbar.inflateMenu(R.menu.toolbar_news_menu);
        initBadge();
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_toolbar_news_share:
                        ToastUtil.showSuccessMsg(R.string.share);
                        break;
                    case R.id.item_toolbar_news_collect:

                        ToastUtil.showSuccessMsg(R.string.collect);
                        break;
                    case R.id.item_toolbar_news_comments:
                        ToastUtil.showSuccessMsg(R.string.comments);
                        break;
                    case R.id.item_toolbar_news_zan:
                        ToastUtil.showSuccessMsg(R.string.zan);
                        break;
                }
                return false;
            }
        });
    }

    private void initBadge() {
        Menu menu = mToolbar.getMenu();
        MenuItem menuItem1 = menu.findItem(R.id.item_toolbar_news_comments);
        MenuItem menuItem2 = menu.findItem(R.id.item_toolbar_news_zan);
        commentActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem1);
        praiseActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem2);
        commentActionProvider.setIcon(R.drawable.comment);
        praiseActionProvider.setIcon(R.drawable.praise);
        commentActionProvider.setOnClickListener(0, onClickListener);// 设置点击监听。
        praiseActionProvider.setOnClickListener(1, onClickListener);// 设置点击监听。
    }

    private BadgeActionProvider.OnClickListener onClickListener = new BadgeActionProvider.OnClickListener() {
        @Override
        public void onClick(int what) {
            if (what == 0) {
                commentActionProvider.setBadge(commentActionProvider.getBadge() + 1);
            } else if (what == 1) {
                praiseActionProvider.setBadge(praiseActionProvider.getBadge() + 1);
            }
        }
    };

    @Override
    public void loadNewsDetailSuccess(NewsDetailBean newsDetailBean) {
        //        binding.wvNewsDetail.loadData(newsDetailBean.getBody(), "text/html; charset=UTF-8", null);

        binding.setNews(newsDetailBean);
        mWebView.loadData(newsDetailBean, this);
        //        binding.wvNewsDetail.loadUrl(newsDetailBean.getShare_url());
    }


    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        return super.onCreateOptionsMenu(menu);
    //    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.item_toolbar_news_share:
    //                ToastUtil.showSuccessMsg(R.string.share);
    //                break;
    //            case R.id.item_toolbar_news_collect:
    //                ToastUtil.showSuccessMsg(R.string.collect);
    //                break;
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }
}
