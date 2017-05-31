package com.ly.example.myapplication2.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.CommentsBean;
import com.ly.example.myapplication2.databinding.ItemCommentsCommentBinding;

public class CommentsAdapter extends BaseRecyclerViewAdapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_comments_comment, null);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentViewHolder) {
            ((CommentViewHolder) holder).bind((CommentsBean.CommentBean) dataLists.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private ItemCommentsCommentBinding binding;

        public CommentViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(CommentsBean.CommentBean commentBean) {
            binding.setComment(commentBean);
            binding.setLikes(String.valueOf(commentBean.getLikes()));
            binding.setVoted(commentBean.getVoted());
            binding.executePendingBindings();
        }
    }
}
