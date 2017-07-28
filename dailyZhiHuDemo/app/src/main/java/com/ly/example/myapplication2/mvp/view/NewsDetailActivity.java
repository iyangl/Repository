package com.ly.example.myapplication2.mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
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
import com.ly.example.myapplication2.rx.RxBus;
import com.ly.example.myapplication2.rx.bean.WebCacheBean;
import com.ly.example.myapplication2.utils.CommonUtils;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.HtmlUtil;
import com.ly.example.myapplication2.utils.StringFormat;
import com.ly.example.myapplication2.utils.ToastUtil;
import com.ly.example.myapplication2.widgets.BadgeActionProvider;
import com.ly.example.myapplication2.widgets.CustomWebView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

import static com.ly.example.myapplication2.utils.Constant.Intent_Extra.NEWS_ID;


public class NewsDetailActivity extends AppCompatActivity implements INewsDetailView {
    private ActivityNewsDetailBinding binding;
    private NewsDetailPresenter mNewsDetailPresenter;
    private CustomWebView mWebView;
    private Toolbar mToolbar;
    private BadgeActionProvider commentActionProvider;
    private BadgeActionProvider praiseActionProvider;
    private BadgeActionProvider collectActionProvider;
    private int newsId;
    private Subscription subscription;
    private WebCacheBean mWebCache;
    private ExtraBean extraBean;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        initToolbar();

