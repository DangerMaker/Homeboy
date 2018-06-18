package com.adg.homeboy.ui.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.model.MovieType;
import com.adg.homeboy.repository.net.RetrofitHelper;
import com.adg.homeboy.repository.response.MoiveTypeMapResp;
import com.adg.homeboy.ui.type.holder.TypeChildHolder;
import com.adg.homeboy.util.BaseRecyclerViewAdapter;
import com.adg.homeboy.util.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2018/1/11.
 */

public class SearchFragment extends BaseFragment {

    SmartRefreshLayout refreshLayout;

    RecyclerView recycler;
    BaseRecyclerViewAdapter<MovieType> mAdapter;
    GridLayoutManager manager;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void onCreateView() {
        recycler = (RecyclerView) rootView.findViewById(R.id.recycler);
        refreshLayout = (SmartRefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

        if (mAdapter == null) {
            mAdapter = new BaseRecyclerViewAdapter<MovieType>(mContext) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new TypeChildHolder(parent);
                }
            };
        }

        manager = new GridLayoutManager(mContext, 2);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(mAdapter);

        Call<MoiveTypeMapResp> resp = RetrofitHelper.getMoiveApi().getHotword();
        resp.enqueue(new Callback<MoiveTypeMapResp>()

                     {
                         @Override
                         public void onResponse(Call<MoiveTypeMapResp> call, Response<MoiveTypeMapResp> response) {
                             if (response.isSuccessful()) {
                                 List<MovieType> list = response.body().data;
                                 mAdapter.addAll(list);
                             }
                         }

                         @Override
                         public void onFailure(Call<MoiveTypeMapResp> call, Throwable t) {

                         }
                     }

        );

        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = mAdapter.getAllData().get(position).name;
                ((SearchActivity)getActivity()).toSearch(name);
            }
        });
    }
}
