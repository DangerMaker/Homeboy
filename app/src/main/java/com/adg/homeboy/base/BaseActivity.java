package com.adg.homeboy.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.adg.homeboy.R;

/**
 * Created by liuxiaoyu on 2017/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected Context mContext;
    View backImageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseContext();
    }


    public void setContentView(int layout){
        super.setContentView(layout);
        backImageView = findViewById(R.id.ic_back);
        if(backImageView != null){
            backImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