        newsId = getIntent().getIntExtra(NEWS_ID, -1);
        if (newsId == -1) {
            return;
        } else {
            initWebCache();
        }
        rxPermissions = new RxPermissions(this);
        initWebView();
        initEvent();
        setAppbarAlphaListener();
    }

    private void initWebCache() {
        mWebCache = new WebCacheBean();
        mWebCache.setId(newsId);
        mWebCache.setCssLinks(new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                return !contains(s) && super.add(s);
            }
        });
        mWebCache.setJsLinks(new ArrayList<String>() {
            @Override
            public boolean add(String s) {
                return !contains(s) && super.add(s);
            }
        });
    }

    private void initEvent() {
        subscription = RxBus.getInstance().toObserverable(WebCacheBean.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WebCacheBean>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.showErrorMsg("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showErrorMsg(e.getMessage());
                    }

                    @Override
                    public void onNext(WebCacheBean webCacheBean) {
                        loadWebViewData(webCacheBean);
                    }
                });

        mNewsDetailPresenter = new NewsDetailPresenter(this);
        mNewsDetailPresenter.loadNewsDetail(newsId);
        mNewsDetailPresenter.loadStoryExtra(newsId);
    }

    private void loadWebViewData(WebCacheBean webCacheBean) {
        if (mWebCache.getId() == webCacheBean.getId()) {

            if (webCacheBean.getCssCacheBean() != null) {
                mWebCache.getCssLinks().add(webCacheBean.getCssCacheBean().getCssLink());
            } else if (webCacheBean.getJsCacheBean() != null) {
                mWebCache.getJsLinks().add(webCacheBean.getJsCacheBean().getJsLink());
            }

            if (mWebCache.getCssLinks().size() == mWebCache.getCssCount() &&
                    mWebCache.getJsLinks().size() == mWebCache.getJsCount()) {
                String htmlData = mWebCache.getHtmlData();
                htmlData = HtmlUtil.createCssTag(mWebCache.getCssLinks()) + htmlData;
                htmlData = htmlData + HtmlUtil.createJsTag(mWebCache.getJsLinks());
                htmlData = HtmlUtil.getDefaultImgContent(htmlData);
                mWebView.loadDataWithBaseURL("file:///" + Constant.Storage.WEB_CACHE_DIR
                        , htmlData, "text/html; charset=UTF-8", null, null);
            }
        }
    }

    @SuppressLint("JSInterface")
    private void initWebView() {
        mWebView = binding.wvNewsDetail;
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //先阻塞加载图片
        webSettings.setBlockNetworkImage(false);
        // 添加js交互接口类，并起别名 imagelistner
        mWebView.addJavascriptInterface(new JSInterface(this), "imagelistner");
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private void initToolbar() {
        mToolbar = binding.newsDetailToolbar.toolbar;
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setPadding(mToolbar.getPaddingLeft(), mToolbar.getPaddingTop(),
                mToolbar.getPaddingRight() + CommonUtils.getDimension(R.dimen.x15)
                , mToolbar.getPaddingBottom());
        mToolbar.setNavigationIcon(R.drawable.back_alpha);
        mToolbar.setNavigationOnClickListener(v -> NewsDetailActivity.this.finish());
    }

    private float lastPercent = 0;

    public void setAppbarAlphaListener() {
        binding.appbar.addOnOffsetChangedListener(this::setAlphaAnimationWithScroll);
    }

    private double ALPHA_PERCENT = 0.1;

    private void setAlphaAnimationWithScroll(AppBarLayout appBarLayout, int verticalOffset) {
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        float maxPercent = 1 - (float) mToolbar.getHeight() / (float) totalScrollRange;
        float percent = Math.abs(verticalOffset) / (float) totalScrollRange;
        if (Math.abs(percent - 0.1) > ALPHA_PERCENT && percent < maxPercent) {
            ALPHA_PERCENT = 0.1;
            AlphaAnimation alphaAnimation;
            if (lastPercent <= percent) {
                alphaAnimation = new AlphaAnimation(maxPercent - lastPercent, maxPercent - percent);
            } else {
                alphaAnimation = new AlphaAnimation(1 - percent, 1 - lastPercent);
            }
            alphaAnimation.setFillAfter(true);
            mToolbar.startAnimation(alphaAnimation);
            lastPercent = percent;
        }
        if (percent >= 1) {
            ALPHA_PERCENT = 0.01;
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setFillAfter(true);
            mToolbar.startAnimation(alphaAnimation);
        }
        if (percent == 0) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setFillAfter(true);
            mToolbar.startAnimation(alphaAnimation);
        }
    }

    private String share_url;
    private String share_title;
    private String share_desc;

    @Override
    public void loadNewsDetailSuccess(NewsDetailBean newsDetailBean) {
        binding.setNews(newsDetailBean);

        mWebCache.setCssCount(CommonUtils.getListSize(newsDetailBean.getCss()));
        mWebCache.setJsCount(CommonUtils.getListSize(newsDetailBean.getJs()));
        mWebCache.setHtmlData(newsDetailBean.getBody());

        mWebView.loadWebViewData(newsDetailBean);

        share_url = newsDetailBean.getShare_url();
        share_desc = newsDetailBean.getImages().get(0);
        share_title = newsDetailBean.getTitle();
    }

    @Override
    public void loadStoryExtra(ExtraBean extraBean) {
        this.extraBean = extraBean;
        commentActionProvider.setBadge(StringFormat.formatKNumber(extraBean.getComments()));
        praiseActionProvider.setBadge(StringFormat.formatKNumber(extraBean.getPopularity()));
        praiseActionProvider.setSelected(extraBean.getVote_status() == 1);
        collectActionProvider.setSelected(extraBean.getFavorite());
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
                mNewsDetailPresenter.share(NewsDetailActivity.this, share_url, share_title,
                        share_desc, umShareListener);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Timber.d("onStart SHARE_MEDIA: %s", share_media.toString());
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Timber.d("onResult SHARE_MEDIA: %s", share_media.toString());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Timber.d("onError SHARE_MEDIA: %s, %s", share_media.toString(), throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Timber.d("onCancel SHARE_MEDIA: %s", share_media.toString());
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        commentActionProvider.setIcon(R.drawable.comment);
        praiseActionProvider.setIcon(R.drawable.selector_item_news_praise);
        collectActionProvider.setIcon(R.drawable.selecotr_item_news_collect);
        collectActionProvider.setBadgeGone();
    }

    private void initBadge(Menu menu) {
        MenuItem menuItem1 = menu.findItem(R.id.item_toolbar_news_comments);
        MenuItem menuItem2 = menu.findItem(R.id.item_toolbar_news_zan);
        MenuItem menuItem3 = menu.findItem(R.id.item_toolbar_news_collect);
        commentActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem1);
        praiseActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem2);
        collectActionProvider = (BadgeActionProvider) MenuItemCompat.getActionProvider(menuItem3);
        commentActionProvider.setOnClickListener(0, onClickListener);// 设置点击监听。
        praiseActionProvider.setOnClickListener(1, onClickListener);// 设置点击监听。
        collectActionProvider.setOnClickListener(2, onClickListener);// 设置点击监听。
    }

    private BadgeActionProvider.OnClickListener onClickListener = new BadgeActionProvider.OnClickListener() {
        @Override
        public void onClick(int what) {
            if (what == 0) {
                Intent intent = new Intent(NewsDetailActivity.this, CommentsActivity.class);
                intent.putExtra(Constant.Intent_Extra.NEWS_STORY_EXTRA, extraBean);
                intent.putExtra(Constant.Intent_Extra.NEWS_ID, newsId);
                NewsDetailActivity.this.startActivity(intent);
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
            } else if (what == 2) {
                collectActionProvider.setSelected(!collectActionProvider.isSelected());
                mNewsDetailPresenter.collectStory(newsId, collectActionProvider.isSelected());
            }
        }
    };


    // 监听
    private class MyWebViewClient extends WebViewClient {

        //调用外部浏览器打开超链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
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
    }

    // 注入js函数监听
    private void addImageClickListner() {
        //网页内容加载完成之后，将真实图片的值替换回src
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "     if(objs[i].src.indexOf(\"loading_image_default.png\")<0) {"
                + "     } else {"
                + "         objs[i].src = objs[i].alt;"
                + "     }" +
                "}" +
                "})()");


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //QQ与新浪分享
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
