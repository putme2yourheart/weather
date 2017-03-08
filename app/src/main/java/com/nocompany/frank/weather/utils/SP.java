package com.nocompany.frank.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nocompany.frank.weather.bean.Area;

/**
 * Created by Frank on 2017/2/18.
 * SharedPreference的相关操作
 */

public class SP {

    private static final String AREA = "sp_area";

    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String DISTRICT = "district";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    /**
     * 提取位置信息
     *
     * @param context Context
     * @return 位置信息
     */
    public static Area get(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AREA, Context.MODE_PRIVATE);
        return new Area(
                sharedPreferences.getString(PROVINCE, "北京"),
                sharedPreferences.getString(CITY, "北京"),
                sharedPreferences.getString(DISTRICT, "北京"));
    }

    /**
     * 存储位置信息
     *
     * @param context Context
     * @param area    位置信息
     */
    public static void save(Context context, Area area) {

        if (area == null) {
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(AREA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PROVINCE, area.getProvince());
        editor.putString(CITY, area.getCity());
        editor.putString(DISTRICT, area.getDistrict());

        editor.apply();
    }

}
