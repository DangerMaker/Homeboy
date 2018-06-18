package com.adg.homeboy.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.model.MovieModel;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MovieListResp;
import com.adg.homeboy.ui.list.MovieListFragment;
import com.adg.homeboy.ui.movie.holder.MovieRelatedHolder;
import com.adg.homeboy.util.BaseRecyclerViewAdapter;
import com.adg.homeboy.util.BaseViewHolder;
import com.adg.homeboy.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/1/20.
 */

public class SearchResultFragment extends BaseFragment {

    SmartRefreshLayout refreshLayout;
    RecyclerView mRecyclerView;
    BaseRecyclerViewAdapter<MovieModel> mAdapter;
    GridLayoutManager gridLayoutManager;
    String keyword = "";
    int page = 0;

    public static SearchResultFragment getInstance(String keyword) {
        Bundle arguments = new Bundle();
        arguments.putString("keyword", keyword);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void onCreateView() {
        keyword = getArguments().getString("keyword");

        refreshLayout = rootView.findViewById(R.id.refreshLayout);
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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        if (mAdapter == null)
            mAdapter = new BaseRecyclerViewAdapter<MovieModel>(mContext) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new MovieRelatedHolder(parent);
                }
            };
        mRecyclerView.setAdapter(mAdapter);
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        refreshLayout.autoRefresh();
    }

    private void getList(final boolean isRefresh) {
        Call<MovieListResp> resp = RetrofitHelper.getMoiveApi().getList(-1, keyword, -1, page);
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
