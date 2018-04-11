package com.adg.homeboy.ui.movie.holder;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.ui.movie.WebViewPlayActivity;
import com.adg.homeboy.util.ImageLoader;
import com.adg.homeboy.util.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class MovieRelatedHolder extends BaseViewHolder<MovieModel> {

    TextView title;
    ImageView bg;

    DisplayMetrics dm;
    int padding = 4;
    int margin;

    public MovieRelatedHolder(ViewGroup itemView) {
        super(itemView, R.layout.subitem_videolist);
        bg = $(R.id.img);
        title = $(R.id.title);

        dm = getContext().getResources().getDisplayMetrics();
        margin = ScreenUtil.dip2px(getContext(),padding);

    }

    @Override
    public void setData(MovieModel data) {
        title.setText(data.name);

        ViewGroup.LayoutParams params = bg.getLayoutParams();
        params.width = (dm.widthPixels - 4 * margin) / 3;
        params.height = (int)(params.width /0.667);
        ImageLoader.loadImage(getContext(), data.pic, bg);
        setImageListener(itemView,data);
    }

    private void setImageListener(View view, final MovieModel model){
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewPlayActivity.class);
                intent.putExtra("id",model.id);
                getContext().startActivity(intent);
            }
        });
    }

}
