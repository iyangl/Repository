package com.ly.example.myapplication2.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.widgets.CustomDialog;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {
    private CompositeSubscription compositeSubscription;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customDialog = new CustomDialog(this, R.style.CustomDialog);
    }

    protected void showLoading() {
        if (!customDialog.isShowing()) {
            customDialog.show();
        }
    }

    protected void dismiss() {
        if (customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    protected void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    /**
     * unSubscribe compositeSubscription内所有Subscription
     * 且以后再添加的Subscription均不能被Subscribe
     */
    protected void removeAllSubscriptions() {
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    /**
     * unSubscribe compositeSubscription内所有Subscription
     */
    protected void clearAllSubscriptions() {
        if (compositeSubscription != null && compositeSubscription.isUnsubscribed()) {
            compositeSubscription.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeAllSubscriptions();
    }
}
