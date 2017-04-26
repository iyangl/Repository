package com.ly.example.myapplication2.mvp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.adapter.NewsListAdapter;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.databinding.ActivityMainBinding;
import com.ly.example.myapplication2.mvp.presenter.MainPresenter;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements IMainView {

    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;
    private NewsListAdapter newsListAdapter;
    private boolean isLoading;

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
        initDrawerLayout();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        binding.srfMain.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        binding.srfMain.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.loadNewsData(true);
            }
        });
    }

    private void initDrawerLayout() {

    }

    private void initRecyclerView() {
        binding.rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter();
        binding.rvMain.setAdapter(newsListAdapter);
        binding.rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == newsListAdapter.getItemCount()) {

                    boolean isRefreshing = binding.srfMain.isRefreshing();
                    if (isRefreshing) {
                        newsListAdapter.notifyItemRemoved(newsListAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mainPresenter.loadBeforeData("20170426");
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initEvent() {
        initPrefetchLaunchImages(mainPresenter);

        mainPresenter.loadNewsData(true);
    }

    private void initPrefetchLaunchImages(MainPresenter mainPresenter) {
        boolean need_prefetch_images = getIntent().getBooleanExtra(
                Constant.Intent_Extra.NEED_PREFETCH_IMAGES, false);
        mainPresenter.prefetchLaunchImages(need_prefetch_images);
    }


    @Override
    public void loadNewsData(NewsBean newsBean, boolean isClear) {
        isLoading = false;
        binding.srfMain.setRefreshing(false);
        newsListAdapter.loadNewsData(newsBean, isClear);
    }

    @Override
    public void loadNewsError(Throwable throwable) {
        isLoading = false;
        binding.srfMain.setRefreshing(false);
        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        ToastUtil.showErrorMsg(throwable.getMessage());
    }
}
