package com.adg.homeboy.ui.list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MovieListResp;
import com.adg.homeboy.ui.movie.holder.MovieRelatedHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/1/10.
 */

public class MovieListFragment extends BaseFragment {

    EasyRecyclerView mRecyclerView;
    RecyclerArrayAdapter<MovieModel> mAdapter;
    GridLayoutManager gridLayoutManager;
    int typeid = -1;
    int page = 0;


    public static MovieListFragment getInstance(int typeid) {
        Bundle arguments = new Bundle();
        arguments.putInt("typeid", typeid);
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_movie_list;
    }

    @Override
    protected void onCreateView() {
        typeid = getArguments().getInt("typeid");

        mRecyclerView = (EasyRecyclerView) rootView.findViewById(R.id.recycler);
        if (mAdapter == null)
            mAdapter = new RecyclerArrayAdapter<MovieModel>(mContext) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new MovieRelatedHolder(parent);
                }
            };

        FrameLayout frameLayout = new FrameLayout(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200, 200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        mRecyclerView.setErrorView(R.layout.view_error);
        mRecyclerView.setEmptyView(R.layout.view_empty);

        mRecyclerView.setAdapterWithProgress(mAdapter);
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                page++;
                getList();
            }

            @Override
            public void onMoreClick() {

            }
        });

        mAdapter.setNoMore(R.layout.view_nomore);

        getList();

    }

    private void getList() {
        Call<MovieListResp> resp = RetrofitHelper.getMoiveApi().getList(typeid, "n", -1, page);
        resp.enqueue(new Callback<MovieListResp>() {
            @Override
            public void onResponse(Call<MovieListResp> call, Response<MovieListResp> response) {
                if (response.isSuccessful()) {
                    mAdapter.addAll(response.body().data);
                    if (mAdapter.getAllData().isEmpty()) {
                        mRecyclerView.showEmpty();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieListResp> call, Throwable t) {

            }
        });
    }

}
