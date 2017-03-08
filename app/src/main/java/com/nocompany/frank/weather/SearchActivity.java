package com.nocompany.frank.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.nocompany.frank.weather.adapter.CityAdapter;
import com.nocompany.frank.weather.bean.City;
import com.nocompany.frank.weather.utils.KeyboardUtils;
import com.nocompany.frank.weather.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2017/3/6.
 * 搜索城市activity
 */

public class SearchActivity extends AppCompatActivity {

    private static final String TAG =  "SearchActivity";

    private List<String> mList;
    private CityAdapter mCityAdapter;

    private EditText et_search;
    private ListView mListView;

    private City mCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        mListView = (ListView) findViewById(R.id.listView);
        et_search = (EditText) findViewById(R.id.et_search);

        mList = new ArrayList<>();

        // 读取城市
        new Thread() {
            @Override
            public void run() {
                mCity = City.get(SearchActivity.this);
            }
        }.start();

        // 从raw中读取城市列表
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = getResources().openRawResource(R.raw.city);
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String info;

            while ((info = bufferedReader.readLine()) != null) {
                mList.add(info);
            }

            for(String s : mList) {
                L.d(TAG, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        mCityAdapter = new CityAdapter(this, mList);
        mListView.setAdapter(mCityAdapter);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                KeyboardUtils.hideKeyboard(SearchActivity.this, mListView);

                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.d(TAG, mList.get(i));

                City city = new City();
                City.C c = city.new C(mList.get(i));

                if (mCity == null) {
                    mCity = new City();
                }
                mCity.getCList().add(c);

                City.save(SearchActivity.this, mCity);

                finish();
            }
        });

        // 设置EditText监听器
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCityAdapter.getFilter().filter(s);      // 传入过滤的字符串
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
