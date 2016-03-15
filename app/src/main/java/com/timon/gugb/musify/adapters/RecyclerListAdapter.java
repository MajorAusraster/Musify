package com.timon.gugb.musify.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timon.gugb.musify.R;
import com.timon.gugb.musify.utils.RecyclerItemViewHolder;
import com.timon.gugb.musify.utils.SongList;
import com.timon.gugb.musify.utils.Utils;

import java.util.List;

/**
 * Created by Timon on 28.02.2016.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SongList songList;
    private int selectedItem=-1;
    private Context context;

    public RecyclerListAdapter(Context context,SongList songList) {
        this.songList = songList;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_row_item, parent, false);
        return RecyclerItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;

        String title=songList.get(position).getTitle();

        String artist=songList.get(position).getArtist();

        String lenght= Utils.Converter.timeToDisplayForm(songList.get(position).getLength());


        holder.setItemText(title,artist,lenght);
        holder.setSongId(songList.get(position).getID());
        if(position==selectedItem){
            holder.selectItem(context);
        }else{
            holder.disselectItem(context);
        }
    }


    @Override
    public int getItemCount() {
        return songList.size();
    }

    public void setSelectedItem(int position){
        selectedItem=position;
        notifyDataSetChanged();
    }

}
