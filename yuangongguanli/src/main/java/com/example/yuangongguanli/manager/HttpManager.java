package com.example.yuangongguanli.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.util.Log;

import com.example.yuangongguanli.Entity.Employee;
import com.example.yuangongguanli.Entity.Path;
import com.example.yuangongguanli.Entity.User;
import com.example.yuangongguanli.Util.ParamsUtil;
import com.example.yuangongguanli.Util.StreamUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpManager {
    static  String SESSIONID = null;
    public static boolean HttpRegistPost(User user){
        boolean flag = false;
        try {
            URL url = new URL(Path.REGIST);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            Map<String,String> params = new HashMap<>();
            params.put("loginname",user.getLoginname());
            params.put("password",user.getPassword());
            params.put("realname",user.getRealname());
            params.put("emial",user.getEmial());
            byte [] datas = ParamsUtil.CarentParams(params).getBytes();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(datas.length));
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(datas);
            os.flush();
            os.close();
            int strtusCode = connection.getResponseCode();
            if (strtusCode==200){
                InputStream is = connection.getInputStream();
                String jsonStr = StreamUtil.CarentStr(is);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if (result.equals("ok")){
                    flag=true;
                    JSONObject jsonuser = jsonObject.getJSONObject("user");
                    String username = jsonuser.getString("username");
                    String passwrod = jsonuser.getString("password");
                    Log.i("TAG:username", username);
                    Log.i("TAG:passwrod", passwrod);
                }else {
                    String msg = jsonObject.getString("msg");
                    Log.i("TAG:msg", msg);
                }
            }else {
                Log.i("TAG:statusCode",""+strtusCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;

    }

    public static Bitmap HttpCodeGet(){
        Bitmap bitmap =null;
        try {
            URL url  = new URL(Path.CODE);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            int strtuscode = connection.getResponseCode();
            if (strtuscode==200){
                SESSIONID = connection.getHeaderField("Set-Cookie").split(";")[0];
                Log.i("TAG:SESSIONID", SESSIONID);
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
            }else {
                Log.i("TAG:statusCode",""+strtuscode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static boolean HttpLoginPost(String loginname,String password,String code){
        boolean flag = false;
        try {
            URL url = new URL(Path.LOGIN);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Cookie",SESSIONID);

            Map<String,String> params = new HashMap<>();
            params.put("loginname",loginname);
            params.put("password",password);
            params.put("code",code);
            byte [] datas = ParamsUtil.CarentParams(params).getBytes();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty( "Content-Length",String.valueOf(datas.length));
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(datas);
            os.flush();
            os.close();
            int strtusCode = connection.getResponseCode();
            if (strtusCode==200){
                InputStream is = connection.getInputStream();
                String jsonStr = StreamUtil.CarentStr(is);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if (result.equals("ok")){
                    flag = true;
                }else {
                    String msg = jsonObject.getString("msg");
                    Log.i("TAG:msg", msg);
                }
            }else {
                Log.i("TAG:statusCode",""+strtusCode);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public static boolean HttpAddPost(Employee employee){
        boolean flag = false;
        try {
            URL url = new URL(Path.ADD);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            Map<String,String> params = new HashMap<>();
            params.put("name",employee.getName());
            params.put("salary",String.valueOf(employee.getSalary()));
            params.put("age",String.valueOf(employee.getAge()));
            params.put("gender",employee.getGender());
            byte [] datas = ParamsUtil.CarentParams(params).getBytes();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty( "Content-Length",String.valueOf(datas.length));
            connection.connect();
            OutputStream os = connection.getOutputStream();
            os.write(datas);
            os.flush();
            os.close();
            int strtusCode = connection.getResponseCode();
            if (strtusCode==200){
                InputStream is = connection.getInputStream();
                String jsonStr = StreamUtil.CarentStr(is);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if (result.equals("ok")){
                    flag = true;
                }else {
                    String msg = jsonObject.getString("msg");
                    Log.i("TAG:msg", msg);
                }
            }else {
                Log.i("TAG:statusCode",""+strtusCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flag;
    }
    public static List<Employee> queryEmployeesHttpGet(){
        List<Employee> employees = new ArrayList<>();
        try {
            URL url = new URL(Path.EMPLOYEELIST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.connect();
            int statusCode = connection.getResponseCode();
            if (statusCode==200){
                InputStream is = connection.getInputStream();
                String jsonStr = StreamUtil.CarentStr(is);
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result = jsonObject.getString("result");
                if (result.equals("ok")){
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (int i=0;i<array.length();i++){
                        Employee employee = new Employee();
                        JSONObject jsonEmp=array.getJSONObject(i);
                        int id = jsonEmp.getInt("id");
                        String name = jsonEmp.getString("name");
                        double salary = jsonEmp.getDouble("salary");
                        int age = jsonEmp.getInt("age");
                        String gender= jsonEmp.getString("gender");
                        employee.setId(id);
                        employee.setAge(age);
                        employee.setName(name);
                        employee.setSalary(salary);
                        employee.setGender(gender);

                        employees.add(employee);
                    }
                }else {
                    String msg = jsonObject.getString("msg");
                    Log.i("TAG:msg", msg);
                }
            }else {
                Log.i("TAG:statusCode",""+statusCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
