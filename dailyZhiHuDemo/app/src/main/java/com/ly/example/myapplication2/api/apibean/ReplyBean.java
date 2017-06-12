package com.ly.example.myapplication2.api.apibean;

public class ReplyBean {

    private String content;
    private String share_to;
    private int reply_to;

    private ReplyCommentBean comment;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare_to() {
        return share_to;
    }

    public void setShare_to(String share_to) {
        this.share_to = share_to;
    }

    public int getReply_to() {
        return reply_to;
    }

    public void setReply_to(int reply_to) {
        this.reply_to = reply_to;
    }

    public static class ReplyCommentBean {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
