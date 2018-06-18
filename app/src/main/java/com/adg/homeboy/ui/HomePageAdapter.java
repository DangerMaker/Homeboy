package com.adg.homeboy.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.adg.homeboy.ui.home.AmazingHomeFragment;
import com.adg.homeboy.ui.home.MemberWelfareFragment;
import com.adg.homeboy.ui.type.TypeMapFragment;
//import com.adg.homeboy.ui.user.CenterFragment;
import com.adg.homeboy.ui.user.LoginFragment;
import com.adg.homeboy.ui.user.LoginFragment1;
import com.adg.homeboy.ui.webview.WebViewFragment;

/**
 * Created by Administrator on 2016/8/1.
 */
public class HomePageAdapter extends FragmentPagerAdapter {

    public final String TAG = this.getClass().getSimpleName();
    public static final int ITEM_COUNT = 5;
    public static final int POSITION_HOME_ITEM = 0;

    public static final int POSITION_CHANNEL_ITEM = 1;
    public static final int POSITION_SUBSCRIBE_ITEM = 2;
    public static final int POSITION_VIP_ITEM = 3;
    public static final int POSITION_USER_ITEM = 4;

    private SparseArray<Fragment> fragments;

    public HomePageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments = new SparseArray<>();
    }

    private Fragment CreateFragment(int arg) {

        switch (arg) {
            case POSITION_HOME_ITEM:
                return new AmazingHomeFragment();
            case POSITION_CHANNEL_ITEM:
                return new TypeMapFragment();
            case POSITION_SUBSCRIBE_ITEM:
                return WebViewFragment.getInstance("file:///android_asset/webpage/firstcontent.html");
            case POSITION_VIP_ITEM:
                return  new MemberWelfareFragment();
            case POSITION_USER_ITEM:
//                return new CenterFragment();
        }
        return EmptyFragment.getInstance("parent",arg + "");
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment == null) {
            fragment = CreateFragment(position);
            fragments.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e(TAG, "instantiateItem: " + position );
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        Log.e(TAG, "destroyItem: " + position );

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "666";
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }
}
