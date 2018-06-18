package com.adg.homeboy.ui.home.holder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.AmazingModel;
import com.adg.homeboy.util.BaseViewHolder;
import com.adg.homeboy.view.BannerView;

/**
 * Created by liuxiaoyu on 2017/12/21.
 */

public class BannerHolder extends BaseViewHolder<AmazingModel> {
    BannerView bannerView;

    public BannerHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_amazing_banner);
        bannerView = $(R.id.header);
    }

    @Override
    public void setData(AmazingModel data) {
        bannerView.setData(data.childList);
    }

}
