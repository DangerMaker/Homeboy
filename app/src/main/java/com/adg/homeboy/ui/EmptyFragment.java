package com.adg.homeboy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;


/**
 * Created by Administrator on 2016/8/1.
 */
public class EmptyFragment extends BaseFragment {

    public static final String ARGUMENT_LEVEL = "LEVEL";
    public static final String ARGUMENT_POS = "POS";

    TextView textView;

    String level;
    String position;

    public static EmptyFragment getInstance(String level, String position) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_LEVEL, level);
        arguments.putString(ARGUMENT_POS, position);
        EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void onCreateView() {
        level = getArguments().getString(ARGUMENT_LEVEL);
        position = getArguments().getString(ARGUMENT_POS);

        textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText("LEVEL: " + level + " POS:" + position);
    }


}
