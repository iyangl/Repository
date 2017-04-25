package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.adapter.NewsListAdapter;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.databinding.ActivityMainBinding;
import com.ly.example.myapplication2.mvp.presenter.MainPresenter;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;
import com.ly.example.myapplication2.utils.Constant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainView {

    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainPresenter = new MainPresenter(this);

        initView();
        initEvent();
    }

    private void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        NewsBean.StoriesBean storiesBean = new NewsBean.StoriesBean();
        storiesBean.setTitle("11111111111111111");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("https://pic1.zhimg.com/v2-4330d0d189a12a81557a21ce46eef154.jpg");
        storiesBean.setImages(strings);
        ArrayList<NewsBean.StoriesBean> storiesBeen = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            storiesBeen.add(storiesBean);
        }
        binding.rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvMain.setAdapter(new NewsListAdapter(storiesBeen));
    }

    private void initEvent() {
        initPrefetchLaunchImages(mainPresenter);
    }

    private void initPrefetchLaunchImages(MainPresenter mainPresenter) {
        boolean need_prefetch_images = getIntent().getBooleanExtra(
                Constant.Intent_Extra.NEED_PREFETCH_IMAGES, false);
        mainPresenter.prefetchLaunchImages(need_prefetch_images);
    }


}
