package com.ly.example.myapplication2.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.view.NewsDetailActivity;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.HtmlUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;


public class CustomWebView extends WebView {

    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        super.loadData(data, mimeType, encoding);
    }

    public void loadData(NewsDetailBean newsDetailBean, NewsDetailActivity newsDetailActivity) {
        if (newsDetailBean.getCss() != null && newsDetailBean.getCss().size() > 0) {
            downloadCJ(newsDetailBean, newsDetailActivity);
        } else {
            loadData(newsDetailBean.getBody(), "text/html; charset=UTF-8", null);
        }
    }

    public void loadData(NewsDetailBean newsDetailBean) {
        String htmlData = "";
        if (newsDetailBean.getCss() != null && newsDetailBean.getCss().size() > 0) {
            htmlData += HtmlUtil.createCssTag(newsDetailBean.getCss());
        }
        htmlData += newsDetailBean.getBody();
        if (newsDetailBean.getJs() != null && newsDetailBean.getJs().size() > 0) {
            htmlData += HtmlUtil.createJsTag(newsDetailBean.getJs());
        }
        loadData(htmlData, "text/html; charset=UTF-8", null);
    }

    public void downloadCJ(final NewsDetailBean newsDetailBean, final NewsDetailActivity newsDetailActivity) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(newsDetailBean.getCss().get(0)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final File cssFile = new File(Constant.Storage.WEB_DIR + newsDetailBean.getId() + ".css");
                if (!cssFile.getParentFile().exists()) {
                    cssFile.getParentFile().mkdirs();
                }
                if (!cssFile.exists()) {
                    cssFile.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(cssFile);
                byte[] bytes = response.body().string().replace(".headline .img-place-holder {\n" +
                        "  height: 200px;\n" +
                        "}", "").getBytes();
                fileOutputStream.write(bytes);
                final String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\""
                        + cssFile.getName() + "\" />" + newsDetailBean.getBody();
                Timber.e("htmlData: %s" + htmlData);
                new Handler(Looper.getMainLooper())
                        .post(new Runnable() {
                            @Override
                            public void run() {
                                loadDataWithBaseURL("file:///" + cssFile.getParentFile().getAbsolutePath() + "/"
                                        , htmlData, "text/html; charset=UTF-8", null, null);
                            }
                        });
                /*newsDetailActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadDataWithBaseURL("file:///" + cssFile.getParentFile().getAbsolutePath() + "/"
                                , htmlData, "text/html; charset=UTF-8", null, null);
                    }
                });*/
            }
        });
    }

}
