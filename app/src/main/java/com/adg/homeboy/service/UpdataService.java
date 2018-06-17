package com.adg.homeboy.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.adg.homeboy.BuildConfig;

import java.io.File;

/**
 * User: lyjq(1752095474)
 * Date: 2016-06-30
 */
public class UpdataService extends Service {
    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;

    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    /**
     * 初始化下载器
     **/
    private void initDownManager(String url) {

        String name = "";
        if (url != null) {
            int index = url.lastIndexOf("/");
            name = url.substring(index + 1, url.length());
        }
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(
                Uri.parse(url));

        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);

        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        // 显示下载界面
        down.setVisibleInDownloadsUi(true);

        // 设置下载后文件存放的位置
        down.setDestinationInExternalFilesDir(this,
                Environment.DIRECTORY_DOWNLOADS, name);

        // 将下载请求放入队列
        manager.enqueue(down);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String url = intent.getStringExtra("url");
        // 调用下载
        initDownManager(url);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);

        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @SuppressLint("NewApi")
        public void onReceive(Context context, Intent intent) {
            long downLoadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Intent install = new Intent(Intent.ACTION_VIEW);
            File apkFile = queryDownloadedApk(context, downLoadId);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (Build.VERSION.SDK_INT > 24) {
                Uri apkUri =
                        FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }else{
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }

            context.startActivity(install);
        }

        /**
         * 安装apk文件
         */
        private void installAPK(Uri apk) {

            // 通过Intent安装APK文件
            Intent intents = new Intent();

            intents.setAction("android.intent.action.VIEW");
            intents.addCategory("android.intent.category.DEFAULT");
            intents.setType("application/vnd.android.package-archive");
            intents.setData(apk);
            intents.setDataAndType(apk, "application/vnd.android.package-archive");
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            android.os.Process.killProcess(android.os.Process.myPid());
            // 如果不加上这句的话在apk安装完成之后点击单开会崩溃

            startActivity(intents);

        }
    }

    public static File queryDownloadedApk(Context context, long downloadId) {
        File targetApkFile = null;
        DownloadManager downloader = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadId != -1) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
            Cursor cur = downloader.query(query);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    if (!TextUtils.isEmpty(uriString)) {
                        targetApkFile = new File(Uri.parse(uriString).getPath());
                    }
                }
                cur.close();
            }
        }
        return targetApkFile;
    }
}