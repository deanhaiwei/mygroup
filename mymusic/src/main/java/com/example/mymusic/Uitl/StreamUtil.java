package com.example.mymusic.Uitl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    public static byte[] CarentByte(InputStream is){
        ByteArrayOutputStream os = null;
        byte[] datas=null;
        try {
            os = new ByteArrayOutputStream();
            byte[] buffur = new byte[1024];
            int len = 0;
            while ((len = is.read(buffur))!=-1){
                os.write(buffur,0,len);
            }
            os.flush();
            datas=os.toByteArray();
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }



        return datas;
    }
}
