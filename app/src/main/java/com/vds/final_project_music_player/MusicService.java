package com.vds.final_project_music_player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.vds.final_project_music_player.Activities.MainActivity;
import com.vds.final_project_music_player.Adapters.SongAdapter;
import com.vds.final_project_music_player.DataLoaders.SongLoader;
import com.vds.final_project_music_player.Models.SongInfo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vidumini on 2/23/2018.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mediaPlayer;
    private ArrayList<SongInfo> songs;
    private int songPos;
    private final IBinder mBinder = new MusicBinder();
    SongAdapter adapter;

    private String songTitle="";
    private static final int NOTIFY_ID=1;
    private boolean shuffle = false;
    private Random random;

    public class MusicBinder extends Binder {

        public MusicService getService(){
            Log.d("muAp","Service getService()");
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d("muAp","Service OnCreate");
        super.onCreate();
        songPos = 0;
        mediaPlayer = new MediaPlayer();
        initMediaPlayer();
        random = new Random();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);

    }

    public void initMediaPlayer(){
        Log.d("muAp","Service initMediaPlayer()");
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    public void playSong(){
        Log.d("muAp","Service playSong()");
        mediaPlayer.reset();

        SongInfo playSong = songs.get(songPos);
        songTitle = playSong.getTitle();
        long currSong = playSong.getId();
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currSong);
        try {
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            initMediaPlayer();
            mediaPlayer.prepareAsync();
        }catch (Exception ee){
            Log.d("muAp","errrrrrrrr");
        }


    }

    public void setSong(int songIndex){
        Log.d("muAp","Service setSong()");
        songPos = songIndex;
    }

    public void setList(ArrayList<SongInfo> songInfos){
        Log.d("muAp","Service setList()");
        songs = songInfos;
    }

    public int getPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void pausePlayer(){
        mediaPlayer.pause();
    }

    public void seek(int position){
        mediaPlayer.seekTo(position);
    }

    public void go(){
        mediaPlayer.start();
    }

    public void playPrev(){
        songPos--;
        if (songPos < 0 )
            songPos = songs.size()-1;
        playSong();
    }

    public void playNext(){
        if (shuffle){
            int newSong  = songPos;
            while (newSong == songPos){
                newSong = random.nextInt(songs.size());
            }
            songPos = newSong;
        }
        else {
            songPos++;
            if (songPos >= songs.size())
                songPos = 0;
        }

        playSong();
    }

    public void setShuffle(){
        if (shuffle)
            shuffle = false;
        else
            shuffle = true;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("muAp","Service onBind()");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("muAp","Service onUnbind()");
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("muAp","Service onCompletion()");
        if (mediaPlayer.getCurrentPosition()>0){
            mediaPlayer.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.d("muAp","Service onError()");
        mediaPlayer.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("muAp","Service onPrepared()");
        mediaPlayer.start();

        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(this,0,notIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendIntent)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("")
                .setContentText(songTitle);
        Notification not = builder.build();
        startForeground(NOTIFY_ID,not);
    }
}
