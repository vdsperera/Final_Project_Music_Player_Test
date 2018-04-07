package com.vds.final_project_music_player.Utils;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by Vidumini on 2/14/2018.
 */

public class ATEUtils {




    public static void setFabBackgroundTint(FloatingActionButton fab, int color){
        ColorStateList fabColorStateList = new ColorStateList(
                new int[][]{
                        new int[]{}
                },
                new int[]{
                        color,
                }
        );
        fab.setBackgroundTintList(fabColorStateList);
    }
}
