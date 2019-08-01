package org.shop.api.utils;

import com.google.gson.Gson;

public class GsonUtil {

    private final static Gson gson = new Gson();

    public static <T>T toJson(String json, Class<T> obj){
        return gson.fromJson(json,obj);
    }

}
