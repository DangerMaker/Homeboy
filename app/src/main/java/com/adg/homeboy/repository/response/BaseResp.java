package com.adg.homeboy.repository.response;

import com.google.gson.Gson;

/**
 * Created by liuxiaoyu on 2017/12/21.
 *
 * code
 */

public class BaseResp <T>{
    public String msg;
    public String code;
    public T data;
}
