package com.nocompany.frank.weather.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.nocompany.frank.weather.bean.Weather;
import com.nocompany.frank.weather.bean.WeatherDaily;
import com.nocompany.frank.weather.bean.WeatherNow;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by Frank on 2017/2/9 0009.
 * 缓存
 */

public class Cache {

    private static final String NOW = "weather_now";
    private static final String DAILY = "weather_daily";
    private static final String SUGGESTION = "weather_suggestion";

    /**
     * 保存到缓存
     *
     * @param context Context
     * @param o       Object
     */
    public static void save(Context context, Object o) {

        if (o == null) {
            return;
        }

        String file;
        if (o instanceof WeatherNow) {
            file = NOW;
        } else if (o instanceof WeatherDaily) {
            file = DAILY;
        } else {
            file = SUGGESTION;
        }

        Gson gson = new Gson();
        String json = gson.toJson(o);

        SDCardUtils.saveFileToSDCardPrivateCacheDir(context.getApplicationContext(),
                json.getBytes(Charset.forName("unicode")), file);
    }

    /**
     * 从缓存中获取Weather类信息
     *
     * @param context Context
     * @return String
     */
    public static String get(Context context, Class cls) {

        String file;
        if (cls == WeatherNow.class) {
            file = NOW;
        } else if (cls == WeatherDaily.class) {
            file = DAILY;
        } else {
            file = SUGGESTION;
        }

        byte[] json = SDCardUtils.loadFileFromSDCard(SDCardUtils.getSDCardPrivateCacheDir(context.getApplicationContext())
                + File.separator + file);

        if (json != null && json.length > 0) {
            return new String(json, Charset.forName("unicode"));
        }

        return null;
    }
}
