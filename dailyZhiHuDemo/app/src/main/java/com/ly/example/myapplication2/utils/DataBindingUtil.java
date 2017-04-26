package com.ly.example.myapplication2.utils;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataBindingUtil {

    @BindingAdapter("image")
    public static void imageLoader(ImageView imageView, List<String> imageUrls) {
        Glide.with((Activity) imageView.getContext()).load(imageUrls.get(0)).into(imageView);
    }
}
