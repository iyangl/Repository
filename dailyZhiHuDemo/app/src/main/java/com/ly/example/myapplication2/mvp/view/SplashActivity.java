package com.ly.example.myapplication2.mvp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.databinding.ActivitySplashBinding;
import com.ly.example.myapplication2.mvp.presenter.SplashPresenter;
import com.ly.example.myapplication2.mvp.view.iview.ISplashView;
import com.ly.example.myapplication2.utils.Constant;

import timber.log.Timber;


public class SplashActivity extends AppCompatActivity implements ISplashView {

    private static final String TAG = "SplashActivity";
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        //接口失效，懒得找图片就白屏吧
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.prefetchLaunchImages();
    }

    @Override
    public void toMainActivity(final Long seconds, final boolean needUpdate) {
        Timber.tag(TAG).i("toMainActivity  seconds: %d  needUpdate: %b", seconds, needUpdate);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(Constant.Intent_Extra.NEED_PREFETCH_IMAGES, needUpdate);
            startActivity(intent);
            finish();
        }, seconds);
    }

    @Override
    public void loadLaunchImages(String imageUrl) {
        Timber.tag(TAG).i("loadLaunchImages  imageUrl: %s", imageUrl);
        Glide.with(SplashActivity.this)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(binding.ivSplash);
    }

}
