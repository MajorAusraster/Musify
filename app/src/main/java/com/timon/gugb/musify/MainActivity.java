package com.timon.gugb.musify;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.timon.gugb.musify.fragments.MusicListFragment;
import com.timon.gugb.musify.fragments.SettingsFragment;
import com.timon.gugb.musify.fragments.TabFragment;
import com.timon.gugb.musify.managers.NotifyManager;
import com.timon.gugb.musify.managers.PreferenceManager;
import com.timon.gugb.musify.managers.StorageManager;
import com.timon.gugb.musify.music.Player;
import com.timon.gugb.musify.music.SDSong;
import com.timon.gugb.musify.music.Song;
import com.timon.gugb.musify.services.MediaPlayerService;
import com.timon.gugb.musify.utils.SongList;
import com.timon.gugb.musify.utils.Utils;
import com.timon.gugb.musify.views.ControlView;

public class MainActivity extends AppCompatActivity implements Player.PlayerCallback{

    /*Ui components*/
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout Drawer;
    private NavigationView navigationView;
    private ControlView controlView;
    private com.timon.gugb.musify.views.FloatingActionButton floatingActionButton;


    /*Constants for Permission*/
    public static final int REQUEST_LOAD_SONGS=5;



    /*The song list in which all the songs are stored*/
    SongList songList;

    /*Indicates whether the activity should load the songs and show the tab Fragment or wait till it
    * got the permission*/
    boolean shouldLoadSongs =false;


    /*The actucal player that is used to communicate to the service*/
    private Player player;

