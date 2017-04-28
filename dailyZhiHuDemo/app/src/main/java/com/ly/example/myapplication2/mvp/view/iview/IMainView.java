package com.ly.example.myapplication2.mvp.view.iview;

import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;

public interface IMainView {

    void loadNewsData(NewsBean newsBean, boolean isClear);

    void loadNewsError(Throwable throwable);

    void loadThemesData(ThemesBean themesBean);

}
