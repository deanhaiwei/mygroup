package com.example.mymusic.Uitl;

import java.util.Map;

public class ParamsUtil {
    public static String CarentParams(Map<String,String> params){
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getKey());
            builder.append("&");
        }
        String str = builder.deleteCharAt(builder.length()-1).toString();
        return str;
    }
}
