package com.vds.final_project_music_player.Subfragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;

import com.vds.final_project_music_player.MusicService;
import com.vds.final_project_music_player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickControllsFragment extends Fragment implements View.OnClickListener,MediaController.MediaPlayerControl {

    private Button btnPrev;
    private Button btnPause;
    private Button btnNext;
    private BottomCardInterface controlls;
    private MusicService musicService;

    private boolean paused=false, playbackPaused=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controlls = (BottomCardInterface)context;
    }

    public QuickControllsFragment() {
        // Required empty public constructor
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int i) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    public interface BottomCardInterface{
        public void onClickBottomCard();
        public void playNextI();
        public void playPrevI();
        public void playPauseI();
        public int getDurationI();
        public int getCurrentPositionI();
        public void seekToI(int i);
        public boolean isPlaying();
        public int getBufferPercentageI();
        public boolean canPauseI();
        public boolean canSeekBackwardI();
        public boolean canSeekForwardI();
        public int getAudioSessionId();
        public int getAudioSessionIdI();
        public void setController();
        public MusicService getMusicService();
        public void goI();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.bottom_nowplaying_card,container,false);
        btnNext = rootView.findViewById(R.id.bottomNext);
        btnPause = rootView.findViewById(R.id.bottomPlay);
        btnPrev = rootView.findViewById(R.id.bottomPre);

        btnPrev.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (paused){
            //setController();
            musicService.go();
            paused = false;

        }
    }

    @Override
    public void onClick(View view) {
        if (view==btnNext){
            controlls.playNextI();
        }else if (view==btnPause){
            if (controlls.isPlaying()){
                controlls.playPauseI();
            }else
                controlls.goI();

        }
        else if (view==btnPrev){
            controlls.playPrevI();
        }

    }
}
