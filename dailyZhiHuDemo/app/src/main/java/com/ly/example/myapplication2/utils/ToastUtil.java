package com.ly.example.myapplication2.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.app;

public class ToastUtil {

    private static Context context = app.getInstance();// App生命周期中唯一Context，BaseApplication继承Application
    private static LayoutInflater inflater = LayoutInflater.from(context);// 布局加载
    private static View myToastView = inflater.inflate(R.layout.layout_top_toast, null);
    private static TextView msgView = (TextView) myToastView.findViewById(R.id.tv_msg_text);

    private static final int TYPE_CODE_SUCCESS = 0x01;
    private static final int TYPE_CODE_ERROR = 0x02;
    private static final int COLOR_SUCCESS = context.getResources().getColor(R.color.black);
    private static final int COLOR_ERROR = context.getResources().getColor(R.color.colorPrimary);
    private static final int DEFAULT_TIME_DELAY = 50;// 单位：毫秒

    private static Toast toast;// 系统提示类
    private static Handler handler;

    public static void showSuccessMsg(int msgResId) {
        try {
            showSuccessMsg(context.getString(msgResId));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorMsg(int msgResId) {
        try {
            showErrorMsg(context.getString(msgResId));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showSuccessMsg(String msg) {
        showMsg(TYPE_CODE_SUCCESS, msg);
    }

    public static void showErrorMsg(String msg) {
        showMsg(TYPE_CODE_ERROR, msg);
    }

    private static void showMsg(final int typeCode, final String msg) {
        if (context == null//
//                || !ApplicationUtil.isRunningForeground(context)// 如果APP回到后台，则不显示
                || msg == null) {
            return;
        }

        if (toast == null) {// 防止重复提示：不为Null，即全局使用同一个Toast实例
            toast = new Toast(context);
        }

        if (handler == null) {
            handler = new Handler();
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int msgViewBagColor = 0;
                switch (typeCode) {
                    case TYPE_CODE_SUCCESS:
                        msgViewBagColor = COLOR_SUCCESS;
                        break;
                    case TYPE_CODE_ERROR:
                        msgViewBagColor = COLOR_ERROR;
                        break;
                    default:
                        msgViewBagColor = COLOR_SUCCESS;
                        break;
                }
                msgView.setBackgroundColor(msgViewBagColor);
                msgView.setText(msg);
                toast.setView(myToastView);
                toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);// 顶部居中
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();

            }
        }, DEFAULT_TIME_DELAY);
    }

    // 暂不对外提供：主要针对需要在某个时候，取消提示
    private static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
