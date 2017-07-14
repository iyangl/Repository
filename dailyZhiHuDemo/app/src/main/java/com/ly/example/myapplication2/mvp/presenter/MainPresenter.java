package com.ly.example.myapplication2.mvp.presenter;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.MainModel;
import com.ly.example.myapplication2.mvp.model.imodel.IMainModel;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;

import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainPresenter {

    private IMainView iMainView;
    private IMainModel iMainModel;
    private int lastThemeNewsId = 1000000;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        this.iMainModel = new MainModel();
    }

    public void prefetchLaunchImages(boolean need_prefetch_images) {
        if (!need_prefetch_images) {
            return;
        }

        ApiFactory.getApi().prefetchLaunchImages("1920*1080")
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<CreativesListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("prefetchLaunchImages__onError: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(CreativesListBean creativesBeen) {
                        Timber.i("creativesBeen : %s", creativesBeen);
                        iMainModel.savePrefetchLaunchImages(creativesBeen);
                    }
                });
    }

    public void loadNewsData(String cacheDate, final boolean isClear) {
        iMainModel.loadNewsData(cacheDate, new RequestImp<NewsBean>() {
            @Override
            public void onSuccess(NewsBean data) {
                iMainView.loadNewsData(data, isClear);
            }

            @Override
            public void onError(Throwable e) {
                iMainView.loadNewsError(e);
            }
        });
    }

    public void loadBeforeData(String before) {
        iMainModel.loadBeforeData(before, new RequestImp<NewsBean>() {
            @Override
            public void onSuccess(NewsBean data) {
                iMainView.loadNewsData(data, false);
            }

            @Override
            public void onError(Throwable e) {
                iMainView.loadNewsError(e);
            }
        });
    }

    public void loadThemesData() {
        iMainModel.loadThemesData(new RequestImp<ThemesBean>() {
            @Override
            public void onSuccess(ThemesBean data) {
                iMainView.loadThemesData(data);
            }

            @Override
            public void onError(Throwable e) {
                iMainView.loadNewsError(e);
            }
        });
    }

    public void loadThemeNewsListData(int id, final boolean isClear) {
        iMainModel.loadThemeNewsListData(id, new RequestImp<ThemeNewsBean>() {
            @Override
            public void onSuccess(ThemeNewsBean data) {
                if (data != null && data.getStories() != null && data.getStories().size() > 0) {
                    lastThemeNewsId = data.getStories().get(data.getStories().size() - 1).getId();
                }
                iMainView.loadThemesDataSuccess(data, isClear);
            }

            @Override
            public void onError(Throwable e) {
                iMainView.loadNewsError(e);
            }
        });
    }

    public void loadThemeNewsListBefore(int themeId) {
        iMainModel.loadThemeNewsListBefore(themeId, lastThemeNewsId, new RequestImp<ThemeNewsBean>() {
            @Override
            public void onSuccess(ThemeNewsBean data) {
                if (data != null && data.getStories() != null && data.getStories().size() > 0) {
                    lastThemeNewsId = data.getStories().get(data.getStories().size() - 1).getId();
                }
                iMainView.loadThemesDataSuccess(data, false);
            }

            @Override
            public void onError(Throwable e) {
                iMainView.loadNewsError(e);
            }
        });
    }
}
