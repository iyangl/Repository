package com.ly.example.myapplication2.utils;

import android.Manifest;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class PermissionUtil {

    public interface RequestPermission {
        void onRequestPermissionSuccess();

        void onRequestPermissionFailed();
    }

    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(final RequestPermission requestPermission, RxPermissions rxPermissions) {
        //先确保是否已经申请过摄像头，和写入外部存储的权限
        boolean isPermissionsGranted =
                rxPermissions
                        .isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (isPermissionsGranted) {//已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过，则申请
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                requestPermission.onRequestPermissionSuccess();
                            } else {
                                requestPermission.onRequestPermissionFailed();
                            }
                        }
                    });
        }
    }
}
