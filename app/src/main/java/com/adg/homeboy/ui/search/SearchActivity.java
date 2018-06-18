package com.adg.homeboy.ui.search;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseActivity;

/**
 * Created by liuxiaoyu on 2018/1/19.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    EditText searchEdit;
    RelativeLayout backBtn;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEdit = (EditText) findViewById(R.id.search_edit);
        backBtn = (RelativeLayout) findViewById(R.id.cancel_layout);
        backBtn.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, new SearchFragment());
//        transaction.addToBackStack("123");
        transaction.commit();

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isOK = true;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        String word = searchEdit.getText().toString().trim();
                        toSearch(word);
                        break;
                    default:
                        isOK = false;
                        break;
                }
                return isOK;
            }
        });

    }

    public void toSearch(String word) {

        if (!word.equals("")) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container, SearchResultFragment.getInstance(word));
            transaction.addToBackStack("234");
            transaction.commit();

            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_layout) {
            if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStackImmediate();
            }else {
                finish();
            }
        }
    }
}
