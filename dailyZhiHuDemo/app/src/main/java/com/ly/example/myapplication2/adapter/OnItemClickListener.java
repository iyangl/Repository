package com.ly.example.myapplication2.adapter;

import android.view.View;

public interface OnItemClickListener<T> {

    void onClick(View view, T... positions);
}
