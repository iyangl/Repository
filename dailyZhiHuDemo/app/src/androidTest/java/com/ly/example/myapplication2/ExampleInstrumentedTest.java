package com.ly.example.myapplication2;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.CommentsModel;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.StringFormat;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        System.out.println("" + Constant.Storage.PACK_CACHE);
        assertEquals("com.ly.example.myapplication2", appContext.getPackageName());
    }

    @Test
    public void testStringFormat() {
        String newsDate = StringFormat.formatNewsDate("20170426");
        System.out.println("" + newsDate);
        String dateBefore = StringFormat.getDateDaysBefore(3);
        System.out.println("" + dateBefore);
    }

    @Test
    public void testDownloadCss() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3";
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse11 : " + response.body().string());
            }
        });
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println("onResponse : " + response.body().string());
            } else {
                System.out.println("Unexpected code " + response);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Test
    public void testApiCJ() {
        String url = "http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3";
        ApiFactory.getApi().downloadCJ(url)
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError :" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext: " + s);
                    }
                });
    }

    @Test
    public void downloadCJ() {

        OkHttpClient okHttpClient = new OkHttpClient();
        final NewsDetailBean newsDetailBean = new NewsDetailBean();
        newsDetailBean.setBody("11111111");
        List<String> css = new ArrayList<>();
        String url = "http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3";
        css.add(url);
        newsDetailBean.setCss(css);
        Request request = new Request.Builder().url(newsDetailBean.getCss().get(0)).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File cssFile = new File(Constant.Storage.WEB_CACHE_DIR + newsDetailBean.getId() + ".css");
                if (!cssFile.exists()) {
                    cssFile.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(cssFile);
                fileOutputStream.write(response.body().bytes());
                String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\""
                        + cssFile.getName() + ".css\" />";
                System.out.println("onResponse: " + htmlData);
            }
        });
    }

    @Test
    public void testLongComments() {
        CommentsModel commentModel = new CommentsModel();
        int newsId = 9458195;
        commentModel.loadLongComments(newsId, new RequestImp<CommentsBean>() {
            @Override
            public void onSuccess(CommentsBean data) {
                Toast.makeText(app.getInstance(), data.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
