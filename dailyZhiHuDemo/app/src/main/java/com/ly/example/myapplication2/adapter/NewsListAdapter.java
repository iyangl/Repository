package com.ly.example.myapplication2.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.StoriesBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.databinding.ItemMainNewsBannerBinding;
import com.ly.example.myapplication2.databinding.ItemMainNewsDateBinding;
import com.ly.example.myapplication2.databinding.ItemMainNewsListBinding;
import com.ly.example.myapplication2.databinding.ItemMainThemeHeaderBinding;
import com.ly.example.myapplication2.utils.CommonUtils;
import com.ly.example.myapplication2.utils.widgets.ImageLoader;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class NewsListAdapter extends BaseRecyclerViewAdapter<Object, NewsListAdapter.NewsBaseViewHolder> {
    /**
     * 顶部轮播图
     */
    private static final int NEWS_BANNER = 1;
    /**
     * 时间
     */
    private static final int NEWS_DATE = 2;
    /**
     * 信息列表
     */
    private static final int NEWS_LIST = 3;
    /**
     * 主题头部
     */
    private static final int THEME_HEADER = 4;


    @Override
    public NewsListAdapter.NewsBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NEWS_BANNER:
                View bannerView = View.inflate(parent.getContext(), R.layout.item_main_news_banner, null);
                return new NewsBannerViewHolder(bannerView);
            case NEWS_DATE:
                View dateView = View.inflate(parent.getContext(), R.layout.item_main_news_date, null);
                return new NewsDateViewHolder(dateView);
            case NEWS_LIST:
                View listView = View.inflate(parent.getContext(), R.layout.item_main_news_list, null);
                return new NewsListViewHolder(listView);
            case THEME_HEADER:
                View headerView = View.inflate(parent.getContext(), R.layout.item_main_theme_header, null);
                return new ThemeHeaderViewHolder(headerView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.NewsBaseViewHolder holder, int position) {
        holder.bind(dataLists.get(position));
    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataLists.get(position) instanceof String) {
            return NEWS_DATE;
        } else if (dataLists.get(position) instanceof List) {
            return NEWS_BANNER;
        } else if (dataLists.get(position) instanceof StoriesBean) {
            return NEWS_LIST;
        } else if (dataLists.get(position) instanceof ThemeNewsBean) {
            return THEME_HEADER;
        }
        return -1;
    }

    private static class NewsListViewHolder extends NewsBaseViewHolder {
        private ItemMainNewsListBinding binding;

        NewsListViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Object storiesBean) {
            Timber.i("NewsDateViewHolder bind: %s", storiesBean);
            if (!(storiesBean instanceof StoriesBean)) {
                return;
            }
            binding.setStoriesbean((StoriesBean) storiesBean);
            binding.executePendingBindings();
        }
    }

    private static class NewsBannerViewHolder extends NewsBaseViewHolder {
        private ItemMainNewsBannerBinding binding;

        NewsBannerViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Object topStoriesBeanList) {
            Timber.i("NewsDateViewHolder bind: %s", topStoriesBeanList);
            if (!(topStoriesBeanList instanceof List)) {
                return;
            }
            List<String> imageUrls = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (NewsBean.TopStoriesBean topStoriesBean : (List<NewsBean.TopStoriesBean>) topStoriesBeanList) {
                imageUrls.add(topStoriesBean.getImage());
                titles.add(topStoriesBean.getTitle());
            }
            binding.itemMainNewsBanner.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    CommonUtils.dip2px(binding.itemMainNewsBanner.getContext(), 200)));
            binding.itemMainNewsBanner.setImageLoader(new GlideImageLoader())
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .setImages(imageUrls)
                    .setBannerTitles(titles)
                    .setDelayTime(3000)
                    .start();
            binding.executePendingBindings();
        }
    }

    private static class NewsDateViewHolder extends NewsBaseViewHolder {
        private ItemMainNewsDateBinding binding;

        NewsDateViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Object date) {
            Timber.i("NewsDateViewHolder bind: %s", date);
            if (!(date instanceof String)) {
                return;
            }
            binding.setDate((String) date);
            binding.executePendingBindings();
        }
    }

    static class ThemeHeaderViewHolder extends NewsBaseViewHolder {
        private ItemMainThemeHeaderBinding binding;

        ThemeHeaderViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void bind(Object obj) {
            if (!(obj instanceof ThemeNewsBean)) {
                return;
            }
            binding.setThemenewsbean((ThemeNewsBean) obj);
        }
    }

    static class NewsBaseViewHolder extends RecyclerView.ViewHolder {

        NewsBaseViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Object obj) {
        }
    }

    private static class GlideImageLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            ImageLoader.loadImage(context, (String) path, (ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }

    public void loadNewsData(NewsBean newsBean, boolean isClear) {
        Timber.i("loadNewsData: %s  %b", newsBean, isClear);
        if (newsBean == null) {
            return;
        }
        if (isClear) {
            dataLists.clear();
        }
        if (newsBean.getTop_stories() != null && newsBean.getTop_stories().size() > 0) {
            dataLists.add(newsBean.getTop_stories());
        }
        if (!TextUtils.isEmpty(newsBean.getDate())) {
            dataLists.add(newsBean.getDate());
        }
        if (newsBean.getStories() != null && newsBean.getStories().size() > 0) {
            dataLists.addAll(newsBean.getStories());
        }
        notifyDataSetChanged();
    }

    public void loadNewsData(ThemeNewsBean themeNewsBean, boolean isClear) {
        Timber.i("loadNewsData: %s  %b", themeNewsBean, isClear);
        if (themeNewsBean == null) {
            return;
        }
        if (isClear) {
            dataLists.clear();
            dataLists.add(themeNewsBean);
        }
        if (themeNewsBean.getStories() != null && themeNewsBean.getStories().size() > 0) {
            dataLists.addAll(themeNewsBean.getStories());
        }
        notifyDataSetChanged();
    }
}
