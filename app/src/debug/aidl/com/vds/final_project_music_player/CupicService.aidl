// CupicService.aidl
package com.vds.final_project_music_player;

// Declare any non-default types here with import statements

interface CupicService {
    long getAudioId();
    boolean isPlaying();
    void setShuffleMode(int shufflemode);
    int getQueuePosition();
    long [] getQueue();
    void play();
    void open(in long [] list, int position, long sourceId, int sourceType);
        void next();
            void pause();

}
