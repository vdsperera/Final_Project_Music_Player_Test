package com.vds.final_project_music_player;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.vds.final_project_music_player.DataLoaders.SongLoader;
import com.vds.final_project_music_player.Helpers.MusicPlaybackTrack;
import com.vds.final_project_music_player.Utils.CupicUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.WeakHashMap;

/**
 * Created by Vidumini on 2/21/2018.
 */

public class MusicPlayer extends Activity{
    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
