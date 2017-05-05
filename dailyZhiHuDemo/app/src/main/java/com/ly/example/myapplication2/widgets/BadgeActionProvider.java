package com.ly.example.myapplication2.widgets;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ActionProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ly.example.myapplication2.R;

public class BadgeActionProvider extends ActionProvider {
    private Context context;

    private ImageView iv_badge;
    private TextView tv_badge;

    // 用来记录是哪个View的点击，这样外部可以用一个Listener接受多个menu的点击。
    private int clickWhat;
    private OnClickListener onClickListener;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public BadgeActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        View view = View.inflate(context, R.layout.menu_badge_provider, null);
        int size = getContext().getResources().getDimensionPixelSize(
                android.support.design.R.dimen.abc_action_bar_default_height_material);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, size);
        view.setLayoutParams(layoutParams);
        iv_badge = (ImageView) view.findViewById(R.id.iv_badge);
        tv_badge = (TextView) view.findViewById(R.id.tv_badge);
        view.setOnClickListener(onViewClickListener);
        return view;
    }

    // 点击处理。
    private View.OnClickListener onViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onClickListener != null)
                onClickListener.onClick(clickWhat);
        }
    };

    // 外部设置监听。
    public void setOnClickListener(int what, OnClickListener onClickListener) {
        this.clickWhat = what;
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int what);
    }

    // 设置图标。
    public void setIcon(@DrawableRes int icon) {
        iv_badge.setImageResource(icon);
    }

    // 设置显示的数字。
    public void setBadge(int i) {
        tv_badge.setText(String.valueOf(i));
    }

    public int getBadge() {
        int returnBadge = 0;
        String badge = tv_badge.getText().toString();
        if (!TextUtils.isEmpty(badge) && !badge.equals("...")) {
            returnBadge = Integer.parseInt(badge);
        }
        return returnBadge;
    }
}
