package com.adg.homeboy.ui.user;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2018/3/10.
 */

public class SettingHolder extends BaseViewHolder<SettingModel> {

    TextView title;
    View divider1;
    View divider2;

    public SettingHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_setting);
        title = $(R.id.title);
        divider1 = $(R.id.divider1);
        divider2 = $(R.id.divider2);
    }

    @Override
    public void setData(SettingModel data) {
        title.setText(data.title);

        if (getAdapterPosition() != 0) {
            divider1.setVisibility(View.GONE);
        }

    }
}
