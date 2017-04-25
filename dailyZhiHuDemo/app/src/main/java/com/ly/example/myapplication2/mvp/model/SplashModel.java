package com.ly.example.myapplication2.mvp.model;

import android.text.TextUtils;

import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.mvp.model.imodel.ISplashModel;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.GsonUtil;
import com.ly.example.myapplication2.utils.SPUtil;

import timber.log.Timber;

public class SplashModel implements ISplashModel {
    private static final String TAG = "SplashModel";

    @Override
    public CreativesListBean hasLaunchImages() {
        Timber.tag(TAG).i("hasLaunchImages");
        String images = (String) SPUtil.get(Constant.Share_prf.LAUNCH_IMAGES, "");
        if (!TextUtils.isEmpty(images)) {
            CreativesListBean creativesListBeanList = GsonUtil.GsonToBean(images, CreativesListBean.class);
            if (creativesListBeanList != null && creativesListBeanList.getCreatives() != null
                    && creativesListBeanList.getCreatives().size() > 0) {
                return creativesListBeanList;
            }
        }
        return null;
    }

    @Override
    public boolean needUpdateLaunchImages(CreativesListBean creativesListBean) {
        Timber.tag(TAG).i("needUpdateLaunchImages  creativesListBean: %s", creativesListBean);
        long l = System.currentTimeMillis() / 1000;
        long i = creativesListBean.getCreatives().get(0).getStart_time();
        return System.currentTimeMillis() / 1000
                - creativesListBean.getCreatives().get(0).getStart_time()
                >= 1000 * 60 * 60 * 24;
    }
}
