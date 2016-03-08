package com.timon.gugb.musify.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timon.gugb.musify.R;
import com.timon.gugb.musify.adapters.TabsPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Timon on 27.02.2016.
 */
public class TabFragment extends Fragment {

    public static final String KEY_TAB_NAME="tab_name";

    ArrayList<Fragment> tabs=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View content =inflater.inflate(R.layout.fragment_tab, container, false);

        TabLayout tabLayout = (TabLayout) content.findViewById(R.id.tab_layout);
        ViewPager pager = (ViewPager) content.findViewById(R.id.pager);

        tabLayout.bringToFront();
        //passing the tab layout to the list fragments so they know when to hide it
        for(Fragment t:tabs){
            if(t instanceof MusicListFragment){
                MusicListFragment musicListFragment=(MusicListFragment)t;
                musicListFragment.setTabLayout(tabLayout);
            }
        }

        TabsPagerAdapter adapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager(),tabs);


        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        return content;
    }

    public void addTab(Fragment fragment,String title){
        Bundle b=new Bundle();
        b.putString(KEY_TAB_NAME,title);
        fragment.setArguments(b);
        tabs.add(fragment);
    }

}
