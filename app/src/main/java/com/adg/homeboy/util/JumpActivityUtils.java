package com.adg.homeboy.util;

import android.content.Context;
import android.content.Intent;

import com.adg.homeboy.player.MoviePlayActivity;

/**
 * Created by liuxiaoyu on 2018/4/5.
 */

public class JumpActivityUtils {

    public static void toPlay(Intent intent, Context activity){
        intent.setClass(activity, MoviePlayActivity.class);
        activity.startActivity(intent);
    }
}
