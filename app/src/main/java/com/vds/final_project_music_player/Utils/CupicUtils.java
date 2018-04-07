package com.vds.final_project_music_player.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import com.vds.final_project_music_player.R;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class CupicUtils {

    public static Uri getAlbumArtUri(long albumId){
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),albumId);

    }

    public static int getBlackWhiteColor(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness >= 0.5) {
            return Color.WHITE;
        } else return Color.BLACK;
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static final String makeShortTimeString(final Context context,long secs){
        long hours,mins;
        hours = secs/3600;
        secs %= 3600;
        mins = secs/60;
        secs %= 60;

        final String durationFormat = context.getResources().getString(
          hours == 0 ? R.string.durationformatshort : R.string.durationformatlong);

        return String.format(durationFormat,hours,mins,secs);
    }

    public static final String makeLabel(final Context context, final int pluralInt, final int numeber){
        return context.getResources().getQuantityString(pluralInt, numeber, numeber);
    }

    public enum IdType{
        NA(0),Artist(1),Album(2),Playlist(3);
        public final int mId;

        IdType(final int id){
            mId = id;
        }

        public static IdType getTypeById(int id){
            for (IdType type : values()){
                if (type.mId == id){
                    return type;
                }
            }
            throw new IllegalArgumentException("Unrecognized id: " + id);
        }
    }





}
