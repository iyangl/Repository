package com.ly.example.myapplication2.mvp.model;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.imodel.IMainModel;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.GsonUtil;
import com.ly.example.myapplication2.utils.SPUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainModel implements IMainModel {
    @Override
    public void savePrefetchLaunchImages(CreativesListBean creativesListBeanList) {
        if (creativesListBeanList != null && creativesListBeanList.getCreatives() != null
                && creativesListBeanList.getCreatives().size() > 0) {
            SPUtil.put(Constant.Share_prf.LAUNCH_IMAGES, GsonUtil.GsonString(creativesListBeanList));
        }
    }

    @Override
    public void loadNewsData(final RequestImp<NewsBean> requestImp) {
        ApiFactory.getApi().lastNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("loadNewsData onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("loadNewsData onError: %s", e.getMessage());
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        Timber.i("loadNewsData onNext: %s", newsBean);
                        requestImp.onSuccess(newsBean);
                    }
                });
    }

    @Override
    public void loadBeforeData(String before, final RequestImp<NewsBean> requestImp) {
        ApiFactory.getApi().before(before)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        requestImp.onSuccess(newsBean);
                    }
                });
    }

    @Override
    public void loadThemesData(final RequestImp<ThemesBean> requestImp) {
        ApiFactory.getApi().themes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThemesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(ThemesBean themesBean) {
                        requestImp.onSuccess(themesBean);
                    }
                });
    }

    @Override
    public void loadThemeNewsListData(int id, final RequestImp<ThemeNewsBean> requestImp) {
        ApiFactory.getApi().themeNewsList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThemeNewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(ThemeNewsBean themeNewsBean) {
                        requestImp.onSuccess(themeNewsBean);
                    }
                });
    }

    @Override
    public void loadThemeNewsListBefore(int themeId, int lastThemeNewsId, final RequestImp<ThemeNewsBean> requestImp) {
        ApiFactory.getApi().themeNewsListBefore(themeId, lastThemeNewsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThemeNewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        requestImp.onError(e);
                    }

                    @Override
                    public void onNext(ThemeNewsBean themeNewsBean) {
                        requestImp.onSuccess(themeNewsBean);
                    }
                });
    }
}
