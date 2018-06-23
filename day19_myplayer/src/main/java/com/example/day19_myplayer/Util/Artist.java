package com.example.day19_myplayer.Util;

public class Artist {
    private String name;
    private int count;

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }

    public Artist(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;

    }

    public int getCount() {
        return count;
    }
}
