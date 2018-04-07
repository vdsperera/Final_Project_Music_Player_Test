package com.vds.final_project_music_player.Models;

/**
 * Created by Vidumini on 1/13/2018.
 */

public class SongInfo {
    //public String songName,artistName,songURL;
    public String songURL;

    public long albumId;
    public String albumName;
    public long artistId;
    public String artistName;
    public int duration;
    public long id;
    public String title;
    public int trackNumber;
     

    public SongInfo() {
        this.id = -1;
        this.albumId = -1;
        this.artistId = -1;
        this.title = "";
        this.artistName = "";
        this.albumName = "";
        this.duration = -1;
        this.trackNumber = -1;

    }

    /*public SongInfo(String songName, String artistName, String songURL) {
        this.songName = songName;
        this.artistName = artistName;
        this.songURL = songURL;
    }*/

    public SongInfo(long _id, long _albumId, long _artistId, String _title, String _artistName, String _albumName, int _duration, int _trackNumber) {
        this.id = _id;
        this.albumId = _albumId;
        this.artistId = _artistId;
        this.title = _title;
        this.artistName = _artistName;
        this.albumName = _albumName;
        this.duration = _duration;
        this.trackNumber = _trackNumber;
    }



    /*public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongURL() {
        return songURL;
    }*/

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
}
