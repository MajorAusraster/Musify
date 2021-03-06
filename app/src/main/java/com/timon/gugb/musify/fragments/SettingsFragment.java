package com.timon.gugb.musify.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.timon.gugb.musify.R;
import com.timon.gugb.musify.managers.PreferenceManager;
import com.timon.gugb.musify.views.DividerItemDecoration;

/**
 * Created by Timon on 07.03.2016.
 *
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    PreferenceManager mPreferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings);

    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(mPreferenceManager);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mPreferenceManager);
        super.onPause();
    }

    public void setPreferenceManager(PreferenceManager hPreferenceManager){
        mPreferenceManager=hPreferenceManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        final int padding_top= (int)getContext().getResources().getDimension(R.dimen.margin_top_prefences);
            getListView().setPadding(10, padding_top, 10, 10);
            // getListView().addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        return v;
    }

}
