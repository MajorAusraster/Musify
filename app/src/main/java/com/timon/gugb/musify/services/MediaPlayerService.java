package com.timon.gugb.musify.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Virtualizer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.timon.gugb.musify.MainActivity;
import com.timon.gugb.musify.music.Player;
import com.timon.gugb.musify.music.SDSong;
import com.timon.gugb.musify.utils.SongList;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Timon on 05.03.2016.
 */
public class MediaPlayerService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{




    /*The ID for the notification*/
    private static final int NOTIFY_ID=1321;

    /*The binder object which is used to communicate to other instances*/
    private final IBinder musicBind = new MusicBinder();

    /*The media player which plays the songs on low level */
    private MediaPlayer player;

    /*The current song list that is played*/
    private SongList currSongList;

    /*The current song position in the song list that is passed to the service*/
    private int songPosn;

    /*The current song Title*/
    private String songTitle="";

    /*Boolean to check if the current mode is shuffel and the random to generate the random song po
    * sition*/
    private boolean shuffle=false;
    private Random rand;

    /*Bass booster and virtulizer that are used to improve the sound quality*/
    private BassBoost bassBoost;
    private Virtualizer virtu;
    private short bassBoostStrenght=1000;
    private short virtualizerStrenght=750;

    /*The list of all interfaces which are used to create callbacks
    * this list is passed from the player.class to the service*/
    ArrayList<Player.PlayerCallback> listeners=new ArrayList<>();

    @Override
    public void onCreate(){
        //create the service
        super.onCreate();
        //initialize position
        songPosn=0;
        //the random for shuffle songs
        rand=new Random();
        //create player
        player = new MediaPlayer();

        //init methods
        initMusicPlayer();
        initAudioEffects();


    }


    public void initMusicPlayer() {
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    private void initAudioEffects() {
        bassBoost=new BassBoost(0,player.getAudioSessionId());
        if(bassBoost.getStrengthSupported()){
            bassBoost.setStrength(bassBoostStrenght);
        }
        bassBoost.setEnabled(false);

        virtu=new Virtualizer(0,player.getAudioSessionId());
        virtu.setStrength(virtualizerStrenght);
        virtu.setEnabled(false);
    }


    public void playSong(SDSong song){

        player.reset();
        songTitle=song.getTitle();
        //get id
        long currSongUriId = song.getUriId();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSongUriId);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        player.prepareAsync();

        //callback to the interface
        for(Player.PlayerCallback listener:listeners){
            listener.onPlayerSongChanged(song,songPosn);
        }

    }


    /**
     * Sets the current song list which is used in case the current song is over and
     * the next songs should be played
     * @param list the list
     */
    public void setCurrentSongList(SongList list){
        currSongList=list;
    }

    /**
     * Sets the current song position in a list so we know what song is the next
     * @param position the position
     */
    public void setCurrentSongPosition(int position){
        songPosn=position;
    }

    public void passListeners(ArrayList<Player.PlayerCallback> listenersList){
        listeners=listenersList;
    }

    public int getPosition(){
        return player.getCurrentPosition();
    }

    public int getDuration(){
        return player.getDuration();
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();

        //callback to the interface
        for(Player.PlayerCallback listener:listeners){
            listener.onPlayerStateChanged(Player.STATE_PAUSED);
        }

    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void startPlayer(){
        player.start();

        //callback to the interface
        for(Player.PlayerCallback listener:listeners){
            listener.onPlayerStateChanged(Player.STATE_PLAYING);
        }

    }

    public void playPrev(){
        songPosn--;
        //Play the last song in the list
        if(songPosn<0) {
            songPosn=currSongList.size()-1;
        }
        playSong((SDSong)currSongList.get(songPosn));
    }

    public void playNext(){
        if(shuffle){
            int newSong = songPosn;
            while(newSong==songPosn){
                newSong=rand.nextInt(currSongList.size());
            }
            songPosn=newSong;
        }
        else{
            //set song list to star if we have reached the end of the song list
            songPosn++;
            if(songPosn>=currSongList.size()) songPosn=0;
        }
        playSong((SDSong)currSongList.get(songPosn));
    }


    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return musicBind;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(player.getCurrentPosition()!=0){
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();

        //callback to the interface
        for(Player.PlayerCallback listener:listeners){
            listener.onPlayerStateChanged(Player.STATE_PLAYING);
        }

       //TODO notification

    }

    public class MusicBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }


}
