package com.example.mymusic.IContact;

public interface IURL {
    String ROOT = "http://192.168.0.103:8080/MusicServer/";
    String MUSIC = ROOT+"loadMusics.jsp";
    String PLAYMUSIC_ACTION="com.example.mymusic.PLAY";
    String PUASEMUSIC_ACTION="com.example.mymusic.PAUSE";
    String PLAYNEXT_ACTION="com.example.mymusic.NEXT";
    String SEEKUPDATE_ACTION="com.example.mymusic.SEEKUPDATE";
    String UPDATEPROGRESS_ACTION="com.example.mymusic.UPDATEPROGRESS";
    String UPDATEPAUSEWIDGET_ACTION = "com.example.mymusic.UPDATEPAUSEWIDGET";
    String UPDATEPLAYWIDGET_ACTION = "com.example.mymusic.UPDATEPLAYWIDGET";
    String UPDATENEXTWIDGET_ACTION = "com.example.mymusic.UPDATENEXTWIDGET";
    String MUSICSLOAD_ACTION = "com.example.mymusic.MUSICSLOAD_ACTION";
}
