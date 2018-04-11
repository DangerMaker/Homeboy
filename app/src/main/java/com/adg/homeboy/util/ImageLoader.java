package com.adg.homeboy.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

/**
 * Description: ImageLoader
 * Creator: yxc
 * date: 2016/9/21 9:53 
 */
public class ImageLoader {

    public static void load(Context context, String url,final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Linux; U; Android 6.0.1; zh-CN; SM-G9250 Build/MMB29K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/11.1.0.870 U3/0.8.0 Mobile Safari/534.30")
                .addHeader("Accept-Language","zh-CN")
                .addHeader("Accept-Encoding","gzip")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8,UC/145,plugin/1")
                .build());


        Glide.with(context).load(glideUrl).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(imageView);
    }

    public static void loadImage(Context context, String url,final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(imageView);
    }

    public static void loadCircleImage(Context context, String url,final ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).
                into(imageView);
    }


    @TargetApi(17)
    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
        }
    }

    public static void loadAll(Context context, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(context).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    @TargetApi(17)
    public static void loadAll(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).crossFade().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        }
    }
}
