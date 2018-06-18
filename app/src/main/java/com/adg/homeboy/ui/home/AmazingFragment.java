package com.adg.homeboy.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.AmazingStore;
import com.adg.homeboy.repository.model.AmazingModel;
import com.adg.homeboy.repository.net.MovieApi;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.AmazingModelResp;
import com.adg.homeboy.ui.search.SearchActivity;
import com.adg.homeboy.util.OnListScrollY;
import com.adg.homeboy.util.SystemUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class AmazingFragment extends BaseFragment {
    SmartRefreshLayout refreshLayout;
    RecyclerView mRecyclerView;
    AmazingListAdapter adapter;
    int offset = 0;
    OnListScrollY scrollY;

    public static AmazingFragment getInstance(OnListScrollY scrollY) {
        AmazingFragment f = new AmazingFragment();
        f.scrollY = scrollY;
        return f;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_choice;
    }

    @Override
    protected void onCreateView() {
        mRecyclerView = rootView.findViewById(R.id.recycler);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext())
                .setAccentColorId(android.R.color.white)
                .setPrimaryColorId(android.R.color.transparent)
        );

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getModel();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        adapter = new AmazingListAdapter(mContext);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                offset = offset + dy;
                scrollY.y(offset);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        refreshLayout.autoRefresh();
    }

    private void getModel() {
        MovieApi api = RetrofitHelper.getMoiveApi();
        Call<AmazingModelResp> resp = api.getHomePage();
        resp.enqueue(new Callback<AmazingModelResp>() {
            @Override
            public void onResponse(Call<AmazingModelResp> call, Response<AmazingModelResp> response) {
                if (response.isSuccessful()) {
                    adapter.clearAndAddAll(response.body().data);
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishRefresh(false);
                }
            }

            @Override
            public void onFailure(Call<AmazingModelResp> call, Throwable t) {
                refreshLayout.finishRefresh(false);
            }
        });
    }
}
