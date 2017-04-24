package com.ly.example.myapplication2.api.apibean;

import java.util.List;

public class NewsBean {

    /**
     * 日期
     */
    private String date;
    /**
     * 当日新闻
     */
    private List<StoriesBean> stories;
    /**
     * 界面顶部 ViewPager 滚动显示的显示内容
     */
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {

        /**
         * 新闻标题
         */
        private String title;
        /**
         * 供 Google Analytics 使用
         */
        private String ga_prefix;
        private int type;
        /**
         * url 与 share_url 中最后的数字（应为内容的 id）
         */
        private int id;
        /**
         * 图像地址
         */
        private List<String> images;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {

        /**
         * 新闻标题
         */
        private String title;
        /**
         * 供 Google Analytics 使用
         */
        private String ga_prefix;
        private int type;
        /**
         * url 与 share_url 中最后的数字（应为内容的 id）
         */
        private int id;
        /**
         * 图像地址
         */
        private String image;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
    }
}
