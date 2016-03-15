package com.timon.gugb.musify.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.timon.gugb.musify.services.MediaPlayerService;

import java.util.ArrayList;

/**
 * Created by Timon on 05.03.2016.
 */
public class Player {

    /*The list of all interfaces which are used to create callbacks*/
    ArrayList<PlayerCallback> listeners=new ArrayList<>();

    /*Constants for the player state*/
    public static final int STATE_PLAYING=32;
    public static final int STATE_PAUSED=211;

    private boolean musicBound=false; //indicatdes wheter the player is connected to the service or not
    private ServiceConnection musicConnection;
    private Intent playIntent;
    private MediaPlayerService mediaPlayerService;
    private Context context;

    public Player(Context context){
        this.context=context;
    }

    public void prepareServiceConnection(){
        musicConnection = new ServiceConnection(){

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MediaPlayerService.MusicBinder binder = (MediaPlayerService.MusicBinder)service;
                //get service
                mediaPlayerService = binder.getService();
                mediaPlayerService.passListeners(listeners);
                musicBound = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicBound = false;
            }
        };
    }

    public void startService(){
        if(playIntent==null){
            playIntent = new Intent(context, MediaPlayerService.class);
            context.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            context.startService(playIntent);
        }
    }

    public void destroy(){
        context.unbindService(musicConnection);
        context.stopService(playIntent);
        mediaPlayerService=null;
        musicConnection=null;
    }

    public void registerCallbackListener(PlayerCallback callbackClass){
        listeners.add(callbackClass);
    }

    public boolean isConnected(){
        return musicBound;
    }

    public MediaPlayerService getMediaPlayerService(){
        return mediaPlayerService;
    }

    /*Interface for callbacks like songChanged. This interface should work with the service and the spotify player so the ui
    * can sync with both*/
    public interface PlayerCallback {

        void onPlayerSongChanged(Song song,int currentPosition,String listID);

        void onPlayerStateChanged(int playerState);
    }

}
