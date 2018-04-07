package com.vds.final_project_music_player.Utils;

import android.provider.MediaStore;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class SortOrder {

    public interface AlbumSortOrder{
        String ALBUM_A_Z = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
    }

    public interface AlbumSongSortOrder {
        /* Album song sort order A-Z */
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        /* Album song sort order Z-A */
        String SONG_Z_A = SONG_A_Z + " DESC";

        /* Album song sort order track list */
        String SONG_TRACK_LIST = MediaStore.Audio.Media.TRACK + ", "
                + MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        /* Album song sort order duration */
        String SONG_DURATION = SongSortOrder.SONG_DURATION;

        /* Album Song sort order year */
        String SONG_YEAR = MediaStore.Audio.Media.YEAR + " DESC";

        /* Album song sort order filename */
        String SONG_FILENAME = SongSortOrder.SONG_FILENAME;
    }

    public interface SongSortOrder {
        /* Song sort order A-Z */
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        /* Song sort order Z-A */
        String SONG_Z_A = SONG_A_Z + " DESC";

        /* Song sort order artist */
        String SONG_ARTIST = MediaStore.Audio.Media.ARTIST;

        /* Song sort order album */
        String SONG_ALBUM = MediaStore.Audio.Media.ALBUM;

        /* Song sort order year */
        String SONG_YEAR = MediaStore.Audio.Media.YEAR + " DESC";

        /* Song sort order duration */
        String SONG_DURATION = MediaStore.Audio.Media.DURATION + " DESC";

        /* Song sort order date */
        String SONG_DATE = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        /* Song sort order filename */
        String SONG_FILENAME = MediaStore.Audio.Media.DATA;
    }
}
