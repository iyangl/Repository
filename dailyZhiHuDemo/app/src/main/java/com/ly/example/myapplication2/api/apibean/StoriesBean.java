package com.ly.example.myapplication2.api.apibean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ly.example.myapplication2.BR;

import java.util.List;

public class StoriesBean extends BaseObservable{

    /**
     * 新闻标题
     */
    private String title;
    /**
     * 供 Google Analytics 使用
     */
    private String ga_prefix;
    /**
     * 类型，作用未知
     */
    private int type;
    /**
     * url 与 share_url 中最后的数字（应为内容的 id）
     */
    private int id;
    /**
     * 图像地址
     */
    private List<String> images;

    /**
     * 是否多图
     */
    private boolean multipic;
    /**
     * 日期 格式：20130101
     */
    private String date;
    /**
     * 显示日期 格式：n月n日
     */
    private String display_date;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Bindable
    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
        notifyPropertyChanged(BR.multipic);
    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);
    }

    public String getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(String display_date) {
        this.display_date = display_date;
    }

    @Override
    public String toString() {
        return "StoriesBean{" +
                "title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", images=" + images +
                ", multipic=" + multipic +
                ", date='" + date + '\'' +
                ", display_date='" + display_date + '\'' +
                '}';
    }
}
