package com.vds.final_project_music_player.Widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.afollestad.appthemeengine.util.TintHelper;

/**
 * Created by Vidumini on 2/12/2018.
 */

public class PopupImageView extends android.support.v7.widget.AppCompatImageView {
    public PopupImageView(Context context) {
        super(context);

    }

    public PopupImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public PopupImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context,attrs,defStyleAttr);
        //super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void tint(){
        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme",false)){
            TintHelper.setTint(this, Color.parseColor("#eeeeee"));

        }
        else TintHelper.setTint(this,Color.parseColor("#434343"));
    }


}
