package com.adg.homeboy.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
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
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class AmazingFragment extends BaseFragment {
    EasyRecyclerView mRecyclerView;
    AmazingListAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_choice;
    }

    @Override
    protected void onCreateView() {
        mRecyclerView = (EasyRecyclerView) rootView.findViewById(R.id.recycler);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        FrameLayout frameLayout = new FrameLayout(mContext);
        ProgressBar progressBar = new ProgressBar(mContext);
        FrameLayout.LayoutParams vlp = new FrameLayout.LayoutParams(200, 200);
        vlp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(vlp);
        frameLayout.addView(progressBar);

        mRecyclerView.setProgressView(frameLayout);
        adapter = new AmazingListAdapter(mContext);
        mRecyclerView.setAdapterWithProgress(adapter);
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View searchView = LayoutInflater.from(getContext()).inflate(R.layout.item_search,null);
                searchView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mContext, SearchActivity.class));
                    }
                });
                return searchView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        getModel();
    }

    private void getModel(){
        MovieApi api = RetrofitHelper.getMoiveApi();
        Call<AmazingModelResp> resp =  api.getHomePage();
        resp.enqueue(new Callback<AmazingModelResp>() {
            @Override
            public void onResponse(Call<AmazingModelResp> call, Response<AmazingModelResp> response) {
                if(response.isSuccessful()){
                    adapter.addAll(response.body().data);
                }else{

                }
            }

            @Override
            public void onFailure(Call<AmazingModelResp> call, Throwable t) {

            }
        });
    }
}
