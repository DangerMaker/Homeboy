package com.adg.homeboy.repository.model;

import java.util.List;

/**
 * Created by liuxiaoyu on 2017/12/21.
 *
 *  showStyle 1、bannner
 *            2、videolist (six chunk)
 *            3、videohorizontal
 */

public class AmazingModel{
    public static final String TYPE_BANNER = "banner";
    public static final String TYPE_LIST = "videolist";
    public static final String TYPE_HONRIZAONTAL = "videohorizontal";
    public static final String TYPE_THREE = "three";
    public static final String TYPE_CATEGORY = "category";

    public String showStyle;
    public String subtitle;
    public int type;
    public List<MovieModel> childList;
}
