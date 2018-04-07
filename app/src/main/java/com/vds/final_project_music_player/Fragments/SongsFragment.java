package com.vds.final_project_music_player.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.vds.final_project_music_player.Activities.MainActivity;
import com.vds.final_project_music_player.DataLoaders.SongLoader;
import com.vds.final_project_music_player.MusicService;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Adapters.SongAdapter;
import com.vds.final_project_music_player.Models.SongInfo;
import com.vds.final_project_music_player.Utils.PreferencesUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {

   /* private ArrayList<SongInfo> songInfos = new ArrayList<SongInfo>();
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    MediaPlayer mediaPlayer;
    View rootView;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecoration;*/
   boolean playState = false;
   MediaPlayer mediaPlayer;
   private SongAdapter songAdapter;
   private RecyclerView recyclerView;
   private PreferencesUtility preferencesUtility;

   MusicService service;
    private OnMyFragmentListner mCallback;




    public SongsFragment() {
        // Required empty public constructor
    }

    public interface OnMyFragmentListner{
        public void onActionPerformed(int pos);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       /* rootView = inflater.inflate(R.layout.fragment_recyclerview,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //songAdapter = new SongAdapter(getContext(),songInfos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(songAdapter); */

     /*   songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Button b, View v, SongInfo obj, int possition) {
                try {
                    if (b.getText().toString().equals("Stop")) {
                        b.setText("Play");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;

                    } else {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(obj.getSongURL());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.start();
                                b.setText("Stop");
                            }
                        });
                    }
                }
                catch (IOException e){

                }
            }
        });*/


       // new LoadSongs().execute("");
        View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //new LoadSongs().execute("");


        return rootView;

    }

    /*private void checkPermissionnn(){
        Log.d("Music","MSG Check permission start");
        if(Build.VERSION.SDK_INT >= 23) {
            Log.d("Music","MSG Check permission if start");
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("Music","MSG CheckSelfPermission if");
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                Log.d("Music","MSG Check permission if");
                return;
            }
            else {
                loadSongs();
            }
        }else {
            loadSongs();
            Log.d("Music","MSG Check permission else");
        }
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 123:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                    Log.d("Music","MSG onrequest permission if");
                }else {
                    Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    checkPermissionnn();
                    Log.d("Music","MSG onRequest permission else");
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                Log.d("Music","MSG onRequest permission default");

        }

    }*/

   /* public void loadSongs(){

        Log.d("Music","MSG loadSong start");
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor = getContext().getContentResolver().query(uri,null,selection,null,null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    SongInfo s = new SongInfo(name,artist,url);
                    songInfos.add(s);
                }while (cursor.moveToNext());
                Log.d("Music","MSG loadSongs if");
            }
            cursor.close();
            //songAdapter = new SongAdapter(getContext(),songInfos);
            Log.d("Music","MSG loadSongs end");
        }
    } */

    public void onMetaChanged() {
        if (songAdapter != null)
            songAdapter.notifyDataSetChanged();
    }

    private void reloadAdapter(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<SongInfo> songList = SongLoader.getAllSongs(getActivity());
                songAdapter.updateDataSet(songList);
                return null;
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //checkPermissionnn();
        //new LoadSongs().execute("");
        preferencesUtility = PreferencesUtility.getInstance(getActivity());

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
 /*       songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View b, View v, SongInfo obj, int possition) {
                try {

                }catch (Exception e){

                }

                Log.d("muAp","Frag click");
            }
        }); */
        try {
            mCallback = (OnMyFragmentListner) context;
        }catch (Exception e){

        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadSongs().execute("");
    }

    private class LoadSongs extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            if(getActivity() != null)
                songAdapter = new SongAdapter((AppCompatActivity) getActivity(),SongLoader.getAllSongs(getActivity()),false,false);
            songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View b, View v, SongInfo obj, int possition) {
                    Log.d("muAp","song clicked");
                    //long pos[] = songAdapter.getSongIds();
                    mCallback.onActionPerformed(possition);

                    /*MediaPlayer m = new MediaPlayer();
                    long pos[] = songAdapter.getSongIds();
                    Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,pos[possition]);
                    try {
                        m.setDataSource(getContext(),uri);
                        m.prepare();
                        m.start();
                    }catch (Exception ee){
                        Log.d("muAp","errrrrrrrr");
                    }*/





                 /*   try {

     if(playState){
         playState = false;
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
     }else {
         try{
             mediaPlayer.stop();
             mediaPlayer.reset();
         }catch (Exception e){
             playState = true;
             long pos[] = songAdapter.getSongIds();
             mediaPlayer = new MediaPlayer();
             Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,pos[possition]);
             try {
                 mediaPlayer.setDataSource(getContext(),uri);
                 mediaPlayer.prepare();
                 mediaPlayer.start();
             }catch (Exception ee){
                 Log.d("muAp","errrrrrrrr");
             }
         }


     }
     }

                    catch (Exception e){
                        Log.d("muAp","click not work");
                    } */
                }
            });


            return "Executed";
            //return null;
        }

        @Override
        protected void onPostExecute(String result) {
            recyclerView.setAdapter(songAdapter);
            if (getActivity() != null)
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        }
    }
}
