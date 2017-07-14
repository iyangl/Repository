package com.ly.example.myapplication2.cache;

import com.ly.example.myapplication2.api.apibean.NewsBean;

import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.EvictDynamicKeyGroup;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * author:ly
 * date:  2017/7/14 11:46
 * desc: 缓存 API 接口
 * {@link LifeCache} 设置缓存过期时间. 如果没有设置@LifeCache ,
 * 数据将被永久缓存理除非你使用了 EvictProvider, EvictDynamicKey or EvictDynamicKeyGroup .
 * {@link EvictProvider} 可以明确地清理清理所有缓存数据.
 * {@link EvictDynamicKey} 可以明确地清理指定的数据 DynamicKey.
 * {@link EvictDynamicKeyGroup} 允许明确地清理一组特定的数据. DynamicKeyGroup.
 * {@link DynamicKey} 驱逐与一个特定的键使用 EvictDynamicKey 相关的数据。比如分页，排序或筛选要求
 * {@link DynamicKeyGroup}。驱逐一组与 key 关联的数据，使用 EvictDynamicKeyGroup。比如分页，排序或筛选要求
 */
public interface CacheProviders {
    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    Observable<Reply<NewsBean>> lastNews(Observable<NewsBean> oRepos, DynamicKey userName, EvictDynamicKey
            evictDynamicKey);

    @LifeCache(duration = 7, timeUnit = TimeUnit.DAYS)
    Observable<Reply<NewsBean>> before(Observable<NewsBean> oRepos, DynamicKey userName, EvictDynamicKey
            evictDynamicKey);


}
