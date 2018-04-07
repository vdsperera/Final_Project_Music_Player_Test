package com.vds.final_project_music_player.DataLoaders;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.vds.final_project_music_player.Models.AlbumInfo;
import com.vds.final_project_music_player.Utils.PreferencesUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class AlbumLoader {

    public static AlbumInfo getAlbum(Cursor cursor){
        AlbumInfo album = new AlbumInfo();
        if(cursor != null){
            if(cursor.moveToFirst()){
                album = new AlbumInfo(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4), cursor.getInt(5));

            }
        }
        if(cursor != null){
            cursor.close();
        }
        return album;
    }

    public static List<AlbumInfo> getAlbumsForCursor(Cursor cursor){
        ArrayList arrayList = new ArrayList();
        if((cursor != null) && (cursor.moveToFirst())){
            do{
                arrayList.add(new AlbumInfo(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getInt(4), cursor.getInt(5)));
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return arrayList;
    }

    public static List<AlbumInfo> getAllAlbums(Context context){
        return getAlbumsForCursor(makeAlbumCursor(context,null,null));
    }

    public static AlbumInfo getAlbum(Context context,long id){
        return getAlbum(makeAlbumCursor(context,"_id=?",new String[]{String.valueOf(id)}));

    }

    public static List<AlbumInfo> getAlbums(Context context,String paramString,int limit){
        List<AlbumInfo> result = getAlbumsForCursor(makeAlbumCursor(context,"album LIKE ?",new String[]{paramString + "%"}));
        if (result.size()<limit){
            result.addAll(getAlbumsForCursor(makeAlbumCursor(context,"album LIKE ?",new String[]{"%_" + paramString + "%"})));
        }
        return result.size() < limit ? result : result.subList(0,limit);
    }

    public static Cursor makeAlbumCursor(Context context,String selection,String[] paramArrayOfStrinfg){
        final String albumSortOrder = PreferencesUtility.getInstance(context).getAlbumSortOrder();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,new String[]{"_id", "album", "artist", "artist_id", "numsongs", "minyear"},selection,paramArrayOfStrinfg,albumSortOrder);
        return cursor;
    }
}
