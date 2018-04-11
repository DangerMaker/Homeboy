package com.adg.homeboy.ui.type;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.adg.homeboy.repository.model.MovieDetail;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.repository.model.MovieType;
import com.adg.homeboy.ui.movie.holder.MovieDetalHolder;
import com.adg.homeboy.ui.movie.holder.MovieRelatedHolder;
import com.adg.homeboy.ui.type.holder.TypeChildHolder;
import com.adg.homeboy.ui.type.holder.TypeParentHolder;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.security.InvalidParameterException;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class MovieTypeAdapter extends RecyclerArrayAdapter<MovieType> {

    public static final int PARENT = 1;
    public static final int CHILD = 2;
//    private OnItemClickListener mOnItemClickListener = null;

    public MovieTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        MovieType item = getItem(position);
        if (item.pid == 0) {
            return PARENT;
        } else {
            return CHILD;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//        parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
//                }
//            }
//        });

        switch (viewType) {
            case PARENT:
                return new TypeParentHolder(parent);
            case CHILD:
                return new TypeChildHolder(parent);
            default:
                throw new InvalidParameterException();
        }

    }

//    @Override
//    public void OnBindViewHolder(BaseViewHolder holder, int position) {
//        super.OnBindViewHolder(holder, position);
//        holder.itemView.setTag(position);
//    }
//
//    public  interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }
}
