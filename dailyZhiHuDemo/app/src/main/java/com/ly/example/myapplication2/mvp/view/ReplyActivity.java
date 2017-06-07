package com.ly.example.myapplication2.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.mvp.contact.ReplyContact;


public class ReplyActivity extends BaseActivity implements ReplyContact.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
    }

    @Override
    public void onLoadingShow() {

    }

    @Override
    public void onLoadingDismiss() {

    }
}
