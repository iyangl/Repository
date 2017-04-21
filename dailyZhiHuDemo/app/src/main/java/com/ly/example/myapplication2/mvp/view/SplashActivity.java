package com.ly.example.myapplication2.mvp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ly.example.myapplication2.MainActivity;
import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.app;
import com.ly.example.myapplication2.databinding.ActivitySplashBinding;
import com.ly.example.myapplication2.utils.SPUtil;

public class SplashActivity extends AppCompatActivity {

    public static final String SPLASH_IMAGE = "splash_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        if ("".equals((String) SPUtil.get(app.getInstance(), SPLASH_IMAGE, ""))) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }
}
