package com.adg.homeboy.ui.type.holder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.MovieType;
import com.adg.homeboy.ui.list.MovieListActivity;
import com.adg.homeboy.util.BaseViewHolder;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class TypeChildHolder extends BaseViewHolder<MovieType> {

    TextView title;
    LinearLayout rootview;

    public TypeChildHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_type_child);
        title = $(R.id.title);
        rootview = $(R.id.root_view);
    }

    @Override
    public void setData(final MovieType data) {
        title.setText(data.name);

//        rootview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MovieListActivity.class);
//                intent.putExtra("typeid",data.id);
//                getContext().startActivity(intent);
//                Log.e("type",data.id + "");
//            }
//        });
    }
}
