package com.vds.final_project_music_player.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * Created by Vidumini on 2/20/2018.
 */

public class MusicVisualizer extends View {
    Random random = new Random();
    Paint paint = new Paint();
    private Runnable animateView = new Runnable() {
        @Override
        public void run() {
            postDelayed(this,120);
            invalidate();
        }
    };

    public MusicVisualizer(Context context) {
        super(context);
        new MusicVisualizer(context, null);
    }

    public MusicVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        removeCallbacks(animateView);
        post(animateView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getDemensionInPixel(0), getHeight() - (40 + random.nextInt((int)(getHeight() / 1.5f) -25)), getDemensionInPixel(7),getHeight() - 15, paint);
        canvas.drawRect(getDemensionInPixel(10), getHeight() - (40 + random.nextInt((int)(getHeight() / 1.5f) -25)), getDemensionInPixel(17),getHeight() - 15, paint);
        canvas.drawRect(getDemensionInPixel(20), getHeight() - (40 + random.nextInt((int)(getHeight() / 1.5f) -25)), getDemensionInPixel(27),getHeight() - 15, paint);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(visibility == VISIBLE){
            removeCallbacks(animateView);
            post(animateView);
        }
        else if(visibility == GONE){
            removeCallbacks(animateView);
        }
    }

    public void setColor(int color){
        paint.setColor(color);
        invalidate();
    }

    private int getDemensionInPixel(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,getResources().getDisplayMetrics());
    }
}
