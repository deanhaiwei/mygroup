package com.example.yuangongguanli.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StreamUtil {



    public static String CarentStr(InputStream is){
        BufferedReader reader = null;
        String jsonStr=null;
        try {
            reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
            StringBuilder builder = new StringBuilder();
            String line="";
            while ((line = reader.readLine())!=null){
                builder.append(line);
            }
            jsonStr = builder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return jsonStr;
    }
}
