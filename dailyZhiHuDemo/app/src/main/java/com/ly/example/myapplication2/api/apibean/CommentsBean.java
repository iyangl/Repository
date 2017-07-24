package com.ly.example.myapplication2.api.apibean;

import java.io.Serializable;
import java.util.List;

public class CommentsBean {

    private List<CommentBean> comments;

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public static class CommentBean implements Serializable {

        /**
         * 评论作者
         */
        private String author;
        /**
         * 评论的内容
         */
        private String content;
        /**
         * 用户头像图片的地址
         */
        private String avatar;
        /**
         * 评论时间
         */
        private Long time;
        /**
         * 所回复的消息
         */
        private ReplyToBean reply_to;
        /**
         * 评论者的唯一标识符
         */
        private int id;
        /**
         * 评论所获『赞』的数量
         */
        private int likes;

        /**
         * 是否已点赞
         */
        private Boolean voted;
        /**
         * 是否是当前用户的评论
         */
        private Boolean own;

        private boolean isExpand;

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }

        public Boolean getOwn() {
            return own;
        }

        public void setOwn(Boolean own) {
            this.own = own;
        }

        public Boolean getVoted() {
            return voted;
        }

        public void setVoted(Boolean voted) {
            this.voted = voted;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public static class ReplyToBean implements Serializable{

            /**
             * 原消息的内容
             */
            private String content;
            /**
             * 消息状态，0为正常，非0为已被删除
             */
            private int status;
            /**
             * 被回复者的唯一标识符
             */
            private int id;
            /**
             * 被回复者
             */
            private String author;

            /**
             * 错误消息，仅当status非0时出现
             */
            private String err_msg;

            public String getErr_msg() {
                return err_msg;
            }

            public void setErr_msg(String err_msg) {
                this.err_msg = err_msg;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            @Override
            public String toString() {
                return "ReplyToBean{" +
                        "content='" + content + '\'' +
                        ", status=" + status +
                        ", id=" + id +
                        ", author='" + author + '\'' +
                        ", err_msg='" + err_msg + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "CommentBean{" +
                    "author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", time=" + time +
                    ", reply_to=" + reply_to +
                    ", id=" + id +
                    ", likes=" + likes +
                    ", voted=" + voted +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "CommentsBean{" +
                "comments=" + comments +
                '}';
    }
}
