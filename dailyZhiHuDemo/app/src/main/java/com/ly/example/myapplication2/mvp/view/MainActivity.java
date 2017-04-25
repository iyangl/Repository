package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.databinding.ActivityMainBinding;
import com.ly.example.myapplication2.mvp.presenter.MainPresenter;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;
import com.ly.example.myapplication2.utils.Constant;

public class MainActivity extends AppCompatActivity implements IMainView {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainPresenter mainPresenter = new MainPresenter(this);

        boolean need_prefetch_images = getIntent().getBooleanExtra(
                Constant.Intent_Extra.NEED_PREFETCH_IMAGES, false);
        mainPresenter.prefetchLaunchImages(need_prefetch_images);
    }
}
