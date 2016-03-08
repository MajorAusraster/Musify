package com.timon.gugb.musify.utils;

import com.timon.gugb.musify.music.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Timon on 26.02.2016.
 */
public class SongList extends ArrayList<Song> {

    public Song findSongById(String id){
        for(Song dmy:this){
            if(dmy.getID()==id){
                return dmy;
            }
        }
        return null;
    }

    public void sortAlphabetical(){
        Collections.sort(this, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }

}
