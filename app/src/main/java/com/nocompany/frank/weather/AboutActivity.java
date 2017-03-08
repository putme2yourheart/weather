package com.nocompany.frank.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Frank on 2017/3/7.
 * 关于
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);      // 显示返回按钮
        }

        getFragmentManager().beginTransaction().replace(R.id.frame, new AboutFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();       // 按下返回按钮后结束本Activity
        }
        return super.onOptionsItemSelected(item);
    }

}
