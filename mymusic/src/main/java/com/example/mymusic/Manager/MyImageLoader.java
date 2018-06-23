package com.example.mymusic.Manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.mymusic.IContact.IURL;
import com.example.mymusic.Uitl.StreamUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyImageLoader {
    public static LruCache<String,Bitmap> lruCache = null;
    static {
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight()*value.getRowBytes();
            }
        };
    }
    public static void  setBitmapFromCache(Context context, ImageView Album,String ImageURL){
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(ImageURL)){
            return;
        }
        bitmap = getBitmapFromMemory(ImageURL);
        if (bitmap!=null){
            Album.setImageBitmap(bitmap);
            return;
        }
        bitmap=getBitmapfromFile(context,ImageURL);
        if (bitmap!=null){
            Album.setImageBitmap(bitmap);
        }

        LoadBitmapFromHttp(context,Album,ImageURL);

    }

    private static void LoadBitmapFromHttp(Context context, ImageView album, String imageURL) {
        ImageAsyncTask task = new ImageAsyncTask(context,album);
        task.execute(imageURL);
    }
    private static class ImageAsyncTask extends AsyncTask<String,Void,Bitmap>{
        Context context;
        ImageView Album;
        ImageAsyncTask(Context context,ImageView imageView){
            this.context = context;
            Album = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;
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
                    bitmap = compressBitmap(is);
                    if (bitmap!=null){
                        lruCache.put(path,bitmap);
                        SaveBitmapToFile(context,path,bitmap);
                        return bitmap;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Album.setImageBitmap(bitmap);
        }
    }
    private static void SaveBitmapToFile(Context context, String path, Bitmap bitmap) {
        File cacheDir = context.getCacheDir();
        try {
            if (cacheDir.exists()) {
                cacheDir.mkdir();
            }
            String fileName = path.substring(path.lastIndexOf("/")+1);
            File file = new File(cacheDir,fileName);
            OutputStream os= new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
    private  static Bitmap compressBitmap(InputStream is) {
        Bitmap bitmap = null;
        byte [] datas = StreamUtil.CarentByte(is);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(datas,0,datas.length,opts);
        int outwidth = opts.outWidth;
        int outheight = opts.outHeight;

        int tagetWidth = 65;
        int tagetHeight = 65;

        int blw = outwidth/tagetHeight;
        int blh = outheight/tagetHeight;

        int bl = blw>blh?blw:blh;
        if (bl<=0){
            bl=1;
        }
        opts.inSampleSize = bl;
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(datas,0,datas.length,opts);
        return bitmap;
    }


    private static Bitmap getBitmapfromFile(Context context, String imageURL) {
        Bitmap bitmap=null;
        String fileName = imageURL.substring(imageURL.lastIndexOf("/")+1);
        File cacheDir = context.getCacheDir();
        if (cacheDir!=null){
            File[] files = cacheDir.listFiles();
            for(File file:files){
                String name = file.getName();
                if (name.equals(fileName)){
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                }

            }
        }
        return bitmap;
    }

    private static Bitmap getBitmapFromMemory(String imageURL) {
        Bitmap bitmap = null;
        bitmap = lruCache.get(imageURL);
        return bitmap;
    }
}
