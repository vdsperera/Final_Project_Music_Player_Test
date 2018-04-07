package com.vds.final_project_music_player.DataLoaders;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.vds.final_project_music_player.Models.SongInfo;
import com.vds.final_project_music_player.Utils.PreferencesUtility;

import java.util.ArrayList;

/**
 * Created by Vidumini on 2/14/2018.
 */

public class AlbumSongLoader {
    private static final long[] emptyList = new long[0];

    public static ArrayList<SongInfo> getSongsForAlbum(Context context, long albumID){
        Cursor cursor = makeAlbumSongCursor(context, albumID);
        ArrayList arrayList = new ArrayList();
        if((cursor != null) && (cursor.moveToFirst())){
            do{
                long id = cursor.getLong(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String album = cursor.getString(3);
                int duration = cursor.getInt(4);
                int trackNumber = cursor.getInt(5);

                while (trackNumber >= 1000){
                    trackNumber = trackNumber - 1000;
                }
                long artistId  =  cursor.getInt(6);
                long albumId = albumID;
                arrayList.add(new SongInfo(id, albumId, artistId, title, artist, album, duration, trackNumber));
            }while (cursor.moveToNext());
            if(cursor !=null){
                cursor.close();
            }

        }
        return arrayList;
    }

    public static Cursor makeAlbumSongCursor(Context context, long albumId){
        ContentResolver resolver = context.getContentResolver();
        final String albumSongSortOrder = PreferencesUtility.getInstance(context).getAlbumSongSortOrder();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String string = "is_music=1 AND title != '' AND album_id=" + albumId;
        Cursor cursor = resolver.query(uri, new String[]{"_id","title","artist","album","duration","track","artist_id"},string,null, albumSongSortOrder);
        return cursor;
    }
}
