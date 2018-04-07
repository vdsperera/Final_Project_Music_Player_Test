package com.vds.final_project_music_player.Adapters;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vds.final_project_music_player.MusicPlayer;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Models.SongInfo;
import com.vds.final_project_music_player.Utils.CupicUtils;
import com.vds.final_project_music_player.Utils.Helpers;
import com.vds.final_project_music_player.Widget.MusicVisualizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vidumini on 1/13/2018.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {


    public int currentPlayingPosition;
    private List<SongInfo> arrayList;
    private AppCompatActivity context;
    private long[] songIds;
    private boolean isPlaylist;
    private boolean animate;
    private int lastPosition = -1;
    private String ateKey;
    private long playlistId;
    OnItemClickListener onItemClickListener;

    public SongAdapter(AppCompatActivity context,List<SongInfo> arrayList,boolean isPlaylist, boolean animate) {
        this.arrayList = arrayList;
        this.context = context;
        this.songIds = songIds;
        this.isPlaylist = isPlaylist;
        this.animate = animate;
        this.ateKey = Helpers.getATEKey(context);
    }

    public interface OnItemClickListener{
        void onItemClick(View b, View v, SongInfo obj, int possition);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isPlaylist){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, null);
            SongHolder itemHolder = new SongHolder(v);
            return itemHolder;
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, null);
            SongHolder itemHolder = new SongHolder(v);
            return itemHolder;
        }
    }

    @Override
    public void onBindViewHolder(final SongHolder holder, final int position) {
        final SongInfo localItem = arrayList.get(position);

        //holder.title.setText(localItem.title);
        //holder.artist.setText(localItem.artistName);

        holder.title.setText(localItem.title);
        holder.artist.setText(localItem.artistName);

        ImageLoader.getInstance().displayImage(CupicUtils.getAlbumArtUri(localItem.albumId).toString(), holder.albumart, new DisplayImageOptions.Builder().cacheInMemory(true).showImageForEmptyUri(R.drawable.ic_music_empty).resetViewBeforeLoading(true).build());

        /*if (MusicPlayer.getCurrentAudioId() == localItem.id){
            holder.title.setTextColor(R.id.format_color_text);
            if (MusicPlayer.isPlaying()){
                holder.visualizer.setColor(android.support.design.R.attr.colorAccent);
                holder.visualizer.setVisibility(View.VISIBLE);
            }else {
                holder.visualizer.setVisibility(View.GONE);
            }
        }else {
            holder.visualizer.setVisibility(View.GONE);
            if(isPlaylist){
                holder.title.setTextColor(Color.WHITE);
            }
            else {
                holder.title.setTextColor(android.support.design.R.attr.rippleColor);
            }


        }*/

        holder.btnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(holder.btnv,view,localItem,position);
                }

            }
        });

    }

    public void setPlaylistId(long playlistId){
        this.playlistId = playlistId;
    }

    @Override
    public int getItemCount()
    {
        return (null != arrayList ? arrayList.size() : 0);
    }

    private void setOnPopupMenuListener(SongHolder holder, final int Position){

    }

    public long[] getSongIds(){
        long[] ret = new long[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            ret[i] = arrayList.get(i).id;
        }

        return ret;
    }

    private void setAnimation(View viewToAnimate, int position){
        if(position>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void updateDataSet(List<SongInfo> arrayList){
        this.arrayList = arrayList;
        this.songIds = getSongIds();
    }

    public class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //TextView songName,artistName;
        //Button btnAction;
        //View.OnClickListener on;
        View btnv;

        protected TextView title, artist;
        protected ImageView albumart, popupMenu;
        private MusicVisualizer visualizer;

        public SongHolder(View itemView) {
            super(itemView);
            Log.d("muAp","SongFraggg");
            title = (TextView) itemView.findViewById(R.id.song_title);
            artist = (TextView) itemView.findViewById(R.id.song_artist);
            albumart = (ImageView) itemView.findViewById(R.id.albumart);
            popupMenu = (ImageView) itemView.findViewById(R.id.popup_menu);
            visualizer = (MusicVisualizer) itemView.findViewById(R.id.visualizer);
            btnv = (View) itemView;
            //itemView.setOnClickListener(on);
            //btnAction = (Button) itemView.findViewById(R.id.btnAction);
        }

        @Override
        public void onClick(View view) {
            Log.d("muAp","CLick Playyyy");
        /*    final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("muAp","Play All Start");
                    MusicPlayer.playAll(context, songIds, getAdapterPosition(), -1, CupicUtils.IdType.NA, false);
                    Log.d("muAp","Play All e1");
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("muAp","Play All e2");
                            notifyItemChanged(currentPlayingPosition);
                            Log.d("muAp","Play All e3");
                            notifyItemChanged(getAdapterPosition());
                            Log.d("muAp","Play All e4");
                        }
                    }, 50);

                }
            }, 100); */

        }

    }

    public SongInfo getSongAt(int i) {
        return arrayList.get(i);
    }

    public void addSongTo(int i, SongInfo song) {
        arrayList.add(i, song);
    }

    public void removeSongAt(int i) {
        arrayList.remove(i);
    }
}
