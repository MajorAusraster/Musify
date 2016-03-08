package com.timon.gugb.musify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timon.gugb.musify.R;
import com.timon.gugb.musify.music.Song;
import com.timon.gugb.musify.utils.SongList;

/**
 * Created by Timon on 26.02.2016.
 */
public class MusicListAdapter extends BaseAdapter {

    private SongList songList;
    private LayoutInflater songInf;
    private Context c;


    public MusicListAdapter(Context c, SongList songs){
        songList=songs;
        songInf=LayoutInflater.from(c);
        this.c=c;
    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return songList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup parent) {
        //map to song layout
        RelativeLayout songLay = (RelativeLayout)songInf.inflate(R.layout.song_list_row_item, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);

        //get song using position
        Song currSong = songList.get(position);

        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());

        //set position as tag
        songLay.setTag(currSong.getID());



        return songLay;
    }


}
