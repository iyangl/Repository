package com.ly.example.myapplication2.api.apibean;

import java.io.Serializable;

public class ExtraBean implements Serializable {

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

    /**
     * 0：未点赞  1：已点赞
     */
    private int vote_status;

    /**
     * 是否收藏
     */
    private Boolean favorite;

    /**
     * 评论点赞返回值
     */
    private Integer count;

    public int getVote_status() {
        return vote_status;
    }

    public void setVote_status(int vote_status) {
        this.vote_status = vote_status;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

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

    @Override
    public String toString() {
        return "ExtraBean{" +
                "long_comments=" + long_comments +
                ", popularity=" + popularity +
                ", short_comments=" + short_comments +
                ", comments=" + comments +
                ", vote_status=" + vote_status +
                ", favorite=" + favorite +
                '}';
    }
}
