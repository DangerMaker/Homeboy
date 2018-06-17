package com.adg.homeboy.ui.home;


import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.adg.homeboy.R;
import com.adg.homeboy.base.BaseFragment;
import com.adg.homeboy.ui.EasyFragment;
import com.adg.homeboy.ui.EmptyFragment;
import com.adg.homeboy.util.OnListScrollY;
import com.adg.homeboy.util.SystemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxiaoyu on 2017/12/20.
 */

public class AmazingHomeFragment extends BaseFragment {

    List<EasyFragment> fragments;

    //    TabLayout tableLayout;
    ViewPager viewPager;
    ImageView topBg;
    ImageView scan;
    ImageView clock;
    ImageView searchBg;
    TextView searchTv;
    ImageView line;
    int lastoffset = 0;

    AmazingHomeAdatper adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_amazing;
    }

    @Override
    protected void onCreateView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        topBg = (ImageView) rootView.findViewById(R.id.top_bg);
        scan = (ImageView) rootView.findViewById(R.id.scan);
        clock = (ImageView) rootView.findViewById(R.id.clock);
        searchBg = (ImageView) rootView.findViewById(R.id.search_bg);
        searchTv = (TextView) rootView.findViewById(R.id.search_txt);
        line = (ImageView) rootView.findViewById(R.id.line);

        AmazingFragment f1 = AmazingFragment.getInstance(new OnListScrollY() {
            @Override
            public void y(int offset) {
                if (offset < 255 && offset >= 0) {
                    System.out.println("offset:" + offset);
                    topBg.setAlpha((255 - offset) / 255f);
                    scan.setColorFilter(Color.argb(255, 255 - offset, 255 - offset, 255 - offset));
                    clock.setColorFilter(Color.argb(255, 255 - offset, 255 - offset, 255 - offset));
                    searchBg.setColorFilter(Color.argb(255, 255 - offset, 255 - offset, 255 - offset));
                    searchTv.setTextColor(Color.argb(255, 255 - offset, 255 - offset, 255 - offset));
                    line.setColorFilter(Color.argb(255, 255 - offset, 255 - offset, 255 - offset));
                } else {
                    if(lastoffset > 255){
                        return;
                    }
                    topBg.setAlpha(0f);
                    scan.setColorFilter(Color.BLACK);
                    clock.setColorFilter(Color.BLACK);
                    searchBg.setColorFilter(Color.BLACK);
                    searchTv.setTextColor(Color.BLACK);
                    line.setColorFilter(Color.BLACK);
                }
                lastoffset = offset;
            }
        });
//        EmptyFragment f2 = EmptyFragment.getInstance("1", "2");
//        EmptyFragment f3 = EmptyFragment.getInstance("1", "3");
        EasyFragment fragment1 = new EasyFragment(f1, "每日推荐");
//        EasyFragment fragment2 = new EasyFragment(f2, "爱情片");
//        EasyFragment fragment3 = new EasyFragment(f3, "战争片");
        fragments = new ArrayList<>();
        fragments.add(fragment1);
//        fragments.add(fragment2);
//        fragments.add(fragment3);

        adapter = new AmazingHomeAdatper(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

//        tableLayout.setupWithViewPager(viewPager);
//        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
