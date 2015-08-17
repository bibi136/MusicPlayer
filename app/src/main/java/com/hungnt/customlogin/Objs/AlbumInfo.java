package com.hungnt.customlogin.Objs;

/**
 * Created by HungNT on 17/August/2015.
 */
public class AlbumInfo {
    private String albumName;
    private String cover;
    private String artist;
    private int id;

    public AlbumInfo(String albumName, String cover, String artist, int id) {
        this.albumName = albumName;
        this.cover = cover;
        this.id = id;
        this.artist = artist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getCover() {
        return cover;
    }

    public int getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }
}
