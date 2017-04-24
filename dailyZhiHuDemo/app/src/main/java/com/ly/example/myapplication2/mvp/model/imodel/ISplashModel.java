package com.ly.example.myapplication2.mvp.model.imodel;

import com.ly.example.myapplication2.api.apibean.CreativesBean;

public interface ISplashModel {

    CreativesBean hasLaunchImages();

    boolean needUpdateLaunchImages(CreativesBean creativesBean);
}
