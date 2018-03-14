package com.chhd.android.demo;


import com.chhd.android.common.http.RetrofitProvider;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;

public class HttpUtils {

    private HttpUtils() {
    }

    public static Retrofit retrofit(String baseUrl) {
        Map<String, String> map = new HashMap<>();
        map.put("Auth", "eccf9/kVS9fttpvaKuo7iMF3hYoOKM1vBuVET7N/ZgVGq3n8cXyV8PNxNqJVod2rxtgVwSwmIH3xV+ZPt0eGhJ0foiW+dobyoWPcvdfyw6ZnMsBXLSmVrmU8Oja4bAZzqzLrpuf0v9go");
        map.put("App-id", "2");
        map.put("Version", "1.0.0");
        map.put("Client-id", "6cdb35a734d05e3cd4c06a41a1a677d4");
        return RetrofitProvider.newInstance(baseUrl, map);
    }
}
