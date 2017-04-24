package com.ly.example.myapplication2.mvp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ly.example.myapplication2.MainActivity;
import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.databinding.ActivitySplashBinding;
import com.ly.example.myapplication2.mvp.presenter.SplashPresenter;
import com.ly.example.myapplication2.mvp.view.iview.ISplashView;
import com.ly.example.myapplication2.utils.Constant;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class SplashActivity extends AppCompatActivity implements ISplashView {

    private static final String TAG = "SplashActivity";
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.prefetchLaunchImages();
    }

    @Override
    public void toMainActivity(final Long seconds, final boolean needUpdate) {
        Timber.tag(TAG).i("toMainActivity  seconds: %l  needUpdate: %b", seconds, needUpdate);
        Observable
                .create(new Observable.OnSubscribe<Long>() {
                    @Override
                    public void call(Subscriber<? super Long> subscriber) {
                        subscriber.onNext(seconds);
                        subscriber.onCompleted();
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag(TAG).e("toMainActivity  onError: %s", e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Timber.tag(TAG).e("toMainActivity  onNext");
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra(Constant.Share_prf.HAS_LAUNCH_IMAGES, needUpdate);
                        startActivity(intent);
                        finish();
                    }
                });
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
