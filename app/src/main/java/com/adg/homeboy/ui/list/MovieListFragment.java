package com.adg.homeboy.ui.list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MovieListResp;
import com.adg.homeboy.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/1/10.
 */

public class MovieListFragment extends BaseFragment {

    RecyclerView mRecyclerView;
    SmartRefreshLayout refreshLayout;
    MovieListAdapter mAdapter;
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

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        mRecyclerView = rootView.findViewById(R.id.recycler);
        if (mAdapter == null)
            mAdapter = new MovieListAdapter(mContext);

        gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                refreshLayout.setEnableLoadMore(true);
                page = 0;
                getList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                page++;
                getList(false);
            }
        });

        refreshLayout.autoRefresh();
    }

    private void getList(final boolean isRefresh) {
        Call<MovieListResp> resp = RetrofitHelper.getMoiveApi().getList(typeid, "n", -1, page);
        resp.enqueue(new Callback<MovieListResp>() {
            @Override
            public void onResponse(Call<MovieListResp> call, Response<MovieListResp> response) {
                if (response.isSuccessful()) {
                    if (isRefresh) {
                        mAdapter.clear();
                    }

                    if(!response.body().data.isEmpty()) {
                        mAdapter.addAll(response.body().data);
                    }else{
                        if(!isRefresh){
                            ToastUtil.message(mContext,"以经到底了");
                            refreshLayout.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setEnableLoadMore(false);
                                }
                            },1000);
                        }
                    }
                    refreshLayout.finishRefresh(true);
                    refreshLayout.finishLoadMore(true);


                }
            }

            @Override
            public void onFailure(Call<MovieListResp> call, Throwable t) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });
    }

}
