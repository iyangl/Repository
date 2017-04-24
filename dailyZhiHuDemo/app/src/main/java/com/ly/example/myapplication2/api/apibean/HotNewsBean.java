package com.ly.example.myapplication2.api.apibean;

import java.util.List;

public class HotNewsBean {

    private List<RecentBean> recent;

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }

    public static class RecentBean {

        /**
         * 消息id
         */
        private int news_id;
        /**
         * 消息url，内容为{@link NewsDetailBean}
         */
        private String url;
        /**
         * 缩略图地址
         */
        private String thumbnail;
        /**
         * 消息标题
         */
        private String title;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
