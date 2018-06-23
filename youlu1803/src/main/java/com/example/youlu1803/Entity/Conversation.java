package com.example.youlu1803.Entity;

public class Conversation {
    private int thread_id;
    private String name;
    private String address;
    private long date;
    private String dateStr;
    private int read;
    private int photoId;
    private String body;

    @Override
    public String toString() {
        return "Conversation{" +
                "thread_id=" + thread_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", read=" + read +
                ", photoId=" + photoId +
                ", body='" + body + '\'' +
                '}';
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Conversation(int thread_id, String name, String address, long date, String dateStr, int read, int photoId, String body) {
        this.thread_id = thread_id;
        this.name = name;
        this.address = address;
        this.date = date;
        this.dateStr = dateStr;
        this.read = read;
        this.photoId = photoId;
        this.body = body;

    }

    public Conversation() {

    }
}
