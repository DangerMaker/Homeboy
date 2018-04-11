package com.adg.homeboy.ui.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;

/**
 * Created by liuxiaoyu on 2018/1/10.
 */

public class MovieListActivity extends BaseActivity {

    String TAG = "list";
    int type = -1;

    RelativeLayout backLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        backLayout = (RelativeLayout) findViewById(R.id.toolbar);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        type = getIntent().getIntExtra("typeid",-1);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content, MovieListFragment.getInstance(type), TAG);
            ft.commit();
        }
    }
}
