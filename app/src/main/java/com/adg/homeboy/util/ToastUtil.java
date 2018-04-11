package com.adg.homeboy.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liuxiaoyu on 2018/3/18.
 */

public class ToastUtil {
    public static void message(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
