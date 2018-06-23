package com.example.mymusic.Manager;

import android.os.AsyncTask;

import com.example.mymusic.Entity.Music;
import com.example.mymusic.IContact.IURL;
import com.example.mymusic.Uitl.StreamUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpManager {
    private static List<Music> getHttpMusic(){
        List<Music> musics =new ArrayList<>();
        try {
            URL url =new URL(IURL.MUSIC);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode==200){
                InputStream is = connection.getInputStream();
                String jsonStr = StreamUtil.CarentStr(is);
                JSONObject jsonObject =  new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if (result.equals("ok")){
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i=0;i<array.length();i++){
                        JSONObject jsonMusic = array.getJSONObject(i);
                         String album=jsonMusic.getString("album");
                         String albumpic = jsonMusic.getString("albumpic");
                         String author = jsonMusic.getString("author");
                         String composer = jsonMusic.getString("composer");
                         String downcount = jsonMusic.getString("downcount");
                         String durationtime = jsonMusic.getString("durationtime");
                         String favcount = jsonMusic.getString("favcount");
                         int id = jsonMusic.getInt("id");
                         String musicpath = jsonMusic.getString("musicpath");
                         String name = jsonMusic.getString("name");
                         String singer = jsonMusic.getString("singer");
                         Music music = new Music(album,albumpic,author,composer,downcount,durationtime,
                                 favcount,id,musicpath,name,singer);
                         musics.add(music);
                    }

                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return musics;
    }
    public static LoadMusicListener listener;
    public static void AsyncLoadMusic(LoadMusicListener musiclistener){
        listener = musiclistener;
        MyAsyncTask Task = new MyAsyncTask();
        Task.execute();

    }
    public static class MyAsyncTask extends AsyncTask<Void,Void,List<Music>>{

        @Override
        protected List<Music> doInBackground(Void... voids) {
            List<Music> musics = getHttpMusic();
            return musics;
        }

        @Override
        protected void onPostExecute(List<Music> music) {
            listener.onLoadMusicEnd(music);
        }
    }
    public interface LoadMusicListener{
        public void onLoadMusicEnd(List<Music> music);
    }
}
