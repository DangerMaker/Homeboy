package com.adg.homeboy.ui.home;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.ui.EasyFragment;
import com.adg.homeboy.ui.EmptyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class AmazingHomeFragment extends BaseFragment {

    List<EasyFragment> fragments;

//    TabLayout tableLayout;
    ViewPager viewPager;

    AmazingHomeAdatper adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_amazing;
    }

    @Override
    protected void onCreateView() {
//        tableLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        AmazingFragment f1 = new AmazingFragment();
//        EmptyFragment f2 = EmptyFragment.getInstance("1", "2");
//        EmptyFragment f3 = EmptyFragment.getInstance("1", "3");
        EasyFragment fragment1 = new EasyFragment(f1, "每日推荐");
//        EasyFragment fragment2 = new EasyFragment(f2, "爱情片");
//        EasyFragment fragment3 = new EasyFragment(f3, "战争片");
        fragments = new ArrayList<>();
        fragments.add(fragment1);
//        fragments.add(fragment2);
//        fragments.add(fragment3);

        adapter = new AmazingHomeAdatper(getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

//        tableLayout.setupWithViewPager(viewPager);
//        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
