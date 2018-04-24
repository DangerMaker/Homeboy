package com.adg.homeboy;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class App extends Application {

    public static Context globalContext;
    public static String COOKIE;
//    public static String IP = "http://192.168.2.107:8888/";
    public static String IP = "http://10.0.2.2:8888/";


    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;

        Glide.get(this).setMemoryCategory(MemoryCategory.LOW);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    public static Context getInstance(){
        return globalContext;
    }
}
