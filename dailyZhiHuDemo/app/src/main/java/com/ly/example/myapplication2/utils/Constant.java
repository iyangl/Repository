package com.ly.example.myapplication2.utils;

import android.os.Environment;

import com.ly.example.myapplication2.app;

public class Constant {

    public static class Share_prf {

        /**
         * 缓存的图片信息
         */
        public static final String LAUNCH_IMAGES = "launch_images";
    }

    public static class Intent_Extra {
        /**
         * 是否存在缓存图片url
         */
        public static final String NEED_PREFETCH_IMAGES = "need_prefetch_images";

        /**
         * 新闻id
         */
        public static final String NEWS_ID = "news_id";

        /**
         * webView中Image的url
         */
        public static final String WEB_IMAGE_URL = "web_image_url";
        /**
         * news的评论数等信息
         */
        public static final String NEWS_STORY_EXTRA = "news_story_extra";

        /**
         * 回复对象的信息
         */
        public static final String REPLY_COMMENT = "reply_comment";
    }

    public static class Storage {
        /**
         * data缓存目录
         */
        public static final String PACK_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + "DailyZhiHu/";

        /**
         * 缓存目录
         */
        public static final String CACHE_DIR = app.getInstance().getCacheDir().getAbsolutePath();

        /**
         * 存放css目录
         */
        public static final String WEB_CACHE_DIR = CACHE_DIR + "web/";
    }

    public static class FileType {
        public static final int CSS = 1;
        public static final int JS = 2;
    }

}
