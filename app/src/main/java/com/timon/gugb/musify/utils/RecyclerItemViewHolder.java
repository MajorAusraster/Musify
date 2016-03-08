package com.timon.gugb.musify.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timon.gugb.musify.R;
import com.timon.gugb.musify.managers.StorageManager;

/**
 * Created by Timon on 28.02.2016.
 */
public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView mTitleTextView;
    private final TextView mArtistTextView;
    private final TextView mLenghtTextView;
    private String SongId;


    public RecyclerItemViewHolder(final View parent, TextView TitleItemTextView, TextView ArtistItemTextView,TextView LengthTextView) {
        super(parent);
        mTitleTextView=TitleItemTextView;
        mArtistTextView=ArtistItemTextView;
        mLenghtTextView=LengthTextView;
    }

    public void setItemText(CharSequence title,CharSequence artist,CharSequence length) {
        mTitleTextView.setText(title);
        mArtistTextView.setText(artist);
        mLenghtTextView.setText(length);
    }

    public void setSongId(String id){
        SongId=id;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView TitleItemTextView = (TextView) parent.findViewById(R.id.song_title);
        TextView ArtistItemTextView =(TextView) parent.findViewById(R.id.song_artist);
        TextView LengthTextView=(TextView) parent.findViewById(R.id.song_length);
        return new RecyclerItemViewHolder(parent, TitleItemTextView,ArtistItemTextView,LengthTextView);
    }

    public final String getSongId(){
        return SongId;
    }


}
