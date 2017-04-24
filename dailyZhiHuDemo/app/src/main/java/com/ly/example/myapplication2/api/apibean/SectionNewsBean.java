package com.ly.example.myapplication2.api.apibean;

import java.util.List;

public class SectionNewsBean {

    /**
     * 内容更新时间戳
     */
    private int timestamp;
    /**
     * 栏目名
     */
    private String name;
    /**
     * 栏目内容列表
     */
    private List<StoriesBean> stories;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {

        /**
         * 日期 格式：20130101
         */
        private String date;
        /**
         * 显示日期 格式：n月n日
         */
        private String display_date;
        /**
         * 消息id
         */
        private int id;
        /**
         * 标题
         */
        private String title;
        /**
         * 是否有多张图片
         */
        private boolean multipic;
        /**
         * 图片url
         */
        private List<String> images;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDisplay_date() {
            return display_date;
        }

        public void setDisplay_date(String display_date) {
            this.display_date = display_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
