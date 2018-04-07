package com.vds.final_project_music_player.Models;

/**
 * Created by Vidumini on 2/3/2018.
 */

public class AlbumInfo {
    public final long artistId;
    public final String artistName;
    public final long id;
    public final int songCount;
    public final String title;
    public final int year;

    public AlbumInfo(){
        this.artistId = -1;
        this.artistName = "";
        this.id = -1;
        this.songCount = -1;
        this.title = "";
        this.year = -1;
    }


       public AlbumInfo(long _id, String _title, String _artistName, long _artistId, int _songCount, int _year) {
        this.artistId = _artistId;
        this.artistName = _artistName;
        this.id = _id;
        this.songCount = _songCount;
        this.title = _title;
        this.year = _year;
    }

    /*
        public AlbumInfo(long artistId, String artistName, long id, int songCount, String title, int year) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.id = id;
        this.songCount = songCount;
        this.title = title;
        this.year = year;
    }
     */
}
