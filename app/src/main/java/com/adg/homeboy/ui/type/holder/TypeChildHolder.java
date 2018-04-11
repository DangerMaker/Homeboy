package com.adg.homeboy.ui.type.holder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.repository.model.MovieType;
import com.adg.homeboy.util.ImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class TypeChildHolder extends BaseViewHolder<MovieType> {

    TextView title;

    public TypeChildHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_type_child);
        title = $(R.id.title);
    }

    @Override
    public void setData(MovieType data) {
        title.setText(data.name);
    }
}
