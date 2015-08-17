package com.hungnt.customlogin.Objs;

/**
 * Created by HungNT on 10/July/2015.
 */
public class SongInfo {
    private String name;
    private String author;
    private int liked;
    private int listened;
    private int downloaded;
    private int cover;

    public SongInfo(String name, String author, int liked, int listened, int downloaded, int cover) {
        this.name = name;
        this.author = author;
        this.liked = liked;
        this.listened = listened;
        this.downloaded = downloaded;
        this.cover = cover;
    }

    public SongInfo(String name, String author) {
        this.name = name;
        this.author = author;
        liked = 0;
        listened = 0;
        downloaded = 0;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getLiked() {
        return liked;
    }

    public int getListened() {
        return listened;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public int getCover() {
        return cover;
    }
}
