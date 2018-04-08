package com.vds.final_project_music_player.Subfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vds.final_project_music_player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickControllsFragment extends Fragment {


    public QuickControllsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_nowplaying_card, container, false);
    }

}
