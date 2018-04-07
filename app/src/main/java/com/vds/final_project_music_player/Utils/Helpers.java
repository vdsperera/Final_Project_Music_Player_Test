package com.vds.final_project_music_player.Utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class Helpers {
    public static String getATEKey(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("dark_theme", false) ?
                "dark_theme" : "light_theme";
    }
}
