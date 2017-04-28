package com.ly.example.myapplication2.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.ThemesBean;
import com.ly.example.myapplication2.databinding.ItemThemesHeaderBinding;
import com.ly.example.myapplication2.databinding.ItemThemesListBinding;
import com.ly.example.myapplication2.databinding.ItemThmeesHomeBinding;

public class ThemesListAdapter extends BaseRecyclerViewAdapter<ThemesBean.OthersBean,
        ThemesListAdapter.BaseThemesViewHolder> implements View.OnClickListener {
    private static final int THEMES_HEADER = 1;
    private static final int THEMES_HOME = 2;
    private static final int THEMES_LIST = 3;

    private OnItemClickListener onItemClickListener;
    private int lastSelectedPosition = 1;

    @Override
    public ThemesListAdapter.BaseThemesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case THEMES_HEADER:
                View header = View.inflate(parent.getContext(), R.layout.item_themes_header, null);
                return new ThemesHeaderViewHolder(header);
            case THEMES_HOME:
                View home = View.inflate(parent.getContext(), R.layout.item_thmees_home, null);
                home.setOnClickListener(this);
                return new ThemesHomeViewHolder(home);
            case THEMES_LIST:
                View list = View.inflate(parent.getContext(), R.layout.item_themes_list, null);
                list.setOnClickListener(this);
                return new ThemesListViewHolder(list);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ThemesListAdapter.BaseThemesViewHolder holder, int position) {
        if (position > 1) {
            holder.bind(dataLists.get(position - 2));
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataLists.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return THEMES_HEADER;
        } else if (position == 1) {
            return THEMES_HOME;
        } else if (position < dataLists.size() + 2) {
            return THEMES_LIST;
        }
        return super.getItemViewType(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int tag = (int) v.getTag();
            onItemClickListener.onClick(v, tag, lastSelectedPosition);
            lastSelectedPosition = tag;
        }
    }

    static class BaseThemesViewHolder extends RecyclerView.ViewHolder {

        BaseThemesViewHolder(View itemView) {
            super(itemView);
        }

        void bind(Object o) {

        }
    }

    private static class ThemesHeaderViewHolder extends BaseThemesViewHolder {
        private ItemThemesHeaderBinding binding;

        ThemesHeaderViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.ivThemesDownload.setCompoundDrawablePadding(0);
            binding.ivThemesFavor.setCompoundDrawablePadding(0);
        }
    }

    private static class ThemesHomeViewHolder extends BaseThemesViewHolder {
        private ItemThmeesHomeBinding binding;

        ThemesHomeViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.rlThemesHome.setSelected(true);
        }
    }

    private static class ThemesListViewHolder extends BaseThemesViewHolder {
        private ItemThemesListBinding binding;

        ThemesListViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override
        void bind(Object o) {
            if (!(o instanceof ThemesBean.OthersBean)) {
                return;
            }
            binding.setOthers((ThemesBean.OthersBean) o);
            binding.executePendingBindings();
        }
    }

}
