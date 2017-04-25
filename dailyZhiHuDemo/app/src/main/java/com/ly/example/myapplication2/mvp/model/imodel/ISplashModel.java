package com.ly.example.myapplication2.mvp.model.imodel;

import com.ly.example.myapplication2.api.apibean.CreativesListBean;

public interface ISplashModel {

    CreativesListBean hasLaunchImages();

    boolean needUpdateLaunchImages(CreativesListBean creativesListBean);
}
