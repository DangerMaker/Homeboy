package com.adg.homeboy.ui.type.holder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.repository.model.MovieDetail;
import com.adg.homeboy.repository.model.MovieType;
import com.adg.homeboy.ui.list.MovieListActivity;
import com.adg.homeboy.ui.type.MovieTypeAdapter;
import com.adg.homeboy.util.BaseViewHolder;
import com.adg.homeboy.util.ScreenUtil;
import com.adg.homeboy.util.SystemUtils;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class TypeParentHolder extends BaseViewHolder<MovieType> {

    TextView name;
    View divdier;
    View empty;
    LinearLayout rootview;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public TypeParentHolder(ViewGroup parent) {
        super(parent, R.layout.item_type_parent);
        name = $(R.id.title);
        divdier = $(R.id.divider);
        empty = $(R.id.empty);
        rootview = $(R.id.root_view);
    }

    @Override
    public void setData(final MovieType data) {

        Log.e("name,type",data.name + "," + data.id);

        if (getAdapterPosition() == 0) {
            divdier.setVisibility(View.GONE);
        }

        if (getAdapterPosition() > 0 && getItemViewType() == MovieTypeAdapter.PARENT &&
                getOwnerAdapter().getItemViewType(getAdapterPosition() - 1) == MovieTypeAdapter.CHILD) {
            empty.setVisibility(View.VISIBLE);
        }

        name.setText(data.name);
//        rootview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MovieListActivity.class);
//                intent.putExtra("typeid",data.id);
//                getContext().startActivity(intent);
//                Log.e("name,type",data.name + "," + data.id);
//            }
//        });
    }
}
