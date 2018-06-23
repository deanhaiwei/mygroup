package com.example.day19_myplayer.Util;

/**
 * Created by dell on 2018/5/8.
 */

public class Song {
    private long id;
    private String title;
    private String artist;
    private String data;
    public Song(long id ,String title , String artist , String data){
        this.id=id;
        this.title=title;
        this.artist = artist;
        this.data = data;
    }
    public long getId(){return id;}
    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }
}
