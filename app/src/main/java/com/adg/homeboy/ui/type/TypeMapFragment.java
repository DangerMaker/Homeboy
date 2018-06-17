package com.adg.homeboy.ui.type;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.AmazingStore;
import com.adg.homeboy.repository.model.MovieType;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MoiveTypeMapResp;
import com.adg.homeboy.ui.home.AmazingListAdapter;
import com.adg.homeboy.ui.list.MovieListActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/1/8.
 */

public class TypeMapFragment extends BaseFragment {

    EasyRecyclerView mRecyclerView;
    MovieTypeAdapter adapter;
    GridLayoutManager manager;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_type_map;
    }

    @Override
    protected void onCreateView() {
        manager = new GridLayoutManager(mContext, 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                MovieType item = adapter.getItem(position);
                if (item.pid == 0) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView = (EasyRecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(manager);
        FrameLayout frameLayout = new FrameLayout(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200, 200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        adapter = new MovieTypeAdapter(mContext);
        mRecyclerView.setAdapterWithProgress(adapter);

//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(mContext, MovieListActivity.class);
//                intent.putExtra("typeid",adapter.getItem(position).id);
////                startActivity(intent);
//                Log.e("type",adapter.getItem(position).id + "");
//            }
//        });

        Call<MoiveTypeMapResp> call = RetrofitHelper.getMoiveApi().getTypeMap();
        call.enqueue(new Callback<MoiveTypeMapResp>() {
            @Override
            public void onResponse(Call<MoiveTypeMapResp> call, Response<MoiveTypeMapResp> response) {
                if (response.isSuccessful()) {
                    List<MovieType> sortList = new ArrayList<>();

                    List<MovieType> typeList = response.body().data;
                    int typeLength = typeList.size();
                    for (int i = 0; i < typeLength; i++) {
                        if (typeList.get(i).pid == 0) {
                            sortList.add(typeList.get(i));
                            for (int j = i + 1; j < typeLength; j++) {
                                if (typeList.get(j).pid == typeList.get(i).id) {
                                    sortList.add(typeList.get(j));
                                }
                            }
                        }
                    }

                    adapter.addAll(sortList);
                }
            }

            @Override
            public void onFailure(Call<MoiveTypeMapResp> call, Throwable t) {

            }
        });

    }
}
