package com.vds.final_project_music_player.Models;

/**
 * Created by Vidumini on 1/21/2018.
 */

public class ArtistInfo {
    public final int albumCount;
    public final long id;
    public final String name;
    public final int songCount;

    public ArtistInfo() {
        albumCount = -1;
        id = -1;
        name = "";
        songCount= -1;
    }

    public ArtistInfo(long id, String name, int songCount) {
        this.id = id;
        this.name = name;
        this.songCount = songCount;
        albumCount = 0;
    }
}
