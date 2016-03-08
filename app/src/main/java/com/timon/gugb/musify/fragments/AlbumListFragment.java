package com.timon.gugb.musify.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.timon.gugb.musify.MainActivity;
import com.timon.gugb.musify.R;
import com.timon.gugb.musify.adapters.RecyclerAdapter;
import com.timon.gugb.musify.interfaces.ItemClickSupport;
import com.timon.gugb.musify.utils.HidingScrollListener;
import com.timon.gugb.musify.utils.RecyclerItemViewHolder;
import com.timon.gugb.musify.utils.SongList;
import com.timon.gugb.musify.views.DividerItemDecoration;

/**
 * Created by Timon on 26.02.2016.
 */
public class AlbumListFragment extends Fragment {

    private SongList songList;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_music_list, container, false);

        final RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);

        final MainActivity activity=(MainActivity)getActivity();

        int paddingTop = 330;
        recyclerView.setPadding(recyclerView.getPaddingLeft(), paddingTop, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(songList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnScrollListener(new HidingScrollListener(activity) {
            @Override
            public void onMoved(int distance) {
                activity.getToolbar().setTranslationY(-distance);
                tabLayout.setTranslationY(-distance);

            }

            @Override
            public void onShow() {
                tabLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                activity.getToolbar().animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void onHide() {
                tabLayout.animate().translationY(-activity.getToolbar().getBottom()).setInterpolator(new AccelerateInterpolator(2)).start();
                activity.getToolbar().animate().translationY(-activity.getToolbar().getBottom()).setInterpolator(new AccelerateInterpolator(2)).start();
            }

        });

        ItemClickSupport listener= ItemClickSupport.addTo(recyclerView);
        listener.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, View view, int position) {
                RecyclerItemViewHolder dmy= (RecyclerItemViewHolder) recyclerView.getChildViewHolder(view);
                Toast.makeText(getContext(),dmy.getSongId(),Toast.LENGTH_SHORT).show();
            }
        });


        return contentView;
    }

    public void setSongList(SongList list) {
        songList = list;
    }

    public void setTabLayout(TabLayout layout){
        tabLayout=layout;
    }

}