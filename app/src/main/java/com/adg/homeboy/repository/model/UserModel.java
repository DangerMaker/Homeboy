package com.adg.homeboy.repository.model;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by liuxiaoyu on 2018/3/10.
 */

public class UserModel {
    public String id;
    public String nickname;
    public String avatar;
    public String token;

    public String toJson(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        Log.e("model json",json);
        return json;
    }
}
