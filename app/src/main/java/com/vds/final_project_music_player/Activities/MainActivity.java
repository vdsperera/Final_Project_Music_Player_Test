package com.vds.final_project_music_player.Activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

import com.afollestad.appthemeengine.ATEActivity;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.customizers.ATENavigationBarCustomizer;
import com.afollestad.appthemeengine.customizers.ATEToolbarCustomizer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vds.final_project_music_player.DataLoaders.SongLoader;
import com.vds.final_project_music_player.Fragments.AlbumsFragment;
import com.vds.final_project_music_player.Fragments.ArtistsFragment;
import com.vds.final_project_music_player.Fragments.MainFragment;
import com.vds.final_project_music_player.MusicController;
import com.vds.final_project_music_player.MusicService;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Fragments.SongsFragment;
import com.vds.final_project_music_player.MusicService.MusicBinder;

public class MainActivity extends ATEActivity implements ATEToolbarCustomizer, ATENavigationBarCustomizer, SongsFragment.OnMyFragmentListner,MediaController.MediaPlayerControl,View.OnClickListener {

    ViewPager viewPager;
    private MusicService musicService;
    private boolean mBound;

    private MusicController musicController;
    private boolean paused=false, playbackPaused=false;
    int xx=0;
    Button nxt;
    Button prev;
    Button pause;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        //ATE.config(this,null).coloredActionBar(true);

        //viewPager = findViewById(R.id.view_pager);
        //viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        Fragment fragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment).commitAllowingStateLoss();
        Log.d("muAp","Before Service");

        nxt = findViewById(R.id.pNext);
        prev = findViewById(R.id.pPrev);
        pause = findViewById(R.id.pPause);
        nxt.setOnClickListener(this);
        prev.setOnClickListener(this);
        pause.setOnClickListener(this);
        setController();

    }

    @Override
    protected void onStart() {
        super.onStart();
        doBindService();
        // Bind to LocalService
        //Intent intent = new Intent(getBaseContext(), MusicService.class);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        //startService(intent);
        Log.d("muAp","Actvity bounded"+ mBound);
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
        playbackPaused =true;
        musicService.pausePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused){
            setController();
            paused = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        musicController.hide();
    }

    void doBindService(){
        Log.d("muAp","Main doBind()");
        Intent intent = new Intent(getBaseContext(), MusicService.class);
        bindService(intent, mConnection,Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    void doUnbindService(){
        Log.d("muAp","Main doUnBind()");
        if(mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public int getLightToolbarMode(@Nullable Toolbar toolbar) {

        return Config.LIGHT_TOOLBAR_AUTO;
    }

    @Override
    public int getToolbarColor(@Nullable Toolbar toolbar) {

        return Color.BLACK;
    }

    @Override
    public int getNavigationBarColor()
    {
        return Color.BLUE;
    }

    @Override
    public void onActionPerformed(int pos) {
        Log.d("muAp","Activity onActionPerformed()");
        //if (xx==0){
            musicService.setSong(pos);
            musicService.playSong();
          //  xx=1;
        //}else if (xx==1){
            //playNext();
        //}

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("muAp","Service is connected");
            MusicBinder binder = (MusicBinder)iBinder;
            musicService = binder.getService();
            musicService.setList(SongLoader.getAllSongs(getApplicationContext()));
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("muAp","Service is disconnected");
            musicService = null;
            mBound = false;
        }
    };

    @Override
    public void start() {
        musicService.go();
    }

    @Override
    public void pause() {
        musicService.pausePlayer();
    }

    @Override
    public int getDuration() {
        if (musicService != null && mBound && isPlaying())
            return musicService.getDuration();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicService != null && mBound && musicService.isPlaying())
            return musicService.getPosition();
        else return 0;
    }

    @Override
    public void seekTo(int i) {
        musicService.seek(i);
    }

    @Override
    public boolean isPlaying() {
        if (musicService != null && mBound)
            return musicService.isPlaying();
        else return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    private void setController(){
        musicController = new MusicController(this);
        musicController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrev();
            }
        });

        musicController.setMediaPlayer(this);
        //musicController.setAnchorView();
        musicController.setEnabled(true);
    }

    private void playNext(){
        musicService.playNext();
        if (playbackPaused){
            setController();
            playbackPaused = false;
        }
        musicController.show(0);
    }

    private void playPrev(){
        musicService.playPrev();
        if (playbackPaused){
            setController();
            playbackPaused = false;
        }
        musicController.show(0);
    }


    public void songPicked(View view){
        musicService.setSong(Integer.parseInt(view.getTag().toString()));
        musicService.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        musicController.show(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     /*   int x=1;
        switch(x){
            case 2:
                musicService.setShuffle();
                break;

        };*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view==nxt){
            playNext();
        }
        else if (view==prev){
            playPrev();
        }
        else if (view==pause){
            if (isPlaying()){
                pause();
                paused = true;
            }
            else if (paused){
                musicService.go();
            }
            //pause.setBackground((View) findViewById(R.drawable.pauseNew));
        }
    }
}

class MyAdapter extends FragmentPagerAdapter{

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position==0){
            fragment = new SongsFragment();
        }
        if(position==1){
            fragment = new AlbumsFragment();
        }
        if(position==2){
            fragment = new ArtistsFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
