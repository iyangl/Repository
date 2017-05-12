package com.ly.example.myapplication2.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.rx.RxBus;
import com.ly.example.myapplication2.rx.bean.WebCacheBean;
import com.ly.example.myapplication2.utils.CommonUtils;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.FileUtils;
import com.ly.example.myapplication2.utils.OkHttp3Utils;


public class CustomWebView extends WebView {

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadWebViewData(NewsDetailBean newsDetailBean) {
        if (newsDetailBean.getCss() != null && newsDetailBean.getCss().size() > 0) {
            for (String cssUrl : newsDetailBean.getCss()) {
                downloadJSorCSS(newsDetailBean.getId(), cssUrl, Constant.FileType.CSS);
            }
        }
        if (newsDetailBean.getJs() != null && newsDetailBean.getJs().size() > 0) {
            for (String jsUrl : newsDetailBean.getJs()) {
                downloadJSorCSS(newsDetailBean.getId(), jsUrl, Constant.FileType.JS);
            }
        }
    }

    public void downloadJSorCSS(final int id, final String url, final int filetype) {
        OkHttp3Utils.getInstance().doGetAsync(url, null, null, new OkHttp3Utils.NetCallback() {
            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onSuccess(int code, String content) {
                String fileName = CommonUtils.getMD5Str(url);
                String filePath = Constant.Storage.WEB_CACHE_DIR + fileName;
                boolean writeFile = FileUtils.writeFile(filePath, content.replace(".headline .img-place-holder {\n" +
                        "  height: 200px;\n" +
                        "}", ""));
                if (writeFile) {
                    WebCacheBean webCacheBean = new WebCacheBean();
                    webCacheBean.setId(id);
                    if (filetype == Constant.FileType.CSS) {
                        WebCacheBean.CSSCacheBean cssCacheBean = new WebCacheBean.CSSCacheBean();
                        cssCacheBean.setCssLink(fileName);
                        webCacheBean.setCssCacheBean(cssCacheBean);
                    } else if (filetype == Constant.FileType.JS) {
                        WebCacheBean.JSCacheBean jsCacheBean = new WebCacheBean.JSCacheBean();
                        jsCacheBean.setJsLink(fileName);
                        webCacheBean.setJsCacheBean(jsCacheBean);
                    }
                    RxBus.getInstance().post(webCacheBean);
                }
            }
        });

    }

}
