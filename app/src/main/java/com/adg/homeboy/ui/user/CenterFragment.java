package com.adg.homeboy.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.repository.user.UserJsonStore;
import com.adg.homeboy.repository.user.UserStore;
import com.adg.homeboy.util.CheckVersionUpdate;
import com.adg.homeboy.util.ImageLoader;
import com.adg.homeboy.util.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaoyu on 2018/3/6.
 */

public class CenterFragment extends BaseFragment implements View.OnClickListener {

    EasyRecyclerView mRecyclerView;
    RecyclerArrayAdapter mAdapter;
    LinearLayoutManager layoutManager;
    LinearLayout centerHeader;
    ImageView avatar;
    TextView name;

    UserStore userStore;
    final List<SettingModel> list = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_center;
    }

    @Override
    protected void onCreateView() {
        mRecyclerView = (EasyRecyclerView) rootView.findViewById(R.id.recycler);

        if (mAdapter == null)
            mAdapter = new RecyclerArrayAdapter<SettingModel>(mContext) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new SettingHolder(parent);
                }
            };


        mRecyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        centerHeader = (LinearLayout) rootView.findViewById(R.id.center_header);
        centerHeader.setOnClickListener(this);

        avatar = (ImageView) rootView.findViewById(R.id.user_avatar);
        avatar.setOnClickListener(this);

        name = (TextView) rootView.findViewById(R.id.txt_name);

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (list.get(position).id){
                    case 0:
                        CheckVersionUpdate.checkVersion(mContext,false);
                        break;
                    case 1:
                        ToastUtil.message(mContext,"暂不开放");
                        break;
                    case 2:
                        ToastUtil.message(mContext,"退出登录");
                        userStore.clear();
                        refreshUserStatus();
                        break;
                }
            }
        });

        userStore = UserJsonStore.getInstance();
        mAdapter.addAll(list);
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshUserStatus();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.center_header) {
            if (userStore.isUserExits()) {

            } else {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        } else {
            if (userStore.isUserExits()) {

            } else {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        }
    }

    private void refreshUserStatus(){
        String url = userStore.getUserAvatar();
        String username = userStore.getUserName();
        if (url != null) {
            ImageLoader.loadCircleImage(mContext, url, avatar);
        }else{
            avatar.setImageResource(R.drawable.avatar_default);
        }

        if (username != null) {
            name.setText(username);

            list.clear();
            mAdapter.clear();

            list.add(new SettingModel("检查更新",0));
            list.add(new SettingModel("开通会员",1));
            list.add(new SettingModel("退出登录",2));
            mAdapter.addAll(list);
        }else{
            name.setText("未登录");

            list.clear();
            mAdapter.clear();
            list.add(new SettingModel("检查更新",0));
            list.add(new SettingModel("开通会员",1));
            mAdapter.addAll(list);

        }
    }
}


