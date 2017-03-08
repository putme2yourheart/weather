package com.nocompany.frank.weather.utils;

import com.nocompany.frank.weather.bean.Weather;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Frank on 2017/1/25 0025.
 * 获取天气信息工具类
 */

public class WeatherUtils {

    /**
     * 根据不同网址获取不同的json数据
     *
     * @param url URL
     * @return String形式的json数据
     */
    private static String get(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                //throw new IOException("Unexpected code " + response);
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取当前天气状况
     *
     * @param city 省+城市
     * @return String形式的json数据
     */
    public static String getWeather(String city) {
        return get("https://api.thinkpage.cn/v3/weather/now.json?key=p27puzazagbebr3x&location=" +
                city + "&language=zh-Hans&unit=c");
    }

    /**
     * 获取未来三天的天气状况
     *
     * @param city 省+城市
     * @return String形式的json数据
     */
    public static String getThreeDayWeather(String city) {
        return get("https://api.thinkpage.cn/v3/weather/daily.json?key=p27puzazagbebr3x&location=" +
                city + "&language=zh-Hans&unit=c");
    }

    /**
     * 获取建议
     *
     * @param city 省+城市
     * @return String形式的json数据
     */
    public static String getSuggestion(String city) {
        return get("https://api.thinkpage.cn/v3/life/suggestion.json?key=p27puzazagbebr3x&location=" +
                city + "&language=zh-Hans");
    }

    /**
     * 将NewWeather中没填写的字段补充完整
     *
     * @param oldWeather 从缓存读取的天气信息
     * @param newWeather 从网络中获取的天气信息
     * @return 已调整好的天气信息
     */
    public static Weather adjust(Weather oldWeather, Weather newWeather) {
        if (newWeather.getWeatherNow() == null) {
            newWeather.setWeatherNow(oldWeather.getWeatherNow());
        }

        if (newWeather.getWeatherDaily() == null) {
            newWeather.setWeatherDaily(oldWeather.getWeatherDaily());
        }

        if (newWeather.getWeatherSuggestion() == null) {
            newWeather.setWeatherSuggestion(oldWeather.getWeatherSuggestion());
        }

        return newWeather;
    }

}
