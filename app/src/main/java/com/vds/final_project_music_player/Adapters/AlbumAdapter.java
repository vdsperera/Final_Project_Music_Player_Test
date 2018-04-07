package com.vds.final_project_music_player.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.provider.SyncStateContract;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.afollestad.appthemeengine.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.vds.final_project_music_player.Models.AlbumInfo;
import com.vds.final_project_music_player.Models.SongInfo;
import com.vds.final_project_music_player.R;
import com.vds.final_project_music_player.Utils.CupicUtils;
import com.vds.final_project_music_player.Utils.Helpers;
import com.vds.final_project_music_player.Utils.NavigationUtils;
import com.vds.final_project_music_player.Utils.PreferencesUtility;

import java.util.List;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumItemHolder> {
    private List<AlbumInfo> arrayList;
    private Activity context;
    private boolean isGrid;

    public AlbumAdapter(Activity context,List<AlbumInfo> arrayList){
        this.arrayList = arrayList;
        this.context = context;
        this.isGrid = PreferencesUtility.getInstance(context).isAlbumsGrid();
    }

    @Override
    public AlbumItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isGrid){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_grid,null);
            AlbumItemHolder itemHolder = new AlbumItemHolder(v);
            return  itemHolder;
        }else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_album_list,null);
            AlbumItemHolder itemHolder = new AlbumItemHolder(v);
            return itemHolder;
        }

    }

    @Override
    public void onBindViewHolder(final AlbumItemHolder holder, final int position) {
        AlbumInfo localItem = arrayList.get(position);
        holder.title.setText(localItem.title);
        holder.artist.setText(localItem.artistName);

        ImageLoader.getInstance().displayImage(CupicUtils.getAlbumArtUri(localItem.id).toString(),holder.albumArt,
                new DisplayImageOptions.Builder().cacheInMemory(true)
                    .showImageOnFail(R.drawable.ic_music_empty)
                    .resetViewBeforeLoading(true)
                    .displayer(new FadeInBitmapDisplayer(400))
                    .build(),new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        //super.onLoadingComplete(imageUri, view, loadedImage);
                        if(isGrid){
                            new Palette.Builder(loadedImage).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch swatch = palette.getVibrantSwatch();
                                    if(swatch != null){
                                        int color = swatch.getRgb();
                                        holder.footer.setBackgroundColor(color);
                                        int textColor = CupicUtils.getBlackWhiteColor(swatch.getTitleTextColor());
                                        holder.title.setTextColor(textColor);
                                        holder.artist.setTextColor(textColor);

                                    }else {
                                        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                                        if(mutedSwatch != null){
                                            int color = mutedSwatch.getRgb();
                                            holder.footer.setBackgroundColor(color);
                                            int textColor = CupicUtils.getBlackWhiteColor(mutedSwatch.getTitleTextColor());
                                            holder.title.setTextColor(color);
                                            holder.artist.setTextColor(color);
                                        }
                                    }
                                }
                            });
                            if(CupicUtils.isLollipop()){
                                holder.albumArt.setTransitionName("transition_album_art" + position);
                            }
                        }
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        //super.onLoadingFailed(imageUri, view, failReason);
                        if (isGrid){
                            //holder.footer.setBackgroundColor(0);
                            if(context != null){
                                int textColorPrimary = Config.textColorPrimary(context, Helpers.getATEKey(context));
                                holder.title.setTextColor(textColorPrimary);
                                holder.artist.setTextColor(textColorPrimary);
                            }
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {

        return (null != arrayList ? arrayList.size() : 0);
    }

    public void updateDataSet(List<AlbumInfo> arraylist) {
        this.arrayList = arraylist;
    }

    public class AlbumItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView title,artist;
        protected ImageView albumArt;
        protected View footer;

        public AlbumItemHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.album_title);
            this.artist = itemView.findViewById(R.id.album_artist);
            this.albumArt = itemView.findViewById(R.id.album_art);
            this.footer = itemView.findViewById(R.id.footer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("muAp","clickkk");
            NavigationUtils.navigateToAlbum(context, arrayList.get(getAdapterPosition()).id,
                    new Pair<View, String>(albumArt, "transition_album_art" + getAdapterPosition()));
        }
    }
}
