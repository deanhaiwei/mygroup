package com.example.youlu1803.Entity;

public class Colllog {
    private int _id;
    private String name;
    private String phone;
    private long date;
    private String dateSre;
    private int photoId;
    private int type;

    public Colllog(int _id, String name, String phone, long date, String dateSre, int photoId, int type) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.dateSre = dateSre;
        this.photoId = photoId;
        this.type = type;
    }

    public Colllog() {

    }

    @Override
    public String toString() {
        return "Colllog{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", date=" + date +
                ", dateSre='" + dateSre + '\'' +
                ", photoId=" + photoId +
                ", type=" + type +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateSre() {
        return dateSre;
    }

    public void setDateSre(String dateSre) {
        this.dateSre = dateSre;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
