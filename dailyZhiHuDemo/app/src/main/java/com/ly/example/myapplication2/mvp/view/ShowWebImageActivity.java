package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.databinding.ActivityWebImageBinding;
import com.ly.example.myapplication2.utils.CommonUtils;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.widgets.glide.download.DownLoadImageService;
import com.ly.example.myapplication2.widgets.glide.download.ImageDownLoadCallBack;

import timber.log.Timber;


public class ShowWebImageActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_web_image);
        final String url = getIntent().getStringExtra(Constant.Intent_Extra.WEB_IMAGE_URL);
        binding.setUrl(url);

        toolbar = binding.imageToolbar.toolbar;
        toolbar.setBackgroundColor(CommonUtils.getColor(R.color.black));
        toolbar.setNavigationIcon(R.drawable.back_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowWebImageActivity.this.finish();
            }
        });

        toolbar.inflateMenu(R.menu.toolbar_web_image_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                downImages(url);
                return false;
            }
        });

        binding.llWebImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbar.getVisibility() == View.GONE) {
                    toolbar.setVisibility(View.VISIBLE);
                } else {
                    toolbar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void downImages(String url) {
        new Thread(new DownLoadImageService(this, url, new ImageDownLoadCallBack() {

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                Timber.i("保存成功");
            }

            @Override
            public void onDownLoadFailed(Throwable e) {
                Timber.e(e.getMessage());
            }
        })).start();
    }
}
