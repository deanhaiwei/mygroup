package com.example.mynetlogin.manage;

import android.util.Log;

import com.example.mynetlogin.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpManage {
    public static boolean LoginPost(String username,String password){
        boolean flag = false;
        String path = "http://192.168.0.103:8080/MyWeb/LoginServlet";
        BufferedReader reader=null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            Map<String,String> parames = new HashMap<>();
            parames.put("username",username);
            parames.put("password",password);
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String,String> entry:parames.entrySet()){
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }
           byte [] datas =  builder.deleteCharAt(builder.length()-1).toString().getBytes();
            connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(datas.length));
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(datas);
            os.flush();
            int strtusCode = connection.getResponseCode();
            if (strtusCode==200){
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine())!=null){
                    sb.append(line);
                }
                JSONObject jsonObject =  new JSONObject(sb.toString());
                String result = jsonObject.getString("result");
                if (result.equals("ok")){
                    flag=true;
                }
            }else {
                Log.i("TAG:", "LoginPost: "+"响应失败");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    public static User RegistPost(User user){
        User resultUser = null;
        String path = "http://192.168.0.103:8080/MyWeb/RegistServlet";
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            Map<String,String> parames = new HashMap<>();
            parames.put("username",user.getUsername());
            parames.put("password",user.getPassword());
            parames.put("emial",user.getEmial());
            parames.put("phone",user.getPhone());

            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String,String> entry:parames.entrySet()){
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }
            byte[] datas = builder.deleteCharAt(builder.length()-1).toString().getBytes();
            connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(datas.length));
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(datas);
            os.flush();

            int strtusCode = connection.getResponseCode();
            if (strtusCode==200){
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine())!=null){
                    sb.append(line);
                }
                JSONObject jsonObject = new JSONObject(sb.toString());
                String result = jsonObject.getString("result");
                if (reader.equals("ok")){

                    JSONObject jsonuser = jsonObject.getJSONObject("user");
                    String username = jsonuser.getString("username");
                    String passwrod = jsonuser.getString("password");
                    String emial = jsonuser.getString("emial");
                    String phone = jsonuser.getString("phone");
                    resultUser = new User(username,passwrod,emial,phone);
                }else {
                    String msg=jsonObject.getString("msg");
                    Log.i("TAG:",msg);
                }
            }else {
                Log.i("TAG:code",strtusCode+"");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultUser;
    }
}
