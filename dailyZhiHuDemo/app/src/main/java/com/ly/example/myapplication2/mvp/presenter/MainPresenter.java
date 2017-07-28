package com.ly.example.myapplication2.mvp.presenter;

import android.content.Context;

import com.ly.example.myapplication2.api.ApiFactory;
import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.MainModel;
import com.ly.example.myapplication2.mvp.model.imodel.IMainModel;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public void loadNewsData(String cacheDate, boolean isUpdate, final boolean isClear) {
        iMainModel.loadNewsData(cacheDate, isUpdate, new RequestImp<NewsBean>() {
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

    public void loadDefaultImg(Context context) {
        File file = new File(Constant.Storage.WEB_CACHE_DIR + Constant.Storage.DEFAULT_IMG);
        if (file.exists()) {
            return;
        } else {
            FileUtils.makeDirs(file.getAbsolutePath());
        }
        //从asserts文件夹下读取默认图片到WEB_CACHE_DIR下
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            inputStream = context.getAssets().open(Constant.Storage.DEFAULT_IMG);
            if (inputStream != null) {
                if (!file.exists()) {
                    FileUtils.makeDirs(file.getAbsolutePath());
                }
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();//刷新缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}