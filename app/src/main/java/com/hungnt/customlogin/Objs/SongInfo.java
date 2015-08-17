package com.hungnt.customlogin.Objs;

/**
 * Created by HungNT on 10/July/2015.
 */
public class SongInfo {
    private String name;
    private String author;
    private String path;
    private int idAlbum;
    private int idArtist;
    private String albums;
    private String cover;

    public SongInfo(String name, String path, String cover, String author, String albums, int idAlbum, int idArtist) {
        this.author = author;
        this.idAlbum = idAlbum;
        this.albums = albums;
        this.idArtist = idArtist;
        this.cover = cover;
        this.name = name;
        this.path = path;
    }

    public String getAlbums() {
        return albums;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
