package com.vds.final_project_music_player.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.vds.final_project_music_player.DataLoaders.AlbumLoader;
import com.vds.final_project_music_player.Models.AlbumInfo;
import com.vds.final_project_music_player.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Vidumini on 2/14/2018.
 */

public class ImageUtils {
    private static final DisplayImageOptions lastfmDisplayImageOptions =
            new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .showImageOnFail(R.drawable.ic_music_empty)
            .build();
    private static final DisplayImageOptions diskDisplayImageOptions =
            new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .build();
    public static void loadAlbumartIntoView(final long albumId, final ImageView view){
        loadAlbumartIntoView(albumId, view, new SimpleImageLoadingListener());
    }

    public static void loadAlbumartIntoView(final long albumId, final ImageView view, final ImageLoadingListener listener){
        if (PreferencesUtility.getInstance(view.getContext()).alwaysLoadAlbumImagesFromLastFm()){

        }
        else {
            loadAlbumartFromDiskWithLastfmFallback(albumId, view, listener);
        }
    }

     private static void loadAlbumartFromDiskWithLastfmFallback(long albumId, ImageView view, final ImageLoadingListener listener){
         ImageLoader.getInstance()
                 .displayImage(CupicUtils.getAlbumArtUri(albumId).toString(),
                         view,diskDisplayImageOptions,
                         new SimpleImageLoadingListener(){
                             @Override
                             public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                 super.onLoadingFailed(imageUri, view, failReason);
                                 //
                                 //
                                 //
                             }
                         });

     }

     private static void loadAlbumartFromLastfm(long albumId, final ImageView albumart){
         AlbumInfo  album = AlbumLoader.getAlbum(albumart.getContext(),albumId);


     }

     public static Drawable createBluredImageFromBitmap(Bitmap bitmap, Context context, int inSampleSize){
         android.support.v8.renderscript.RenderScript rs = android.support.v8.renderscript.RenderScript.create(context);
         final BitmapFactory.Options options = new BitmapFactory.Options();
         options.inSampleSize = inSampleSize;

         ByteArrayOutputStream stream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
         byte[] imageInBytes = stream.toByteArray();
         ByteArrayInputStream bis = new ByteArrayInputStream(imageInBytes);
         Bitmap blurTemplate = BitmapFactory.decodeStream(bis, null, options);

         final android.support.v8.renderscript.Allocation input = android.support.v8.renderscript.Allocation.createFromBitmap(rs,blurTemplate);
         Allocation output = Allocation.createTyped(rs, input.getType());
         ScriptIntrinsicBlur script =   ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
         script.setRadius(8f);
         script.setInput(input);
         script.forEach(output);
         output.copyTo(blurTemplate);

         return new BitmapDrawable(context.getResources(), blurTemplate);
     }
}
