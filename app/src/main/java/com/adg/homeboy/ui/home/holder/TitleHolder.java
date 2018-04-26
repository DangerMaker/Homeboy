package com.adg.homeboy.ui.home.holder;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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
import com.adg.homeboy.util.eventbus.TabEvent;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by liuxiaoyu on 2017/12/24.
 */

public class TitleHolder extends BaseViewHolder<AmazingModel> {

    TextView title1;
    ImageView imageView1;
    TextView title2;
    ImageView imageView2;
    TextView title3;
    ImageView imageView3;

    CardView cardView1;
    CardView cardView2;
    CardView cardView3;

    public TitleHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_type_home_child);
        title1 = $(R.id.title1);
        title2 = $(R.id.title2);
        title3 = $(R.id.title3);

        imageView1 = $(R.id.img1);
        imageView2 = $(R.id.img2);
        imageView3 = $(R.id.img3);

        cardView1 = $(R.id.card1);
        cardView2 = $(R.id.card2);
        cardView3 = $(R.id.card3);
    }

    @Override
    public void setData(final AmazingModel data) {
        List<MovieModel> list = data.childList;
        title1.setText(list.get(0).name);
        title2.setText(list.get(1).name);
        title3.setText(list.get(2).name);

        imageView1.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.logo_whole));
        ImageLoader.loadImage(getContext(), data.childList.get(1).pic, imageView2);
        ImageLoader.loadImage(getContext(), data.childList.get(2).pic, imageView3);

        setImageListener(cardView1, list.get(0));
        setImageListener(cardView2, list.get(1));
        setImageListener(cardView3, list.get(2));
    }

    private void setImageListener(View view, final MovieModel model) {
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (model.id == 0) {
                    EventBus.getDefault().post(new TabEvent());
                } else {
                    Intent intent = new Intent(getContext(), MovieListActivity.class);
                    intent.putExtra("typeid", model.id);
                    getContext().startActivity(intent);
                }
            }
        });
    }
}
