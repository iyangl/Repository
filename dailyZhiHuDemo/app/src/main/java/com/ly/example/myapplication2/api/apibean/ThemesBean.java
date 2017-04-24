package com.ly.example.myapplication2.api.apibean;

import java.util.List;

public class ThemesBean {

    /**
     * 返回数目之限制（仅为猜测）
     */
    private int limit;
    /**
     * 已订阅条目
     */
    private List<?> subscribed;
    /**
     * 其他条目
     */
    private List<OthersBean> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<?> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<?> subscribed) {
        this.subscribed = subscribed;
    }

    public List<OthersBean> getOthers() {
        return others;
    }

    public void setOthers(List<OthersBean> others) {
        this.others = others;
    }

    public static class OthersBean {

        /**
         * 颜色，作用未知
         */
        private int color;
        /**
         * 供显示的图片地址,点进主题日报后头部显示的图片
         */
        private String thumbnail;
        /**
         * 主题日报的介绍
         */
        private String description;
        /**
         * 该主题日报的编号
         */
        private int id;
        /**
         * 供显示的主题日报名称
         */
        private String name;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
