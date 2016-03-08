package com.timon.gugb.musify.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;


import com.timon.gugb.musify.R;

/**
 * Created by Timon on 07.03.2016.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragment_settings);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }

}
