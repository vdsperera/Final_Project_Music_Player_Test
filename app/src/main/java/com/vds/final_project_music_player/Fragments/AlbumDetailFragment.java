package com.vds.final_project_music_player.Fragments;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vds.final_project_music_player.Adapters.AlbumSongsAdapter;
import com.vds.final_project_music_player.DataLoaders.AlbumLoader;
import com.vds.final_project_music_player.DataLoaders.AlbumSongLoader;
import com.vds.final_project_music_player.Listeners.SimpleTransitionListener;
import com.vds.final_project_music_player.Models.AlbumInfo;
import com.vds.final_project_music_player.Models.SongInfo;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Utils.ATEUtils;
import com.vds.final_project_music_player.Utils.Constants;
import com.vds.final_project_music_player.Utils.CupicUtils;
import com.vds.final_project_music_player.Utils.FabAnimationUtils;
import com.vds.final_project_music_player.Utils.Helpers;
import com.vds.final_project_music_player.Utils.ImageUtils;
import com.vds.final_project_music_player.Utils.PreferencesUtility;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailFragment extends Fragment {
    long albumId = -1;
    ImageView albumArt, artistArt;
    TextView albumTitle,albumDetail;
    RecyclerView recyclerView;
    AlbumSongsAdapter adapter;
    Toolbar toolbar;
    AlbumInfo album;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    FloatingActionButton fab;
    private boolean loadFailed = false;
    private PreferencesUtility preferencesUtility;
    private Context context;
    private int primaryColor = -1;

    public static AlbumDetailFragment newInstance(long id, boolean useTransition, String transitionName){
        AlbumDetailFragment fragment = new AlbumDetailFragment();
        Bundle args = new Bundle();
        args.putLong(Constants.ALBUM_ID,id);
        args.putBoolean("transition",useTransition);
        if(useTransition){
            args.putString("transition_name",transitionName);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            albumId = getArguments().getLong(Constants.ALBUM_ID);
        }
        context = getActivity();
        preferencesUtility = PreferencesUtility.getInstance(context);
    }

    public AlbumDetailFragment() {
        // Required empty public constructor
    }


    @TargetApi(21)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_album_detail,container,false);

        albumArt = rootView.findViewById(R.id.album_art);
        artistArt = rootView.findViewById(R.id.artist_art);
        albumTitle = rootView.findViewById(R.id.album_title);
        albumDetail = rootView.findViewById(R.id.album_detail);
        toolbar = rootView.findViewById(R.id.toolbar);
        fab = rootView.findViewById(R.id.fab);
        if(getArguments().getBoolean("transition")){
            albumArt.setTransitionName(getArguments().getString("transition_name"));
        }
        recyclerView = rootView.findViewById(R.id.recyclerview_albd);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsing_toolbar);
        appBarLayout = rootView.findViewById(R.id.app_bar);
        recyclerView.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        album = AlbumLoader.getAlbum(getActivity(),albumId);

        setAlbumart();
        setUpEverything();
        return rootView;
    }

    private void setupToolbar(){
/*        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(album.title); */
    }

    private void setAlbumart(){
        ImageUtils.loadAlbumartIntoView(album.id, albumArt, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                loadFailed = true;
                MaterialDrawableBuilder builder = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.SHUFFLE)
                        .setColor(CupicUtils.getBlackWhiteColor(Config.accentColor(context, Helpers.getATEKey(context))));
                ATEUtils.setFabBackgroundTint(fab, Config.accentColor(context, Helpers.getATEKey(context)));
                fab.setImageDrawable(builder.build());
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try {
                    new Palette.Builder(loadedImage).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch swatch = palette.getVibrantSwatch();
                            if(swatch != null){
                                primaryColor = swatch.getRgb();
                                collapsingToolbarLayout.setContentScrimColor(primaryColor);
                                if(getActivity() != null){
                                    //ATEUtil.set
                                }
                            }
                            else {
                                Palette.Swatch swatchMuted = palette.getMutedSwatch();
                                if (swatchMuted != null){
                                    primaryColor = swatchMuted.getRgb();
                                    collapsingToolbarLayout.setContentScrimColor(primaryColor);
                                    if(getActivity() != null){
                                        /////
                                    }
                                }
                            }

                            MaterialDrawableBuilder builder = MaterialDrawableBuilder.with(getActivity())
                                    .setIcon(MaterialDrawableBuilder.IconValue.SHUFFLE)
                                    .setSizeDp(30);
                            if(primaryColor != -1){
                                ATEUtils.setFabBackgroundTint(fab, Config.accentColor(context, Helpers.getATEKey(context)));
                                builder.setColor(CupicUtils.getBlackWhiteColor(Config.accentColor(context,Helpers.getATEKey(context))));
                                fab.setImageDrawable(builder.build());
                            }
                            else {
                                if(context != null) {
                                    ATEUtils.setFabBackgroundTint(fab, Config.accentColor(context,Helpers.getATEKey(context)));
                                    fab.setImageDrawable(builder.build());
                                }
                            }
                        }
                    });
                }catch (Exception ignored){

                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }

    private void setAlbumDetail(){
        String songCount = CupicUtils.makeLabel(getActivity(), R.plurals.Nsongs, album.songCount);
        String year = (album.year !=0 ) ? (" - " + String.valueOf(album.year)) : "";
        albumTitle.setText(album.title);
        albumDetail.setText(album.artistName + " - "+songCount + year);

    }

    private void setUpAlbumSongs(){
        List<SongInfo> songList = AlbumSongLoader.getSongsForAlbum(getActivity(),albumId);
        adapter = new AlbumSongsAdapter(getActivity(),songList, albumId);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setUpEverything(){
        setupToolbar();
        setAlbumDetail();
        setUpAlbumSongs();
    }

    private void reloadAdapter(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                List<SongInfo> songList = AlbumSongLoader.getSongsForAlbum(getActivity(), albumId);
                adapter.updateDataSet(songList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class EnterTransitionListener extends SimpleTransitionListener{
        @Override
        public void onTransitionEnd(@NonNull Transition transition) {
            FabAnimationUtils.scaleIn(fab);
        }

        @Override
        public void onTransitionStart(@NonNull Transition transition) {
            FabAnimationUtils.scaleOut(fab);
        }
    }
}
