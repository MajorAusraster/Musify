package com.timon.gugb.musify.music;

import java.util.UUID;

/**
 * Created by Timon on 26.02.2016.
 */
public abstract class Song {

    // unique id for every song
    private final String ID= UUID.randomUUID().toString();

    //Title of the song
    private String title;

    //artist of the song
    private String artist;

    //album in which the song is
    private String album;

    //öenght of the song in sconds
    private int length;

    public Song(String title,String artist,String album,int length){
        this.title=title;
        this.artist=artist;
        this.album=album;
        this.length=length;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getLength(){
        return length;
    }

    public final String getID() {
        return ID;
    }
}
