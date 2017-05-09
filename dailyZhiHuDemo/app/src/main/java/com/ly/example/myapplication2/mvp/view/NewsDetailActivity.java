package com.ly.example.myapplication2.mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.databinding.ActivityNewsDetailBinding;
import com.ly.example.myapplication2.mvp.presenter.NewsDetailPresenter;
import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.StringFormat;
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
    private int newsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        initToolbar();

        newsId = getIntent().getIntExtra(Constant.Intent_Extra.NEWS_ID, -1);
        if (newsId == -1) {
            return;
        }
        initWebView();
        mNewsDetailPresenter = new NewsDetailPresenter(this);
        mNewsDetailPresenter.loadNewsDetail(newsId);
        mNewsDetailPresenter.loadStoryExtra(newsId);

    }

    @SuppressLint("JSInterface")
    private void initWebView() {
        mWebView = binding.wvNewsDetail;
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //先阻塞加载图片
        webSettings.setBlockNetworkImage(true);
        // 添加js交互接口类，并起别名 imagelistner
        mWebView.addJavascriptInterface(new JSInterface(this), "imagelistner");
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private void initToolbar() {
        mToolbar = binding.newsDetailToolbar.toolbar;
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.this.finish();
            }
        });
    }

    @Override
    public void loadNewsDetailSuccess(NewsDetailBean newsDetailBean) {
        binding.setNews(newsDetailBean);
        mWebView.loadData(newsDetailBean);
    }

    @Override
    public void loadStoryExtra(ExtraBean extraBean) {
        commentActionProvider.setBadge(StringFormat.formatKNumber(extraBean.getComments()));
        praiseActionProvider.setBadge(StringFormat.formatKNumber(extraBean.getPopularity()));
        praiseActionProvider.setSelected(extraBean.getVote_status() == 1);
    }

    @Override
    public void onErrorLoad(Throwable e) {
        Toast.makeText(NewsDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_news_menu, menu);
        initBadge(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_toolbar_news_share:
                ToastUtil.showSuccessMsg(R.string.share);
                break;
            case R.id.item_toolbar_news_collect:
                ToastUtil.showSuccessMsg(R.string.collect);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        commentActionProvider.setIcon(R.drawable.comment);
        praiseActionProvider.setIcon(R.drawable.selector_item_news_praise);
    }

    private void initBadge(Menu menu) {
        MenuItem menuItem1 = menu.findItem(R.id.item_toolbar_news_comments);
        MenuItem menuItem2 = menu.findItem(R.id.item_toolbar_news_zan);
        commentActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem1);
        praiseActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem2);
        commentActionProvider.setOnClickListener(0, onClickListener);// 设置点击监听。
        praiseActionProvider.setOnClickListener(1, onClickListener);// 设置点击监听。
    }

    private BadgeActionProvider.OnClickListener onClickListener = new BadgeActionProvider.OnClickListener() {
        @Override
        public void onClick(int what) {
            if (what == 0) {
            } else if (what == 1) {
                int data = 0;
                if (praiseActionProvider.isSelected()) {
                    praiseActionProvider.setBadge(String.valueOf(praiseActionProvider.getBadge() - 1));
                } else {
                    praiseActionProvider.setBadge(String.valueOf(praiseActionProvider.getBadge() + 1));
                    data = 1;
                }
                praiseActionProvider.setSelected(!praiseActionProvider.isSelected());
                mNewsDetailPresenter.voteStory(newsId, data);
            }
        }
    };


    // 监听
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            WebSettings webSettings = view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBlockNetworkImage(false);
            //判断webview是否加载了，图片资源
            if (!webSettings.getLoadsImagesAutomatically()) {
                //设置wenView加载图片资源
                webSettings.setLoadsImagesAutomatically(true);
            }

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }


    // js通信接口
    public class JSInterface {

        private Context context;

        public JSInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(String img) {
            System.out.println(img);
            Intent intent = new Intent();
            intent.putExtra(Constant.Intent_Extra.WEB_IMAGE_URL, img);
            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }
    }
}
