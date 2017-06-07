package com.ly.example.myapplication2.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class BaseRecyclerViewHolder<T extends ViewDataBinding, D> extends RecyclerView.ViewHolder {
    protected T binding;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bind(D d) {
        if (binding != null) {
            binding.executePendingBindings();
        }
    }
}