    private NotifyManager notifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        controlView= (ControlView) findViewById(R.id.control_view);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);

        /*init ui*/
        setSupportActionBar(toolbar);
        setupDrawer();
        setupFAB();
        setupControlView();

        notifyManager=new NotifyManager(this);
        notifyManager.postWidgetNotification();
        player=new Player(this);
        player.prepareServiceConnection();
        player.registerCallbackListener(this);
        loadSongs();

    }


    @Override
    protected void onDestroy(){
        player.destroy();
        super.onDestroy();
    }

    @Override
    protected void onStart(){
        super.onStart();
        player.startService();

    }

    @Override
    protected void onResume() {
        super.onResume();

        /*If we are coming from onPermissionResult should load songs is true when
        * the permission was granted*/
        if(shouldLoadSongs) {
            loadSongs();
            shouldLoadSongs =false;
        }
    }

    /*Method to load the songs from disk and show the default layout*/
    private void loadSongs() {
        int hasPermissionForStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(hasPermissionForStorage==PackageManager.PERMISSION_GRANTED){
            songList=new SongList();
            StorageManager manager=StorageManager.getInstance();
            manager.loadSongsFromStorage(this, songList);
            songList.sortAlphabetical();

            setupMyLibraryFragment();
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_LOAD_SONGS);
        }


    }






    private void setupMyLibraryFragment() {

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

     /*The Fragment for the main music content and the tabs that belong
            * to it*/
        TabFragment MyLibraryFragment;
        //Tabs:
        MusicListFragment AllSongsTab;
        MusicListFragment ArtistsTab;
        MusicListFragment AlbumTab;

        AllSongsTab=new MusicListFragment();
        ArtistsTab=new MusicListFragment();
        AlbumTab=new MusicListFragment();

        AllSongsTab.setSongList(songList,player);
        ArtistsTab.setSongList(songList,player);
        AlbumTab.setSongList(songList,player);

        MyLibraryFragment=new TabFragment();
        MyLibraryFragment.addTab(AllSongsTab, getString(R.string.all_songs));
        MyLibraryFragment.addTab(ArtistsTab, getString(R.string.artist));
        MyLibraryFragment.addTab(AlbumTab, getString(R.string.album));
        ft.replace(R.id.fragment, MyLibraryFragment);
        ft.commit();

    }

    private void setupSettingsFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(1)).start();

        SettingsFragment frag= new SettingsFragment();
        frag.setPreferenceManager(new PreferenceManager(player,this));
        ft.replace(R.id.fragment,frag);
        ft.commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOAD_SONGS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                  shouldLoadSongs =true;
                } else {
                    //permission denied
                   Snackbar snack= Snackbar.make(findViewById(android.R.id.content), getString(R.string.need_permission), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.try_again), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loadSongs();
                                }
                            });
                    snack.getView().setBackgroundColor(getResources().getColor(R.color.windowBackground_bright));
                    snack.show();
                    // permission denied, boo! Disable the

                }
                return;
            }
        }
    }


    protected void setupNaviationBar(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.windowBackground_bright));
        }
    };


    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setupFAB(){
        floatingActionButton= (com.timon.gugb.musify.views.FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // floatingActionButton.toggle();
                /*The toggleing is done in the onPlayerStatechanged because there we got the actual player state*/
                if (player.getMediaPlayerService().isPlaying()) {
                    player.getMediaPlayerService().pausePlayer();
                } else {
                    player.getMediaPlayerService().startPlayer();
                }
            }
        });
        floatingActionButton.fadeOut();
    };

    private void setupControlView(){
        controlView.setOnClickListener(new ControlView.onClickListener() {
            @Override
            public void onClick(int item) {
                switch (item){
                    case ControlView.BUTTON_TOGGLE_MODE:
                        //switch the button modes
                        switch (controlView.getCurrentMode()){
                            case ControlView.MODE_REPEAT:
                                controlView.setCurrentMode(ControlView.MODE_REPEAT_ONE);
                                player.getMediaPlayerService().setCurrentMode(MediaPlayerService.MODE_REPEAT_ONE);
                                break;
                            case ControlView.MODE_REPEAT_ONE:
                                controlView.setCurrentMode(ControlView.MODE_SHUFFLE);
                                player.getMediaPlayerService().setCurrentMode(MediaPlayerService.MODE_SHUFFLE);

                                break;
                            case ControlView.MODE_SHUFFLE:
                                controlView.setCurrentMode(ControlView.MODE_REPEAT);
                                player.getMediaPlayerService().setCurrentMode(MediaPlayerService.MODE_REPEAT);
                                break;
                        }
                        break;


                    case ControlView.BUTTON_NEXT:
                        if(player.getMediaPlayerService().isPlaying())
                        player.getMediaPlayerService().playNext();
                        break;
                    case ControlView.BUTTON_PREVIOUS:
                        if(player.getMediaPlayerService().isPlaying())
                        player.getMediaPlayerService().playPrev();
                        break;
                }
            }
        });
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();


        /*Setup the nav fragments*/
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment;

                        switch (menuItem.getItemId()) {
                            case R.id.my_library:
                                setupMyLibraryFragment();
                                Drawer.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.settings:
                                setupSettingsFragment();
                                Drawer.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.playlists:

                                break;
                            default:

                        }


                        return true;
                    }
                });


    }



    public Toolbar getToolbar(){
        return toolbar;
    }

    private void loadSDSongCover(SDSong song) {
        Bitmap cover=song.getCover(this);
        if(cover!=null){
            controlView.setCover(cover);
            notifyManager.getWidgetNotification().setCover(cover);
        }else{
            controlView.setCoverToDefault();
            notifyManager.getWidgetNotification().setCover(null);
        }
    }

    @Override
    public void onPlayerSongChanged(Song song, int currentPosition,String listID) {
            /*Control view in the drawer*/
            controlView.setText(song.getTitle(), song.getArtist());
            notifyManager.getWidgetNotification().setText(song.getTitle(), song.getArtist());


            if(song instanceof SDSong) {
                loadSDSongCover((SDSong) song);
             }

        notifyManager.getWidgetNotification().update();
        if(!floatingActionButton.isFadeIn()){
            floatingActionButton.fadeIn();
        }
    }


    @Override
    public void onPlayerStateChanged(int playerState) {
        if(playerState==Player.STATE_PLAYING&&floatingActionButton.isPlay()){
            floatingActionButton.toggle();
        }else if(playerState==Player.STATE_PAUSED&&!floatingActionButton.isPlay()){
            floatingActionButton.toggle();
        }
    }
}
