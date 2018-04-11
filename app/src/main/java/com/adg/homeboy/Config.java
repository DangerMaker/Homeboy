package com.adg.homeboy;

import java.io.File;

/**
 * Created by liuxiaoyu on 2018/1/8.
 */

public class Config {
    public static final String PATH_DATA = App.globalContext.getExternalCacheDir() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";
}
