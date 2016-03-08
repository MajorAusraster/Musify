package com.timon.gugb.musify.adapters;

/**
 * Created by Timon on 27.02.2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.timon.gugb.musify.fragments.TabFragment;

import java.util.ArrayList;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> tabs=new ArrayList<>();

    public TabsPagerAdapter(FragmentManager fm, ArrayList<Fragment> tab) {
        super(fm);
        tabs=tab;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getArguments().getString(TabFragment.KEY_TAB_NAME);
    }
}
