package com.adg.homeboy.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.adg.homeboy.ui.EasyFragment;

import java.util.List;


/**
 * Created by Administrator on 2016/11/27.
 */
public class AmazingHomeAdatper extends FragmentPagerAdapter {

    private List<EasyFragment> fragments;

    public AmazingHomeAdatper(FragmentManager fm, List<EasyFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }
}
