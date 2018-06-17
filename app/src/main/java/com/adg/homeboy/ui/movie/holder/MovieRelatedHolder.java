package com.adg.homeboy.ui.movie.holder;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.util.ImageLoader;
import com.adg.homeboy.util.JumpActivityUtils;
import com.adg.homeboy.util.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class MovieRelatedHolder extends BaseViewHolder<MovieModel> {

    TextView title;
    ImageView bg;
    RelativeLayout textLayout;

    DisplayMetrics dm;
    int padding = 4;
    int margin;

    public MovieRelatedHolder(ViewGroup itemView) {
        super(itemView, R.layout.subitem_videolist);
        bg = $(R.id.img);
        title = $(R.id.title);
        textLayout = $(R.id.text_layout);

        dm = getContext().getResources().getDisplayMetrics();
        margin = ScreenUtil.dip2px(getContext(),padding);

    }

    @Override
    public void setData(MovieModel data) {
        title.setText(data.name);

        ViewGroup.LayoutParams params = bg.getLayoutParams();
//        params.width = (dm.widthPixels - 2 * margin) / 3;
        params.width = ScreenUtil.getScreenWidth(getContext()) /3;
        params.height = (int)(params.width /0.667);
        ImageLoader.loadImage(getContext(), data.pic, bg);
        setImageListener(itemView,data);

        ViewGroup.LayoutParams textParams = textLayout.getLayoutParams();
        textParams.width = (dm.widthPixels - 2 * margin) / 3;
        textParams.height = ScreenUtil.dip2px(getContext(),40);
    }

    private void setImageListener(View view, final MovieModel model){
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", model.id);
                intent.putExtra("pic", model.pic);
                intent.putExtra("playurl",model.playurl);
                JumpActivityUtils.toPlay(intent,getContext());
            }
        });
    }

}
