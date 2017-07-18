package com.ly.example.myapplication2.mvp.presenter;

import android.app.Activity;

import com.ly.example.myapplication2.api.apibean.ExtraBean;
import com.ly.example.myapplication2.api.apibean.NewsDetailBean;
import com.ly.example.myapplication2.mvp.RequestImp;
import com.ly.example.myapplication2.mvp.model.NewsDetailModel;
import com.ly.example.myapplication2.mvp.model.imodel.INewsDetailModel;
import com.ly.example.myapplication2.mvp.view.iview.INewsDetailView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class NewsDetailPresenter {

    private INewsDetailView iNewsDetailView;
    private INewsDetailModel iNewsDetailModel;

    public NewsDetailPresenter(INewsDetailView iNewsDetailView) {
        this.iNewsDetailView = iNewsDetailView;
        iNewsDetailModel = new NewsDetailModel();
    }

    public void loadNewsDetail(int newsId) {
        iNewsDetailModel.loadNewsDetail(newsId, new RequestImp<NewsDetailBean>() {
            @Override
            public void onSuccess(NewsDetailBean data) {
                iNewsDetailView.loadNewsDetailSuccess(data);
            }

            @Override
            public void onError(Throwable e) {
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }

    public void loadStoryExtra(int newsId) {
        iNewsDetailModel.loadStoryExtra(newsId, new RequestImp<ExtraBean>() {
            @Override
            public void onSuccess(ExtraBean data) {
                if (data != null) {
                    iNewsDetailView.loadStoryExtra(data);
                }
            }

            @Override
            public void onError(Throwable e) {
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }

    public void voteStory(int newsId, int data) {
        iNewsDetailModel.voteStory(newsId, data, new RequestImp<ExtraBean>() {
            @Override
            public void onSuccess(ExtraBean data) {

            }

            @Override
            public void onError(Throwable e) {
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }

    public void collectStory(int newsId, boolean collect) {
        iNewsDetailModel.collectStory(newsId, collect, new RequestImp<ExtraBean>() {
            @Override
            public void onSuccess(ExtraBean data) {

            }

            @Override
            public void onError(Throwable e) {
                iNewsDetailView.onErrorLoad(e);
            }
        });
    }

    public void share(Activity context, String share_url, String title,
                      String desc, UMShareListener umShareListener) {
        UMWeb web = new UMWeb(share_url);
        web.setTitle(title);//标题
        web.setDescription(title);//描述
        web.setThumb(new UMImage(context, desc));
        new ShareAction(context).withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN,SHARE_MEDIA.TENCENT,
                        SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }
}
