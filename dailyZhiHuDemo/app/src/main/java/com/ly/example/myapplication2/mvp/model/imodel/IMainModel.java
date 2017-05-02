package com.ly.example.myapplication2.mvp.model.imodel;

import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.mvp.RequestImp;

public interface IMainModel {

    void savePrefetchLaunchImages(CreativesListBean creativesListBeanList);

    void loadNewsData(RequestImp<NewsBean> requestImp);

    void loadBeforeData(String before, RequestImp<NewsBean> requestImp);

    void loadThemesData(RequestImp<ThemesBean> requestImp);

    void loadThemeNewsListData(int id, RequestImp<ThemeNewsBean> requestImp);

    void loadThemeNewsListBefore(int themeId, int lastThemeNewsId, RequestImp<ThemeNewsBean> requestImp);
}
