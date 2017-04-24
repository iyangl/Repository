package com.ly.example.myapplication2.mvp.model;

import android.text.TextUtils;

import com.ly.example.myapplication2.api.apibean.CreativesBean;
import com.ly.example.myapplication2.mvp.model.imodel.ISplashModel;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.GsonUtil;
import com.ly.example.myapplication2.utils.SPUtil;

import java.util.List;

import timber.log.Timber;

public class SplashModel implements ISplashModel {
    private static final String TAG = "SplashModel";

    @Override
    public CreativesBean hasLaunchImages() {
        Timber.tag(TAG).i("hasLaunchImages");
        String images = (String) SPUtil.get(Constant.Share_prf.LAUNCH_IMAGES, "");
        if (!TextUtils.isEmpty(images)) {
            List<CreativesBean> creativesBeanList = GsonUtil.GsonToList(images, CreativesBean.class);
            if (creativesBeanList != null && creativesBeanList.size() > 0) {
                return creativesBeanList.get(0);
            }
        }
        return null;
    }

    @Override
    public boolean needUpdateLaunchImages(CreativesBean creativesBean) {
        Timber.tag(TAG).i("needUpdateLaunchImages  creativesBean: %s", creativesBean);
        return creativesBean.getStart_time() - System.currentTimeMillis() >= 1000 * 60 * 60 * 24;
    }
}
