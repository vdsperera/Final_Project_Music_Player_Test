package com.vds.final_project_music_player.Adapters;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vds.final_project_music_player.Models.ArtistInfo;
import com.vds.final_project_music_player.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vidumini on 1/21/2018.
 */

public class ArtistAdpater extends RecyclerView.Adapter<ArtistAdpater.ArtistHolder> {

    private ArrayList<ArtistInfo> artistInfos;
    private Context context;
    private boolean isGrid=true;

    public ArtistAdpater(Context context,ArrayList<ArtistInfo> artistInfos) {
        this.context = context;
        this.artistInfos = artistInfos;
        //this.isGrid =
    }

    public static int getOpaqueColor(@ColorInt int paramInt){
        return 0xFF000000 | paramInt;
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isGrid){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_grid,null);
            ArtistHolder ml = new ArtistHolder(v);
            return  ml;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        final ArtistInfo localItem = artistInfos.get(position);

        holder.name.setText(localItem.name);
        holder.albums.setText(null);
        holder.artistImage.setImageResource(R.drawable.ic_launcher_background);
        //holder.footer.findViewById()
        //String albumNumber
    }

    @Override
    public int getItemCount() {
        return (null != artistInfos ? artistInfos.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return artistInfos.get(position).id;
    }

    public void updateDataSet(ArrayList<ArtistInfo> arrayList) {
        this.artistInfos = arrayList;
    }

    public class ArtistHolder extends RecyclerView.ViewHolder{
        protected TextView name,albums;
        protected ImageView artistImage;
        protected  View footer;


        public ArtistHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.artist_name);
            this.artistImage = itemView.findViewById(R.id.artist_image);
            this.albums = itemView.findViewById(R.id.album_song_count);
            this.footer = itemView.findViewById(R.id.footer);
        }
    }
}
