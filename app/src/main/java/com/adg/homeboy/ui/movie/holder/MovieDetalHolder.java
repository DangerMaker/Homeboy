package com.adg.homeboy.ui.movie.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.MovieDetail;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class MovieDetalHolder extends BaseViewHolder<MovieDetail> {

    TextView name;
    TextView year;
    TextView remark;
    TextView score;
    TextView des;
    TextView star;

    public MovieDetalHolder(ViewGroup parent) {
        super(parent, R.layout.item_movie_des);
        name = $(R.id.name);
        year = $(R.id.year);
        remark = $(R.id.remark);
        score = $(R.id.score);
        des = $(R.id.des);
        star = $(R.id.star);
    }

    @Override
    public void setData(MovieDetail data) {
        name.setText(data.name);
        year.setText(data.year);
        remark.setText(data.remark);
        score.setText(data.score + "分");
        des.setText("    " + data.content);
        star.setText(data.star);
    }
}
