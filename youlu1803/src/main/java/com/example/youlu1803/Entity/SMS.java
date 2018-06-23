package com.example.youlu1803.Entity;

public class SMS {
    private int _id;
    private String address;
    private long date;
    private String dateStr;
    private int type;
    private int photoId;
    private String body;

    @Override
    public String toString() {
        return "SMS{" +
                "_id=" + _id +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", type=" + type +
                ", photoId=" + photoId +
                ", body='" + body + '\'' +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public SMS(int _id, String address, long date, String dateStr, int type, int photoId, String body) {

        this._id = _id;
        this.address = address;
        this.date = date;
        this.dateStr = dateStr;
        this.type = type;
        this.photoId = photoId;
        this.body = body;
    }

    public SMS() {

    }
}
