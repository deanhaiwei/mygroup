package com.example.weather_forecast.Manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather_forecast.Enity.MyWeatherBin;
import com.example.weather_forecast.ICon.IURL;
import com.google.gson.Gson;

import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpWeathrManager {
    public  static  RequestQueue queue = null;
    public static void LoadWeather(Context context,String CityNme,
                                   final WeatherBeaLoadListener weatherBeaLoadListener){
        try {
            CityNme = URLEncoder.encode(CityNme,"utf8");
            String url = IURL.ROOT+"cityname="+CityNme+"$key="+IURL.APPNAME;
            if (queue==null){
                queue= Volley.newRequestQueue(context);
            }
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Gson gson =new Gson();
                    MyWeatherBin myWeatherBin = gson.fromJson(s,MyWeatherBin.class);
                    weatherBeaLoadListener.WeatherBeaLoadEnd(myWeatherBin);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.i("TAG:","volley error");
                }
            });
            queue.add(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
    public interface WeatherBeaLoadListener{
        public void WeatherBeaLoadEnd(MyWeatherBin myWeatherBin);
    }
}
