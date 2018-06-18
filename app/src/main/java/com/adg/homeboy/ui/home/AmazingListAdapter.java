package com.adg.homeboy.ui.home;

import android.content.Context;
import android.view.ViewGroup;

import com.adg.homeboy.repository.model.AmazingModel;
import com.adg.homeboy.ui.home.holder.BannerHolder;
import com.adg.homeboy.ui.home.holder.HorizontalHolder;
import com.adg.homeboy.ui.home.holder.ThreeListHolder;
import com.adg.homeboy.ui.home.holder.TitleHolder;
import com.adg.homeboy.ui.home.holder.VideoListHolder;
import com.adg.homeboy.util.BaseRecyclerViewAdapter;
import com.adg.homeboy.util.BaseViewHolder;

import java.security.InvalidParameterException;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class AmazingListAdapter extends BaseRecyclerViewAdapter<AmazingModel> {
    public static final int TYPE_BANNER = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_HONRIZAONTAL = 2;
    public static final  int TYPE_THREE = 3;
    public static final  int TYPE_CATEGORY = 4;

    public AmazingListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        AmazingModel model = getItem(position);
        if(model.showStyle.equals(AmazingModel.TYPE_BANNER)){
            return TYPE_BANNER;
        }else if(model.showStyle.equals(AmazingModel.TYPE_LIST)){
            return TYPE_LIST;
        }else if(model.showStyle.equals(AmazingModel.TYPE_HONRIZAONTAL)){
            return TYPE_HONRIZAONTAL;
        }else if(model.showStyle.equals(AmazingModel.TYPE_THREE)){
            return TYPE_THREE;
        }else if(model.showStyle.equals(AmazingModel.TYPE_CATEGORY)){
            return TYPE_CATEGORY;
        }
        return -1;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_BANNER:
                return new BannerHolder(parent);
            case TYPE_LIST:
                return new VideoListHolder(parent);
            case TYPE_HONRIZAONTAL:
                return new HorizontalHolder(parent);
            case TYPE_THREE:
                return new ThreeListHolder(parent);
            case TYPE_CATEGORY:
                return new TitleHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}
