package com.adg.homeboy.ui.home.holder;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
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
 * Created by liuxiaoyu on 2017/12/21.
 */

public class ThreeListHolder extends BaseViewHolder<AmazingModel> {

    GridLayout gridLayout;
    TextView title;
    TextView more;
    ImageView next;

    DisplayMetrics dm;
    int padding = 4;
    int margin;
    int offset; //两边间距

    public ThreeListHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_amazing_videolist);
        dm = getContext().getResources().getDisplayMetrics();
        gridLayout = $(R.id.gridlayout);
        title = $(R.id.title);
        more = $(R.id.more);
        next = $(R.id.next);
        margin = ScreenUtil.dip2px(getContext(), padding);
        offset = ScreenUtil.dip2px(getContext(), 40);
    }

    @Override
    public void setData(final AmazingModel data) {
        gridLayout.removeAllViews();
        title.setText(data.subtitle);
        View.OnClickListener vo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieListActivity.class);
                intent.putExtra("typeid", data.type);
                getContext().startActivity(intent);
            }
        };

        more.setOnClickListener(vo);
        next.setOnClickListener(vo);

        for (int i = 0; i < data.childList.size(); i++) {
            View convertView = LayoutInflater.from(getContext()).inflate(R.layout.subitem_videolist, gridLayout, false);
            TextView picTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView picImage = (ImageView) convertView.findViewById(R.id.img);

            if (i == 0) {
                int width = (dm.widthPixels - offset - 3 * margin) / 3;
                int height = (int) (width / 0.667);

                picTitle.setText(data.childList.get(i).name);
                ImageLoader.loadImage(getContext(), data.childList.get(i).pic, picImage);
                setImageListener(picImage, data.childList.get(i));

                GridLayout.LayoutParams gl = (GridLayout.LayoutParams) convertView.getLayoutParams();
                gl.width =  2 * width + margin;
                gl.height =  2 * height + 2 * margin;
                gl.rowSpec = GridLayout.spec(0, 2);
                gl.columnSpec = GridLayout.spec(0, 2);
                gl.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);

            } else {
                int width = (dm.widthPixels - offset - 2 * margin) / 3;

                picTitle.setText(data.childList.get(i).name);
                ImageLoader.loadImage(getContext(), data.childList.get(i).pic, picImage);
                setImageListener(picImage, data.childList.get(i));

                GridLayout.LayoutParams gl = (GridLayout.LayoutParams) convertView.getLayoutParams();
                gl.width = (dm.widthPixels - offset - 2 * margin) / 3;
                gl.height = (int) (width / 0.667);
                gl.setMargins(margin / 2, margin / 2, margin / 2, margin / 2);
            }
            gridLayout.addView(convertView);
        }
    }

    private void setImageListener(View view, final MovieModel model) {
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewPlayActivity.class);
                intent.putExtra("id", model.id);
                intent.putExtra("pic", model.pic);
                getContext().startActivity(intent);
            }
        });
    }
}
