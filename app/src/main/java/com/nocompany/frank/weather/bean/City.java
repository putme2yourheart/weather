package com.nocompany.frank.weather.bean;

import android.content.Context;

import com.google.gson.Gson;
import com.nocompany.frank.weather.utils.SDCardUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2017/3/7.
 * 城市类，用于记录用户添加的天气的城市位置，json格式保存到缓存
 */

public class City {

    private static final String FILENAME = "city.txt";

    // 保存城市
    public class C {
        private String mCity;

        public C(String city) {
            mCity = city;
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String city) {
            mCity = city;
        }
    }

    private List<C> mCList = new ArrayList<>();

    public List<C> getCList() {
        return mCList;
    }

    public void setCList(List<C> CList) {
        mCList = CList;
    }

    public static void save(Context context, City city) {
        Gson gson = new Gson();

        String json = gson.toJson(city);

        SDCardUtils.saveFileToSDCardPrivateCacheDir(context.getApplicationContext(),
                json.getBytes(Charset.forName("unicode")), FILENAME);
    }

    public static City get(Context context) {
        Gson gson = new Gson();

        byte[] json = SDCardUtils.loadFileFromSDCard(SDCardUtils.getSDCardPrivateCacheDir(context.getApplicationContext())
                + File.separator + FILENAME);

        if (json != null && json.length > 0) {
            return gson.fromJson(new String(json, Charset.forName("unicode")), City.class);
        }

        return null;
    }
}
