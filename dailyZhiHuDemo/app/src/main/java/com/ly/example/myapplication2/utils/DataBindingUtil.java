package com.ly.example.myapplication2.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ly.example.myapplication2.R;
import com.ly.example.myapplication2.api.apibean.ThemeNewsBean;
import com.ly.example.myapplication2.widgets.ImageLoader;

import java.util.List;

public class DataBindingUtil {

    @BindingAdapter("image")
    public static void imageLoader(ImageView imageView, String imageUrls) {
        if (!TextUtils.isEmpty(imageUrls)) {
            ImageLoader.loadXMLImage(imageView.getContext(), imageUrls, imageView);
        }
    }

    @BindingAdapter("avatar")
    public static void avatarLoader(ImageView imageView, String imageUrls) {
        ImageLoader.loadCircleImage(imageView.getContext(), imageUrls, imageView);
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
                        (int) CommonUtils.getDimens(R.dimen.x30),
                        (int) CommonUtils.getDimens(R.dimen.x30));
                params.gravity = Gravity.CENTER_VERTICAL;
                params.rightMargin = CommonUtils.getDimension(R.dimen.x10);
                ImageView imageView = new ImageView(layout.getContext());
                imageView.setLayoutParams(params);
                layout.addView(imageView);
                ImageLoader.loadCircleImage(imageView.getContext(), editor.getAvatar(), imageView);
            }
        }
    }

    @BindingAdapter("android:layout_marginTop")
    public static void setTopMargin(View view, int topMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, topMargin,
                layoutParams.rightMargin, layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("selected")
    public static void selected(ImageView view, boolean selected) {
        view.setSelected(selected);
    }

}
