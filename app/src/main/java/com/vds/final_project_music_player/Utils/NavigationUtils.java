package com.vds.final_project_music_player.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.vds.final_project_music_player.Fragments.AlbumDetailFragment;
import com.vds.final_project_music_player.R;

/**
 * Created by Vidumini on 2/14/2018.
 */

public class NavigationUtils {

    @TargetApi(21)
    public static  void navigateToAlbum(Activity context, long albumId, Pair<View, String> transitionViews){
        android.support.v4.app.FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        transaction.setCustomAnimations(R.anim.design_fab_out,R.anim.design_fab_out);
        fragment = AlbumDetailFragment.newInstance(albumId, false, null);
        transaction.hide(((AppCompatActivity)context).getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        transaction.add(R.id.fragment_container,fragment);
        transaction.addToBackStack(null).commit();

    }
}
