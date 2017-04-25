package com.ly.example.myapplication2.mvp.model;

import com.ly.example.myapplication2.api.apibean.CreativesListBean;
import com.ly.example.myapplication2.mvp.model.imodel.IMainModel;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.GsonUtil;
import com.ly.example.myapplication2.utils.SPUtil;

public class MainModel implements IMainModel {
    @Override
    public void savePrefetchLaunchImages(CreativesListBean creativesListBeanList) {
        if (creativesListBeanList != null && creativesListBeanList.getCreatives() != null
                && creativesListBeanList.getCreatives().size() > 0) {
            SPUtil.put(Constant.Share_prf.LAUNCH_IMAGES, GsonUtil.GsonString(creativesListBeanList));
        }
    }
}
