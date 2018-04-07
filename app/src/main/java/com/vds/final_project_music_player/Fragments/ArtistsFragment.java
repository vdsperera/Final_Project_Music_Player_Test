package com.vds.final_project_music_player.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vds.final_project_music_player.Adapters.ArtistAdpater;
import com.vds.final_project_music_player.Models.ArtistInfo;
import com.vds.final_project_music_player.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {

    private ArrayList<ArtistInfo> artistInfos = new ArrayList<ArtistInfo>();
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private boolean isGrid = true;
    private ArtistAdpater artistAdpater;
    private View rootView;

    public ArtistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

}
