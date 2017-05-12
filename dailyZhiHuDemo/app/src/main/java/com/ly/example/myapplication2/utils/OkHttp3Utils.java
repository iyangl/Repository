package com.ly.example.myapplication2.utils;

import android.content.Context;
import android.os.Handler;

import com.ly.example.myapplication2.app;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttp3Utils {

    private volatile static OkHttp3Utils mInstance;

    private OkHttpClient mOkHttpClient;

    private Handler mHandler;

    private Context mContext;

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private OkHttp3Utils() {
        super();
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient = clientBuilder.build();
        this.mContext = app.getInstance();
        mHandler = new Handler(mContext.getMainLooper());
    }

    public static OkHttp3Utils getInstance() {
        OkHttp3Utils temp = mInstance;
        if (temp == null) {
            synchronized (OkHttp3Utils.class) {
                temp = mInstance;
                if (temp == null) {
                    temp = new OkHttp3Utils();
                    mInstance = temp;
                }
            }
        }
        return temp;
    }

    /**
     * 设置请求头
     *
     * @param headersParams
     * @return
     */
    private Headers SetHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }

    /**
     * post请求参数
     *
     * @param BodyParams
     * @return
     */
    private RequestBody SetPostRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;
    }

    /**
     * Post上传图片的参数
     *
     * @param BodyParams
     * @return
     */
    private RequestBody SetFileRequestBody(Map<String, String> BodyParams, Map<String, String> filePathParams) {
        // 带文件的Post参数
        RequestBody body = null;
        MultipartBody.Builder MultipartBodyBuilder = new MultipartBody.Builder();
        MultipartBodyBuilder.setType(MultipartBody.FORM);
        RequestBody fileBody = null;
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                MultipartBodyBuilder.addFormDataPart(key, BodyParams.get(key));
            }
        }
        if (filePathParams != null) {
            Iterator<String> iterator = filePathParams.keySet().iterator();
            String key = "";
            int i = 0;
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                i++;
                MultipartBodyBuilder.addFormDataPart(key, filePathParams.get(key));
                fileBody = RequestBody.create(MEDIA_TYPE_PNG, new File(filePathParams.get(key)));
                MultipartBodyBuilder.addFormDataPart(key, i + ".png", fileBody);
            }
        }
        body = MultipartBodyBuilder.build();
        return body;
    }

    /**
     * get方法连接拼加参数
     *
     * @param mapParams
     * @return
     */
    private String setGetUrlParams(Map<String, String> mapParams) {
        String strParams = "";
        if (mapParams != null) {
            Iterator<String> iterator = mapParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                strParams += "&" + key + "=" + mapParams.get(key);
            }
        }
        return strParams;
    }

    /**
     * 实现post请求
     *
     * @param reqUrl
     * @param headersParams
     * @param params
     * @param callback
     */
    public void doPost(String reqUrl, Map<String, String> headersParams, Map<String, String> params, final
    NetCallback callback) {
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl);// 添加URL地址
        RequestBuilder.method("POST", SetPostRequestBody(params));
        RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
        Request request = RequestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onSuccess(0, response.body().toString());
                        call.cancel();
                    }
                });
            }

            @Override
            public void onFailure(final Call call, final IOException exception) {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onFailure(-1, exception.getMessage());
                        call.cancel();
                    }
                });
            }
        });
    }

    /**
     * 实现get请求
     *
     * @param reqUrl
     * @param headersParams
     * @param params
     * @param callback
     */
    public void doGet(String reqUrl, Map<String, String> headersParams, Map<String, String> params, final NetCallback
            callback) {
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl + setGetUrlParams(params));// 添加URL地址 自行加 ?
        RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
        Request request = RequestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onSuccess(0, response.body().toString());
                        call.cancel();
                    }
                });
            }

            @Override
            public void onFailure(final Call call, final IOException exception) {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onFailure(-1, exception.getMessage());
                        call.cancel();
                    }
                });
            }
        });
    }

    /**
     * 实现get请求,回调在分线程
     *
     * @param reqUrl
     * @param headersParams
     * @param params
     * @param callback
     */
    public void doGetAsync(String reqUrl, Map<String, String> headersParams, Map<String, String> params,
                           final NetCallback callback) {
        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(reqUrl + setGetUrlParams(params));// 添加URL地址 自行加 ?
        RequestBuilder.headers(SetHeaders(headersParams));// 添加请求头
        Request request = RequestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                callback.onSuccess(0, response.body().string());
                call.cancel();
            }

            @Override
            public void onFailure(final Call call, final IOException exception) {
                callback.onFailure(-1, exception.getMessage());
                call.cancel();
            }
        });
    }


    public abstract static class NetCallback {
        public abstract void onFailure(int code, String msg);

        public abstract void onSuccess(int code, String content);
    }
}