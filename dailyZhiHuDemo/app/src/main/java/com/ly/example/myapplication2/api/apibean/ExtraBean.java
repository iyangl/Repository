package com.ly.example.myapplication2.api.apibean;

public class ExtraBean {

    /**
     * 长评论总数
     */
    private int long_comments;
    /**
     * 点赞总数
     */
    private int popularity;
    /**
     * 短评论总数
     */
    private int short_comments;
    /**
     * 评论总数
     */
    private int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
