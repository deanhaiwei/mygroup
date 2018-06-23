package com.example.mymusic.Wiget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.mymusic.Entity.Music;
import com.example.mymusic.IContact.IURL;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class MusicWidget extends AppWidgetProvider {

    private static  List<Music> musics;
    private static int position = 0;


//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        if (musics!=null&&musics.size()>0){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.music_widget);
            remoteViews.setViewVisibility(R.id.appwidget_play, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.appwidget_pause, View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.appwidget_next, View.VISIBLE);
            setWidget(context,remoteViews);
            appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);
        }
    }

    private void setWidget(Context context, RemoteViews remoteViews) {
        Music music = musics.get(position);
        remoteViews.setTextViewText(R.id.appwidget_name,music.getName());
        remoteViews.setTextViewText(R.id.appwidget_singer,music.getSinger());
        String musicPath=IURL.ROOT+music.getMusicpath();
        Intent intentPlay = new Intent(IURL.PLAYMUSIC_ACTION);
        intentPlay.putExtra("musicpath",musicPath);
        PendingIntent pendingPlay = PendingIntent.getBroadcast(context,1,
                intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_play,pendingPlay);

        Intent intentPause = new Intent(IURL.PUASEMUSIC_ACTION);
        intentPlay.putExtra("musicpath",musicPath);
        PendingIntent pendingPause = PendingIntent.getBroadcast(context,1,
                intentPause,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_pause,pendingPause);

        Intent intentNext = new Intent(IURL.UPDATENEXTWIDGET_ACTION);
        intentPlay.putExtra("musicpath",musicPath);
        PendingIntent pendingNext = PendingIntent.getBroadcast(context,1,
                intentNext,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.appwidget_next,pendingNext);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.music_widget);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context,MusicWidget.class);
        String action = intent.getAction();
        if (IURL.MUSICSLOAD_ACTION.equals(action)){
            musics = (ArrayList<Music>) intent.getSerializableExtra("musics");
            Log.i("TAG:", "音乐加载完成:" + musics.size());
        }else if (IURL.UPDATEPAUSEWIDGET_ACTION.equals(action)){
            views.setViewVisibility(R.id.appwidget_play,View.INVISIBLE);
            views.setViewVisibility(R.id.appwidget_pause,View.VISIBLE);
            String musicpath = intent.getStringExtra("musicpath");
            for(int i=0;i<musics.size();i++) {
                String path = IURL.ROOT + musics.get(i).getMusicpath();
                if (path.equals(musicpath)) {
                    position = i;
                    //更新组件
                    setWidget(context, views);
                    manager.updateAppWidget(componentName, views);
                    break;
                }
            }
        }else if (IURL.UPDATEPLAYWIDGET_ACTION.equals(action)){
            views.setViewVisibility(R.id.appwidget_play,View.VISIBLE);
            views.setViewVisibility(R.id.appwidget_pause,View.INVISIBLE);
            manager.updateAppWidget(componentName, views);
        }else if (IURL.UPDATENEXTWIDGET_ACTION.equals(action)){
            Log.i("TAG:","收到播放下一首的广播");
            if(position<musics.size()-1){
                position+=1;
            }else{
                position=musics.size()-1;
            }
            Intent intent_next = new Intent(IURL.PLAYMUSIC_ACTION);
            String musicPath = IURL.ROOT+musics.get(position).getMusicpath();
            intent_next.putExtra("musicpath",musicPath);
            context.sendBroadcast(intent_next);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

