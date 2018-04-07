package com.vds.final_project_music_player.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class PreferencesUtility {
    public static final String ALBUM_SORT_ORDER = "album_sort_order";
    public static final String ALBUM_SONG_SORT_ORDER = "album_song_sort_order";
    public static final String SONG_SORT_ORDER = "song_sort_order";
    private static final String TOGGLE_ALBUM_GRID = "toggle_album_grid";
    private static final String TOGGLE_HEADPHONE_PAUSE = "toggle_headphone_pause";

    private static PreferencesUtility instance;
    private static SharedPreferences preferences;
    private static Context context;
    private static ConnectivityManager connectivityManager;
    private static final String ALWAYS_LOAD_ALBUM_IMAGES_LASTFM = "always_load_album_images_lastfm";

    public PreferencesUtility(final Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final PreferencesUtility getInstance(final Context context){
        if (instance == null){
            instance = new PreferencesUtility(context.getApplicationContext());
        }
        return instance;
    }

    public final String getAlbumSortOrder(){
        return preferences.getString(ALBUM_SORT_ORDER,SortOrder.AlbumSortOrder.ALBUM_A_Z);
    }

    public boolean isAlbumsGrid(){
        return preferences.getBoolean(TOGGLE_ALBUM_GRID,true);
    }

    public  boolean alwaysLoadAlbumImagesFromLastFm(){
        return preferences.getBoolean(ALWAYS_LOAD_ALBUM_IMAGES_LASTFM,false);
    }

    public final String getAlbumSongSortOrder(){
        return preferences.getString(ALBUM_SONG_SORT_ORDER,SortOrder.AlbumSongSortOrder.SONG_TRACK_LIST);
    }

    public void setAlbumSongSortOrder(final String value){
        setSortOrder(SONG_SORT_ORDER, value);
    }

    private void setSortOrder(final String key, final String value){
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public boolean pauseEnabledOnDetach() {
        return preferences.getBoolean(TOGGLE_HEADPHONE_PAUSE, true);
    }

    public final String getSongSortOrder(){
        return preferences.getString(SONG_SORT_ORDER, SortOrder.SongSortOrder.SONG_A_Z);
    }

}
