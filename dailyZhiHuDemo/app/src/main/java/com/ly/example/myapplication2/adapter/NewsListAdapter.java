package com.ly.example.myapplication2.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.api.apibean.StoriesBean;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.databinding.ItemBannerImagesBinding;
import com.ly.example.myapplication2.databinding.ItemMainNewsConvenientbannerBinding;
import com.ly.example.myapplication2.databinding.ItemMainNewsDateBinding;
import com.ly.example.myapplication2.databinding.ItemMainNewsListBinding;
import com.ly.example.myapplication2.databinding.ItemMainThemeHeaderBinding;
import com.ly.example.myapplication2.utils.CommonUtils;

import java.util.List;

import timber.log.Timber;


public class NewsListAdapter extends BaseRecyclerViewAdapter<Object, NewsListAdapter.NewsBaseViewHolder> implements
        View.OnClickListener {
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

    private com.ly.example.myapplication2.adapter.OnItemClickListener<Integer> onItemClickListener;


    @Override
    public NewsListAdapter.NewsBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NEWS_BANNER:
                View bannerView = View.inflate(parent.getContext(), R.layout.item_main_news_convenientbanner, null);
                return new NewsBannerViewHolder(bannerView);
            case NEWS_DATE:
                View dateView = View.inflate(parent.getContext(), R.layout.item_main_news_date, null);
                return new NewsDateViewHolder(dateView);
            case NEWS_LIST:
                View listView = View.inflate(parent.getContext(), R.layout.item_main_news_list, null);
                listView.setOnClickListener(this);
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
        holder.itemView.setTag(position);
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

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Object o = dataLists.get(tag);
        Timber.e("NewsListAdapter onClick: %s", o);
        if (onItemClickListener != null) {
            if (o instanceof StoriesBean) {
                int id = ((StoriesBean) o).getId();
                onItemClickListener.onClick(v, id);
            }
        }
    }

    public void setOnItemClickListener(com.ly.example.myapplication2.adapter.OnItemClickListener<Integer>
                                               onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    private class NewsBannerViewHolder extends NewsBaseViewHolder {
        private ItemMainNewsConvenientbannerBinding binding;

        NewsBannerViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(final Object topStoriesBeanList) {
            Timber.i("NewsDateViewHolder bind: %s", topStoriesBeanList);
            if (!(topStoriesBeanList instanceof List)) {
                return;
            }
            binding.convenientBanner.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT
                    , CommonUtils.getDimension(R.dimen.x200)));
            binding.convenientBanner.setPages(new CBViewHolderCreator<ImageHolderView>() {
                @Override
                public ImageHolderView createHolder() {
                    return new ImageHolderView();
                }
            }, (List<NewsBean.TopStoriesBean>) topStoriesBeanList)
                    .setPointViewVisible(true)
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setPageIndicator(new int[]{R.drawable.dot_radius_selected, R.drawable.dot_radius_unselected})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Timber.e("convenientBanner onItemClick : %s", ((List) topStoriesBeanList).get(position));
                            if (((List) topStoriesBeanList).get(position) instanceof NewsBean.TopStoriesBean) {
                                int id = ((NewsBean.TopStoriesBean) ((List) topStoriesBeanList).get(position)).getId();
                                if (onItemClickListener != null) {
                                    onItemClickListener.onClick(null, id);
                                }
                            }
                        }
                    });
            if (!binding.convenientBanner.isTurning()) {
                binding.convenientBanner.startTurning(3000);
            }
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

    private static class ImageHolderView implements Holder<NewsBean.TopStoriesBean> {
        private ItemBannerImagesBinding binding;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_images, null, false);
            binding = DataBindingUtil.bind(view);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, NewsBean.TopStoriesBean data) {
            binding.setImageurl(data.getImage());
            binding.setTitle(data.getTitle());
        }
    }
}
