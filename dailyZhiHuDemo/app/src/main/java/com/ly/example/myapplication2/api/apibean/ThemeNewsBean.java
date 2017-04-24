package com.ly.example.myapplication2.api.apibean;

import java.util.List;

public class ThemeNewsBean {

    /**
     * 该主题日报的介绍
     */
    private String description;
    /**
     * 该主题日报的背景图片（大图）
     */
    private String background;
    /**
     * 颜色，作用未知
     */
    private int color;
    /**
     * 该主题日报的名称
     */
    private String name;
    /**
     * 背景图片的小图版本
     */
    private String image;
    /**
     * 图像的版权信息
     */
    private String image_source;
    /**
     * 该主题日报中的文章列表
     */
    private List<StoriesBean> stories;
    /**
     * 该主题日报的编辑（『用户推荐日报』中此项的指是一个空数组，
     * 在 App 中的主编栏显示为『好多人』，点击后访问该主题日报的介绍页面，请留意）
     */
    private List<EditorsBean> editors;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<EditorsBean> getEditors() {
        return editors;
    }

    public void setEditors(List<EditorsBean> editors) {
        this.editors = editors;
    }

    public static class StoriesBean {

        /**
         * 类型，作用未知
         */
        private int type;
        /**
         * 消息的id
         */
        private int id;
        /**
         * 消息的标题
         */
        private String title;
        /**
         * 图像地址（其类型为数组。请留意在代码中处理无该属性与数组长度为 0 的情况）
         */
        private List<String> images;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class EditorsBean {

        /**
         * 主编的知乎用户主页
         */
        private String url;
        /**
         * 主编的个人简介
         */
        private String bio;
        /**
         * 数据库中的唯一表示符
         */
        private int id;
        /**
         * 主编的头像
         */
        private String avatar;
        /**
         * 主编的姓名
         */
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
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
