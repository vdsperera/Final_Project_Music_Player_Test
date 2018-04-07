package com.vds.final_project_music_player.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vds.final_project_music_player.Models.SongInfo;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Utils.CupicUtils;

import java.util.List;

/**
 * Created by Vidumini on 2/12/2018.
 */

public class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsAdapter.AlbumSongItemHolder>{
    private List<SongInfo> arrayList;
    private Activity activity;
    private long albumId;
    private long[] songIds;

    public AlbumSongsAdapter( Activity activity,List<SongInfo> arrayList, long albumId) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.albumId = albumId;
        this.songIds = getSongIds();
    }

    @Override
    public AlbumSongItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_song,null);
        AlbumSongItemHolder holder = new AlbumSongItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlbumSongItemHolder holder, int position) {
        SongInfo localItem = arrayList.get(position);
        holder.title.setText(localItem.title);
        holder.duration.setText(CupicUtils.makeShortTimeString(activity,(localItem.duration)/1000));
        int trackNumber = localItem.trackNumber;
        if(trackNumber == 0){
            holder.trackNumber.setText("-");
        }
        else holder.trackNumber.setText(String.valueOf(trackNumber));

        setPopupMenuListner(holder, position);


    }

    private void setPopupMenuListner(AlbumSongItemHolder holder, final int position){

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public long[] getSongIds(){
        long[] ret = new long[getItemCount()];
        for(int i = 0; i < getItemCount(); i++){
            ret[i] = arrayList.get(i).id;
        }
        return ret;
    }

    public void updateDataSet(List<SongInfo> arrayList){
        this.arrayList = arrayList;
        this.songIds = getSongIds();
    }

    public class AlbumSongItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView title, duration, trackNumber;
        protected ImageView menu;

        public AlbumSongItemHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.song_title);
            this.duration = itemView.findViewById(R.id.song_duration);
            this.trackNumber = itemView.findViewById(R.id.trackNumber);
            this.menu = itemView.findViewById(R.id.popup_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
