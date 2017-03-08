package com.nocompany.frank.weather.bean;

/**
 * Created by Frank on 2017/2/8 0008.
 * 包含的天气信息，包含now, three day weather, suggestion
 */

public class Weather {
    private WeatherNow mWeatherNow;
    private WeatherDaily mWeatherDaily;
    private WeatherSuggestion mWeatherSuggestion;

    public WeatherNow getWeatherNow() {
        return mWeatherNow;
    }

    public void setWeatherNow(WeatherNow weatherNow) {
        mWeatherNow = weatherNow;
    }

    public WeatherDaily getWeatherDaily() {
        return mWeatherDaily;
    }

    public void setWeatherDaily(WeatherDaily weatherDaily) {
        mWeatherDaily = weatherDaily;
    }

    public WeatherSuggestion getWeatherSuggestion() {
        return mWeatherSuggestion;
    }

    public void setWeatherSuggestion(WeatherSuggestion weatherSuggestion) {
        mWeatherSuggestion = weatherSuggestion;
    }

}
