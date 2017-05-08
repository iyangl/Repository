package com.ly.example.myapplication2.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ServiceGenerator {

    private static final String BASE_URL = "http://news-at.zhihu.com/api/";
    private static final String Authorization = "Bearer moEVfTWCT3-KWj-g9ZJIxQ";

    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();


    private static Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(setLoggingClient())
            .baseUrl(BASE_URL)
            .build();

    private static OkHttpClient setLoggingClient() {
        addHeaderInterceptor();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.d("Retrofit ---> %s", message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    private static void addHeaderInterceptor() {
        builder.interceptors().clear();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", Authorization)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
    }

    public static <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
