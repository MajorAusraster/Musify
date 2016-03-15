package com.timon.gugb.musify.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.timon.gugb.musify.music.Player;

/**
 * Created by Timon on 13.03.2016.
 */
public class PreferenceManager implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String PREFERENCES_FILE_NAME ="musifyPrefs";
    public final static int MODE_PRIVATE=0;

    private Player mPlayer;
    private Context mContext;

    /*Constants for the preferences*/
    public static final String PREF_BASS_BOOST="bassBoost";
    public static final String PREF_VIRTULIZER="virtulizer";

    public PreferenceManager(Player player,Context context){
        mPlayer=player;
        mContext=context;
    }

    public void register(){
        mContext.getSharedPreferences(PREFERENCES_FILE_NAME,MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
    }

    public void unregister(){
        mContext.getSharedPreferences(PREFERENCES_FILE_NAME,MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.e("SHARED", "CHANGED");
            switch (key){
                case PREF_BASS_BOOST:
                    mPlayer.getMediaPlayerService().setBassBoost(sharedPreferences.getBoolean(PREF_BASS_BOOST,false));
                    break;
                case PREF_VIRTULIZER:
                    mPlayer.getMediaPlayerService().setVirtualizer(sharedPreferences.getBoolean(PREF_VIRTULIZER,false));
                    break;
            }
    }
}
