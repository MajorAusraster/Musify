package com.timon.gugb.musify.managers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.timon.gugb.musify.music.SDSong;
import com.timon.gugb.musify.utils.SongList;

/**
 * Created by Timon on 28.02.2016.
 */
public class StorageManager {

    private static StorageManager storageManager=new StorageManager();

    private StorageManager(){
    }

    public void loadSongsFromStorage(Activity context,SongList list){


        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);

            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);

            int lenghtColumn =musicCursor.getColumnIndex
                    ( MediaStore.Audio.Media.DURATION);


            int coverId = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM_ID);

            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            //add songs to list
            do {

                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum=musicCursor.getString(albumColumn);
                int lenght=(musicCursor.getInt(lenghtColumn)/1000);//convert to seconds
                long thisId = musicCursor.getLong(idColumn);
                long coverPath=musicCursor.getLong(coverId);

                list.add(new SDSong(thisTitle, thisArtist, thisAlbum,lenght, coverPath,thisId));

            }
            while (musicCursor.moveToNext());
        }
    }

    public static StorageManager getInstance(){
        return storageManager;
    }

}
