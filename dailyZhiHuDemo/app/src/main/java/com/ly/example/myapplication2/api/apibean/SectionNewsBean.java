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

}
