package com.adg.homeboy.repository.net;


import com.adg.homeboy.BuildConfig;
import com.adg.homeboy.Config;
import com.adg.homeboy.util.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description: RetrofitHelper
 * Creator: yxc
 * date: 2016/9/21 10:03
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private static MovieApi videoApi;
    private static AuthApi authApi;
    private static CommonApi commonApi;

    public static MovieApi getMoiveApi() {
        initOkHttp();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MovieApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (videoApi == null) {
            videoApi = retrofit.create(MovieApi.class);
        }
        return videoApi;
    }

    public static AuthApi getAuthApi() {
        initOkHttp();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AuthApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (authApi == null) {
            authApi = retrofit.create(AuthApi.class);
        }
        return authApi;
    }

    public static CommonApi getCommonApi() {
        initOkHttp();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(CommonApi.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (commonApi == null) {
            commonApi = retrofit.create(CommonApi.class);
        }
        return commonApi;
    }



    private static void initOkHttp() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }
            File cacheFile = new File(Config.PATH_CACHE);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!SystemUtils.isNetworkConnected()) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
//                                .addHeader("Content-Type","application/json")
                                .build();
                    }

                    Response response = chain.proceed(request);
                    return response;
                }
            };

            //设置缓存
//            builder.addNetworkInterceptor(cacheInterceptor);
            builder.addInterceptor(cacheInterceptor);

            Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();
                    Request.Builder builder = request.newBuilder();
                    request = builder.addHeader("Accept", "application/json").build();
                    return chain.proceed(request);
                }
            };

            builder.addInterceptor(headerInterceptor);
            builder.cache(cache);
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            okHttpClient = builder.build();
        }
    }
}
