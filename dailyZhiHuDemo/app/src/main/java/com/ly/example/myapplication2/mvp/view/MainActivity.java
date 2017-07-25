package com.ly.example.myapplication2.mvp.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Toast;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.adapter.NewsListAdapter;
import com.ly.example.myapplication2.adapter.ThemesListAdapter;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.StoriesBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.databinding.ActivityMainBinding;
import com.ly.example.myapplication2.mvp.presenter.MainPresenter;
import com.ly.example.myapplication2.mvp.view.iview.IMainView;
import com.ly.example.myapplication2.utils.CommonUtils;
import com.ly.example.myapplication2.utils.Constant;
import com.ly.example.myapplication2.utils.StringFormat;
import com.ly.example.myapplication2.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements IMainView {

    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;
    private NewsListAdapter newsListAdapter;
    /**
     * 是否正在上拉加载更多
     */
    private boolean isLoading;
    /**
     * 记录将要加载多少天之前的信息
     */
    private int beforeDays = 0;
    private ThemesListAdapter themesListAdapter;
    private int selectedThemePosition = -1;

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
        initSwipeRefreshLayout();
        initToolbar();
        initLeftRecyclerView();
        initDrawerToggle();
    }

    private void initLeftRecyclerView() {
        binding.rvMainThemes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        themesListAdapter = new ThemesListAdapter();
        binding.rvMainThemes.setAdapter(themesListAdapter);
        themesListAdapter.setOnItemClickListener((view, positions) -> {
            selectedThemePosition = positions[0] - 2;
            if (positions[0] != positions[1]) {
                if (positions[0] == 1) {
                    mainPresenter.loadNewsData(StringFormat.getDateDaysBefore(0), true, true);
                    binding.toolbarMain.toolbar.setTitle(R.string.home);
                } else {
                    mainPresenter.loadThemeNewsListData(
                            themesListAdapter.getItem(selectedThemePosition).getId(), true);
                    binding.toolbarMain.toolbar.setTitle(
                            themesListAdapter.getItem(selectedThemePosition).getName());
                }
            }
            binding.dlMain.closeDrawer(Gravity.START);
        });
    }

    private void initToolbar() {
        //        binding.toolbarMain.toolbar.setNavigationIcon(R.drawable.drawer_arrow);
        binding.toolbarMain.toolbar.setTitle(R.string.home);
        binding.toolbarMain.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.toolbarMain.toolbar.inflateMenu(R.menu.toolbar_menu);
        /*binding.toolbarMain.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dlMain.openDrawer(Gravity.START);
                if (themesListAdapter.isEmpty()) {
                    mainPresenter.loadThemesData();
                }
            }
        });*/
        /*setSupportActionBar(binding.toolbarMain.toolbar);*/
        binding.toolbarMain.toolbar.setPopupTheme(R.style.Theme_Design_Light);
    }

    private void initDrawerToggle() {
        // 参数：开启抽屉的activity、DrawerLayout的对象、toolbar按钮打开关闭的对象、描述open drawer、描述close drawer
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                binding.dlMain, binding.toolbarMain.toolbar, R.string.open, R.string.close);
        // 添加抽屉按钮，通过点击按钮实现打开和关闭功能; 如果不想要抽屉按钮，只允许在侧边边界拉出侧边栏，可以不写此行代码
        mDrawerToggle.getDrawerArrowDrawable().setColor(CommonUtils.getColor(R.color.white));
        mDrawerToggle.syncState();
        // 设置按钮的动画效果; 如果不想要打开关闭抽屉时的箭头动画效果，可以不写此行代码
        binding.dlMain.setDrawerListener(mDrawerToggle);
    }

    private void initSwipeRefreshLayout() {
        binding.srfMain.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        binding.srfMain.setOnRefreshListener(() -> {
            if (selectedThemePosition < 0) {
                mainPresenter.loadNewsData(StringFormat.getDateDaysBefore(0), true, true);
                beforeDays = 0;
            } else {
                mainPresenter.loadThemeNewsListData(
                        themesListAdapter.getItem(selectedThemePosition).getId(), true);
            }
        });
    }

    private void initRecyclerView() {
        binding.rvMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newsListAdapter = new NewsListAdapter();
        binding.rvMain.setAdapter(newsListAdapter);
        newsListAdapter.setOnItemClickListener((view, positions) -> {
            Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
            intent.putExtra(Constant.Intent_Extra.NEWS_ID, positions[0]);
            startActivity(intent);
        });
        binding.rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPosition;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                changeTitleByScroll(recyclerView);
                lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (lastVisibleItemPosition + 1 == newsListAdapter.getItemCount()
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {

                    boolean isRefreshing = binding.srfMain.isRefreshing();
                    if (isRefreshing) {
                        newsListAdapter.notifyItemRemoved(newsListAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        if (selectedThemePosition < 0) {
                            mainPresenter.loadBeforeData(StringFormat.getDateDaysBefore(beforeDays));
                            beforeDays++;
                        } else {
                            mainPresenter.loadThemeNewsListBefore(
                                    themesListAdapter.getItem(selectedThemePosition).getId());
                            beforeDays = 0;
                        }
                    }
                }
            }
        });
    }

    private int lastVisibleItemPosition;

    /**
     * 随着滑动更新toolbar标题为当前News日期
     *
     * @param recyclerView recyclerView
     */
    private void changeTitleByScroll(RecyclerView recyclerView) {
        if (selectedThemePosition < 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstVisibleItemPosition();
            if (firstVisibleItemPosition > 0) {
                Object item;
                if (lastVisibleItemPosition > firstVisibleItemPosition) {
                    item = newsListAdapter.getItem(firstVisibleItemPosition + 1);
                    if (item instanceof String) {
                        item = StringFormat.getTomorrowDate((String) item);
                        binding.toolbarMain.toolbar.setTitle(StringFormat.formatNewsDate((String) item));
                    }
                } else {
                    item = newsListAdapter.getItem(firstVisibleItemPosition);
                    if (item instanceof StoriesBean) {
                        binding.toolbarMain.toolbar.setTitle(
                                StringFormat.formatNewsDate(((StoriesBean) item).getDate()));
                    }
                }
            } else {
                binding.toolbarMain.toolbar.setTitle(R.string.home);
            }
            lastVisibleItemPosition = firstVisibleItemPosition;
        }
    }

    private void initEvent() {
        initPrefetchLaunchImages(mainPresenter);
        mainPresenter.loadThemesData();
        mainPresenter.loadNewsData(StringFormat.getDateDaysBefore(0), false, true);
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
        if (isClear) {
            binding.rvMain.scrollToPosition(0);
        }
    }

    @Override
    public void loadNewsError(Throwable throwable) {
        isLoading = false;
        binding.srfMain.setRefreshing(false);
        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        ToastUtil.showErrorMsg(throwable.getMessage());
    }

    @Override
    public void loadThemesData(ThemesBean themesBean) {
        themesListAdapter.setDataLists(themesBean.getOthers());
    }

    @Override
    public void loadThemesDataSuccess(ThemeNewsBean themeNewsBean, boolean isClear) {
        isLoading = false;
        binding.srfMain.setRefreshing(false);
        newsListAdapter.loadNewsData(themeNewsBean, isClear);
        if (isClear) {
            binding.rvMain.scrollToPosition(0);
        }
    }
}
