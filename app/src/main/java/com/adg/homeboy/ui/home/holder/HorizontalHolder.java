package com.adg.homeboy.ui.home.holder;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.AmazingModel;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.ui.list.MovieListActivity;
import com.adg.homeboy.ui.movie.WebViewPlayActivity;
import com.adg.homeboy.util.ImageLoader;
import com.adg.homeboy.util.ScreenUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2017/12/24.
 */

public class HorizontalHolder extends BaseViewHolder<AmazingModel> {

    GridLayout gridLayout;
    TextView title;
    TextView more;
    ImageView next;

    DisplayMetrics dm;
    int padding = 8;
    int margin;

    int offset; //两边间距

    public HorizontalHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_amazing_horizontal);
        dm = getContext().getResources().getDisplayMetrics();
        margin = ScreenUtil.dip2px(getContext(), padding);

        next = $(R.id.next);
        gridLayout = $(R.id.gridlayout);
        title = $(R.id.title);
        more = $(R.id.more);

        offset = ScreenUtil.dip2px(getContext(), 40);

    }

    @Override
    public void setData(final AmazingModel data) {
        gridLayout.removeAllViews();

        View.OnClickListener vo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieListActivity.class);
                intent.putExtra("typeid", data.type);
                getContext().startActivity(intent);
            }
        };

        title.setText(data.subtitle);
        more.setOnClickListener(vo);
        next.setOnClickListener(vo);

        for (int i = 0; i < data.childList.size(); i++) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.subitem_videolist, gridLayout, false);
            TextView picTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView picImage = (ImageView) convertView.findViewById(R.id.img);

            int width = (dm.widthPixels -offset- 5 * margin) / 4;
            int height = (int) (width / 0.667);
            setImageListener(picImage,data.childList.get(i));

            picTitle.setText(data.childList.get(i).name);
            ImageLoader.loadImage(getContext(), data.childList.get(i).pic, picImage);
            setImageListener(picImage, data.childList.get(i));

            GridLayout.LayoutParams gl = (GridLayout.LayoutParams) convertView.getLayoutParams();
            gl.width = (dm.widthPixels -offset- 5 * margin) / 4;
            gl.height = (int) (width / 0.667);
            gl.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);

            gridLayout.addView(convertView);
        }
    }

    private void setImageListener(View view, final MovieModel model){
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewPlayActivity.class);
                intent.putExtra("id",model.id);
                intent.putExtra("pic",model.pic);
                getContext().startActivity(intent);
            }
        });
    }
}
