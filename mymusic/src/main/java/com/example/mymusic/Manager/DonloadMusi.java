package com.example.mymusic.Manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.mymusic.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DonloadMusi {
    public static void sendNotifaction(Context context, String ticker, String title, String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.favo);
        builder.setContentInfo(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        Notification notification = builder.build();
        manager.notify(100,notification);

    }
    public static void downLoadFile(final Context context,String musicPath,String name){
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath();
        final File file = new File(downloadPath,name);
        new AsyncTask<String,Void,File>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                sendNotifaction(context,"准备下载","准备下载","准备中");
            }

            @Override
            protected File doInBackground(String... strings) {
                String path = strings[0];
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setDoInput(true);
                    connection.connect();
                    int statusCode = connection.getResponseCode();
                    if (statusCode==200){
                        InputStream is = connection.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024*8];
                        OutputStream os = new FileOutputStream(file);
                        BufferedOutputStream bos = new BufferedOutputStream(os);
                        int downLoadCount = 0;
                        int contentLength = connection.getContentLength();
                        int len = 0;
                        while ((len = bis.read(buffer))!=-1){
                            downLoadCount+=len;
                            sendNotifaction(
                                    context,
                                    "下载中",
                                    "下载百分比",
                                    downLoadCount*100/contentLength+"%");
                            bos.write(buffer,0,len);
                        }
                        bos.flush();
                        bos.close();
                        bis.close();
                        return file;

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                sendNotifaction(context,"下载完成",file.getName(),"下载完成");
            }
        }.execute(musicPath);

    }
}
