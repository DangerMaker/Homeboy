package com.adg.homeboy.ui.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private Context context;
    private List<MovieModel> data;

    int padding = 4;
    int margin;
    DisplayMetrics dm;


    public MovieListAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();

        this.dm = context.getResources().getDisplayMetrics();
        margin = ScreenUtil.dip2px(context, padding);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subitem_videolist, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieModel data = this.data.get(position);
        holder.title.setText(data.name);

        ViewGroup.LayoutParams params = holder.bg.getLayoutParams();
//        params.width = (dm.widthPixels - 2 * margin) / 3;
        params.width = ScreenUtil.getScreenWidth(context) / 3;
        params.height = (int) (params.width / 0.667);
        ImageLoader.loadImage(context, data.pic, holder.bg);
        setImageListener(holder.itemView, data);

        ViewGroup.LayoutParams textParams = holder.textLayout.getLayoutParams();
        textParams.width = (dm.widthPixels - 2 * margin) / 3;
        textParams.height = ScreenUtil.dip2px(this.context, 40);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView bg;
        RelativeLayout textLayout;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            bg = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            textLayout = itemView.findViewById(R.id.text_layout);
        }
    }

    private void setImageListener(final View view, final MovieModel model) {
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", model.id);
                intent.putExtra("pic", model.pic);
                intent.putExtra("playurl", model.playurl);
                JumpActivityUtils.toPlay(intent, view.getContext());
            }
        });
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<MovieModel> newList){
        data.addAll(newList);
        notifyDataSetChanged();
    }

    public List<MovieModel> getAllData(){
        return data;
    }
}
