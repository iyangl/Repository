package com.ly.example.myapplication2.utils;

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
    }

    public static class Storage {
        /**
         * data缓存目录
         */
        public static final String PACK_CACHE = app.getInstance().getCacheDir().getAbsolutePath() + "/";

        /**
         * 存放css目录
         */
        public static final String CSS_DIR = PACK_CACHE + "css/";

    }

}
