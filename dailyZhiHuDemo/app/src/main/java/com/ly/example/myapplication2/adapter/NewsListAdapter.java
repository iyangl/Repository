package com.ly.example.myapplication2.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.NewsBean;
import com.ly.example.myapplication2.databinding.ItemMainNewsListBinding;

import java.util.List;

public class NewsListAdapter extends BaseRecyclerViewAdapter<NewsBean.StoriesBean, NewsListAdapter.NewsListViewHolder> {

    public NewsListAdapter() {
        super();
    }

    public NewsListAdapter(List<NewsBean.StoriesBean> storiesBean) {
        super();
        dataLists = storiesBean;
    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_main_news_list, null);
        return new NewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        holder.bind(dataLists.get(position));
    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    public static class NewsListViewHolder extends RecyclerView.ViewHolder {
        private ItemMainNewsListBinding binding;

        public NewsListViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(NewsBean.StoriesBean storiesBean) {
            binding.setStoriesbean(storiesBean);
        }
    }
}
