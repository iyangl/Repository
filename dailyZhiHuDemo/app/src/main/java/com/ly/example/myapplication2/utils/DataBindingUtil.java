package com.ly.example.myapplication2.utils;

import android.databinding.BindingAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.utils.widgets.ImageLoader;

import java.util.List;

public class DataBindingUtil {

    @BindingAdapter("image")
    public static void imageLoader(ImageView imageView, String imageUrls) {
        ImageLoader.loadImage(imageView.getContext(), imageUrls, imageView);
    }

    @BindingAdapter("selected")
    public static void itemSelected(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

    @BindingAdapter("editors")
    public static void loadEditors(LinearLayout layout, List<ThemeNewsBean.EditorsBean> editors) {
        if (layout.getChildCount() > 1) {
            layout.removeViews(1, layout.getChildCount() - 1);
        }
        if (editors != null && editors.size() > 0) {
            for (ThemeNewsBean.EditorsBean editor : editors) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        CommonUtils.dip2px(layout.getContext(), 30),
                        CommonUtils.dip2px(layout.getContext(), 30));
                params.gravity = Gravity.CENTER_VERTICAL;
                params.rightMargin = CommonUtils.dip2px(layout.getContext(), 10);
                ImageView imageView = new ImageView(layout.getContext());
                imageView.setLayoutParams(params);
                layout.addView(imageView);
                ImageLoader.loadCircleImage(imageView.getContext(), editor.getAvatar(), imageView);
            }
        }
    }
}
