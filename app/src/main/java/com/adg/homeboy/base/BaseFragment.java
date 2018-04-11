package com.adg.homeboy.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuxiaoyu on 2017/12/17.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }

        onCreateView();
        return rootView;
    }

    protected abstract int getLayoutResource();

    protected abstract void onCreateView();
}
