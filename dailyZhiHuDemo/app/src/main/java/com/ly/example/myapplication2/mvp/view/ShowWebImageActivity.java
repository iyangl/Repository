package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.databinding.ActivityShowWebImageBinding;
import com.ly.example.myapplication2.utils.Constant;


public class ShowWebImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShowWebImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_web_image);
        String url = getIntent().getStringExtra(Constant.Intent_Extra.WEB_IMAGE_URL);
        binding.setUrl(url);
    }
}
