package com.adg.homeboy.util;

import com.adg.homeboy.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class LocalJsonReadUtil {

    public static String open(String fileName) {
        InputStream is = null;
        try {
            is = App.getInstance().getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb2 = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb2.append(line);
            }
            String json = sb2.toString();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> T getModelFromJson(String json,Class<T> t){
        Gson gson = new Gson();
        return gson.fromJson(json,t);
    }

    public static  <T> T getModelFromFile(String fileName,Class<T> t){
        String json = open(fileName);
        if(json != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                jsonObject.toString();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        return null;
//
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            Gson gson = builder.create();
            return gson.fromJson(json, t);
        }else{
            return null;
        }
    }
}
