package com.adg.homeboy.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.service.UpdataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/4/12.
 */

public class CheckVersionUpdate {

    //http://10.0.0.2/update/fly_ver1.0.0.apk

    public static void checkVersion(final Context mContext, final boolean auto) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");

        /**
         *获取版本号与当前版本进行比较
//         */
//        Call<String> resp = RetrofitHelper.getCommonApi().update();
//        resp.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response != null && response.isSuccessful()) {
//                    final String verUrl = response.body();
                    final String verUrl = "http://10.0.0.2/update/fly_ver1.0.0.apk";
                    int startIndex = verUrl.indexOf("_ver");
                    int endIndex = verUrl.indexOf(".apk");

                    String verResult = verUrl.substring(startIndex + 4, endIndex);
                    System.out.println("net ver:" + verResult);
                    String currentVer = SystemUtils.getVersion(mContext);

                    if (SystemUtils.VersionComparison(verResult, currentVer) == 1) {
                        builder.setMessage("有新版本可以更新");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startUpdata(mContext,verUrl);
                                dialog.dismiss();
                            }
                        });
                        builder.show();

                    } else {
                        if (!auto)
                            Toast.makeText(mContext, "当前版本是最新版", Toast.LENGTH_SHORT).show();
                    }
//                }
//            }

//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
    }

    /**
     * 开始下载升级
     */
    private static void startUpdata(Context mContext,String apkUrl) {
        Intent intent = new Intent(mContext, UpdataService.class);
        intent.putExtra("url", apkUrl);
        mContext.startService(intent);
    }
}