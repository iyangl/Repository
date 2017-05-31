package com.ly.example.myapplication2.widgets;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.widgets.glide.GlideCircleTransform;
import com.ly.example.myapplication2.widgets.glide.GlideRoundTransform;

import java.io.File;

public class ImageLoader {

    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).centerCrop().error(erroImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).crossFade().placeholder(R.drawable.image_small_default).error(R.drawable
                .image_small_default).centerCrop()
                .into(iv);
    }

    public static void loadXMLImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).crossFade().placeholder(R.drawable.image_small_default).error(R.drawable
                .image_small_default)
                .into(iv);
    }

    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable
                .image_small_default).centerCrop().error(R.drawable.image_small_default).into(iv);
    }


    public static void loadCircleImage(Context context, String url, ImageView iv) {
        loadCircleImage(context, url, iv, R.drawable.image_small_default);
    }

    public static void loadCircleImage(Context context, String url, ImageView iv, int defaultImage) {
        Glide.with(context).load(url).placeholder(defaultImage).error(defaultImage)
                .centerCrop().transform(new GlideCircleTransform(context)).into(iv);
    }

    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.drawable.image_small_default).error(R.drawable
                .image_small_default).centerCrop().transform(new
                GlideRoundTransform(context, 10)).into(iv);
    }


    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file).centerCrop()
                .into(imageView);


    }

    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId).centerCrop()
                .into(imageView);
    }
}