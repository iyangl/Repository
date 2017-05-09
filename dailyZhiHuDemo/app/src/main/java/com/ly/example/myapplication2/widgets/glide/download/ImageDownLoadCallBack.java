package com.ly.example.myapplication2.widgets.glide.download;

import android.graphics.Bitmap;

/**
 * Note:
 * Author:leolee
 * Time:2017/5/9
 */
public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed(Throwable e);
}