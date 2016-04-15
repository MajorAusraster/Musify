package com.timon.gugb.musify.music;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

/**
 * Created by Timon on 26.02.2016.
 */
public class SDSong extends Song {

    private long coverId;
    private long uriId;

    public SDSong(String title, String artist, String album,int length,long coverId,long uriId) {
        super(title, artist, album,length);
        this.coverId=coverId;
        this.uriId=uriId;
    }

    public Bitmap getCover(Context context)
    {
        Bitmap bm = null;
        try
        {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, coverId);

            ParcelFileDescriptor pfd = context.getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public long getUriId(){
        return uriId;
    }


}
