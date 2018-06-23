package com.example.day_14;

/**
 * Created by dell on 2018/5/8.
 */

public class Songs {
    private String title;
    private String artist;
    private String data;
    public Songs(String title , String artist ,String data){
        this.title=title;
        this.artist = artist;
        this.data = data;
    }

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
