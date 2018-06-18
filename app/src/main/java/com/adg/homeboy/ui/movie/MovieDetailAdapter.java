package com.adg.homeboy.ui.movie;

import android.content.Context;
import android.view.ViewGroup;

import com.adg.homeboy.repository.model.MovieDetail;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.ui.movie.holder.MovieDetalHolder;
import com.adg.homeboy.ui.movie.holder.MovieRelatedHolder;
import com.adg.homeboy.util.BaseRecyclerViewAdapter;
import com.adg.homeboy.util.BaseViewHolder;

import java.security.InvalidParameterException;

/**
 * Created by liuxiaoyu on 2017/12/26.
 */

public class MovieDetailAdapter extends BaseRecyclerViewAdapter<Object> {

    public static final int DES = 1;
    public static final int ESLE = 2;

    public MovieDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        Object item = getItem(position);
        if(item instanceof MovieModel){
            return ESLE;
        }else if(item instanceof MovieDetail){
            return DES;
        }
        return -1;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case DES:
                return new MovieDetalHolder(parent);
            case ESLE:
                return new MovieRelatedHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}
