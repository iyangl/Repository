package com.ly.example.myapplication2.api.apibean;

import java.util.List;

public class RecommendersBean {

    private int item_count;
    private List<?> items;
    private List<EditorsBean> editors;

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public List<EditorsBean> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorsBean> editors) {
        this.editors = editors;
    }

    public static class EditorsBean {

        /**
         * 推荐人个人简介
         */
        private String bio;
        /**
         * 推荐人标题
         */
        private String title;
        /**
         * 推荐人id
         */
        private int id;
        /**
         * 推荐人头像
         */
        private String avatar;
        /**
         * 推荐人名
         */
        private String name;

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
