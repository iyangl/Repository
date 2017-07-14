package com.ly.example.myapplication2.cache;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.ZhihuDailyApi;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.app;
import com.ly.example.myapplication2.utils.FileUtils;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import rx.Observable;
import rx.functions.Func1;

/**
 * author:ly
 * date:  2017/7/14 15:11
 * desc:
 */
public class Repository {

    private ZhihuDailyApi api;
    private CacheProviders cacheProviders;
    private static Repository instance;
    private static final String cacheDir = app.getInstance().getCacheDir().getAbsolutePath() + "DailyZhiHu/";

    private Repository() {
        api = ApiFactory.getApi();
        cacheProviders = new RxCache.Builder()
                .persistence(FileUtils.makeFile(cacheDir), new GsonSpeaker())
                .using(CacheProviders.class);
    }

    public static Repository getInstance() {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    return new Repository();
                }
            }
        }
        return instance;
    }

    public Observable<NewsBean> lastNews(String date, boolean isUpdate) {
        return cacheProviders.lastNews(api.lastNews(), new DynamicKey(date), new EvictDynamicKey(isUpdate))
                .map(new CacheFunc1<NewsBean>());
    }

    public Observable<NewsBean> before(String date, boolean isUpdate) {
        return cacheProviders.before(api.before(date), new DynamicKey(date), new EvictDynamicKey(isUpdate))
                .map(new CacheFunc1<NewsBean>());
    }

    private class CacheFunc1<T> implements Func1<Reply<T>, T> {
        @Override
        public T call(Reply<T> o) {
            return o.getData();
        }
    }

}
