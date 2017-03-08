package com.nocompany.frank.weather;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nocompany.frank.weather.bean.Area;
import com.nocompany.frank.weather.bean.WeatherDaily;
import com.nocompany.frank.weather.bean.WeatherNow;
import com.nocompany.frank.weather.bean.WeatherSuggestion;
import com.nocompany.frank.weather.service.LocateService;
import com.nocompany.frank.weather.utils.Cache;
import com.nocompany.frank.weather.utils.L;
import com.nocompany.frank.weather.utils.SP;
import com.nocompany.frank.weather.utils.WeatherUtils;

import java.security.MessageDigest;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private final int weatherIcon[] = {
            R.drawable.sun, R.drawable.sun, R.drawable.sun, R.drawable.sun,
            R.drawable.cloud, R.drawable.partly_cloudy_day, R.drawable.partly_cloudy_day, R.drawable.clouds,
            R.drawable.clouds, R.drawable.clouds, R.drawable.partly_cloudy_rain, R.drawable.storm,
            R.drawable.sleet, R.drawable.light_rain, R.drawable.moderate_rain, R.drawable.heavy_rain,
            R.drawable.intense_rain, R.drawable.torrential_rain, R.drawable.torrential_rain, R.drawable.sleet,
            R.drawable.sleet, R.drawable.light_snow, R.drawable.snow, R.drawable.snow,
            R.drawable.snow, R.drawable.snow, R.drawable.dry, R.drawable.dry,
            R.drawable.dry, R.drawable.dry, R.drawable.fog_day, R.drawable.fog_day,
            R.drawable.windy_weather, R.drawable.windy_weather, R.drawable.tornado, R.drawable.tornado,
            R.drawable.tornado, R.drawable.summer, R.drawable.windy_weather
    };

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.tv_temperature)
    TextView mTemperature;
    @BindView(R.id.tv_city)
    TextView mDistrict;
    @BindView(R.id.tv_weather_text)
    TextView mWeatherText;

    @BindView(R.id.iv_day_1)
    ImageView mIcon_1;
    @BindView(R.id.iv_day_2)
    ImageView mIcon_2;
    @BindView(R.id.iv_day_3)
    ImageView mIcon_3;

    @BindView(R.id.tv_weather_text_1)
    TextView mWeatherText_1;
    @BindView(R.id.tv_weather_text_2)
    TextView mWeatherText_2;
    @BindView(R.id.tv_weather_text_3)
    TextView mWeatherText_3;

    @BindView(R.id.tv_day_temperature_1)
    TextView mTemperature_1;
    @BindView(R.id.tv_day_temperature_2)
    TextView mTemperature_2;
    @BindView(R.id.tv_day_temperature_3)
    TextView mTemperature_3;

    @BindView(R.id.tv_car_washing)
    TextView mCarWashing;
    @BindView(R.id.tv_dressing)
    TextView mDressing;
    @BindView(R.id.tv_flu)
    TextView mFlu;
    @BindView(R.id.tv_sport)
    TextView mSport;
    @BindView(R.id.tv_travel)
    TextView mTravel;
    @BindView(R.id.tv_uv)
    TextView mUv;

    private LocateServiceConnection locateServiceConnection;

    // 位置信息
    private Area mArea;

    /**
     * 接收定位服务信息
     */
    private BroadcastReceiver mLocateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent.getAction().equals(LocateService.LOCATE_SERVICE)) {

                // 从Intent中读取位置信息
                final Area area = new Area(intent.getStringExtra(LocateService.PROVINCE),
                        intent.getStringExtra(LocateService.CITY),
                        intent.getStringExtra(LocateService.DISTRICT));

                // 位置不同，更正位置信息
                if (!Area.isSame(mArea, area)) {
                    dialog(new MyDialogInterface() {
                        @Override
                        public void iTrue() {
                            mArea = area;
                            // 保存到SharedPreferences中
                            SP.save(MainActivity.this, mArea);

                            loadWeather();
                        }

                        @Override
                        public void iFalse() {

                        }
                    });
                }

                // 设置城区
                if (intent.getStringExtra(LocateService.DISTRICT) != null) {
                    mDistrict.setText(intent.getStringExtra(LocateService.DISTRICT));
                } else {
                    mDistrict.setText(intent.getStringExtra(LocateService.CITY));
                }

                final String pc = Area.getPC(mArea);

                // 天气
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String weatherNow = WeatherUtils.getWeather(pc);
                        e.onNext(weatherNow);
                        e.onComplete();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String value) {
                                L.d(TAG, value);

                                if (value == null) {
                                    return;
                                }

                                Gson gson = new Gson();
                                WeatherNow weatherNow = gson.fromJson(value, WeatherNow.class);

                                if (weatherNow != null) {
                                    WeatherNow.Results results = weatherNow.getResults().get(0);
                                    mTemperature.setText(String.valueOf(results.getNow().getTemperature() + "°"));
                                    mWeatherText.setText(String.valueOf(results.getNow().getText()));

                                    // 写入缓存
                                    Cache.save(MainActivity.this, weatherNow);
                                }
                                L.d(TAG, gson.fromJson(value, WeatherNow.class).getResults().get(0).getLast_update());
                            }

                            @Override
                            public void onError(Throwable e) {
                                L.d(TAG, "onError test");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                // 三天天气
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String weatherDaily = WeatherUtils.getThreeDayWeather(pc);
                        L.d(TAG, weatherDaily);
                        e.onNext(weatherDaily);
                        e.onComplete();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String value) {
                                if (value == null) {
                                    return;
                                }
                                Gson gson = new Gson();
                                WeatherDaily weatherDaily = gson.fromJson(value, WeatherDaily.class);

                                if (weatherDaily != null) {
                                    WeatherDaily.Results results = weatherDaily.getResults().get(0);

                                    // 今天
                                    WeatherDaily.Results.Daily daily_1 = results.getDaily().get(0);
                                    mIcon_1.setImageResource(weatherIcon[daily_1.getCode_day()]);
                                    mTemperature_1.setText(String.valueOf(daily_1.getHigh() + "° / " + daily_1.getLow() + "°"));
                                    mWeatherText_1.setText(String.valueOf(daily_1.getText_day()));

                                    // 明天
                                    WeatherDaily.Results.Daily daily_2 = results.getDaily().get((1));
                                    mIcon_2.setImageResource(weatherIcon[daily_2.getCode_day()]);
                                    mTemperature_2.setText(String.valueOf(daily_2.getHigh() + "° / " + daily_2.getLow() + "°"));
                                    mWeatherText_2.setText(String.valueOf(daily_2.getText_day()));

                                    // 后天
                                    WeatherDaily.Results.Daily daily_3 = results.getDaily().get(2);
                                    mIcon_3.setImageResource(weatherIcon[daily_3.getCode_day()]);
                                    mTemperature_3.setText(String.valueOf(daily_3.getHigh() + "° / " + daily_3.getLow() + "°"));
                                    mWeatherText_3.setText(String.valueOf(daily_3.getText_day()));

                                    // 写入缓存
                                    Cache.save(MainActivity.this, weatherDaily);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                // 天气指数
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        String weatherSuggestion = WeatherUtils.getSuggestion(pc);
                        e.onNext(weatherSuggestion);
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String value) {
                                if (value == null) {
                                    return;
                                }

                                Gson gson = new Gson();
                                WeatherSuggestion weatherSuggestion = gson.fromJson(value, WeatherSuggestion.class);
                                if (weatherSuggestion != null) {
                                    WeatherSuggestion.Results.Suggest suggest = weatherSuggestion.getResults().get(0).getSuggestion();

                                    mCarWashing.setText(String.valueOf(suggest.getCar_washing().getBrief()));
                                    mDressing.setText(String.valueOf(suggest.getDressing().getBrief()));
                                    mFlu.setText(String.valueOf(suggest.getFlu().getBrief()));
                                    mSport.setText(String.valueOf(suggest.getSport().getBrief()));
                                    mTravel.setText(String.valueOf(suggest.getTravel().getBrief()));
                                    mUv.setText(String.valueOf(suggest.getUv().getBrief()));

                                    // 写入缓存
                                    Cache.save(MainActivity.this, weatherSuggestion);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                if (locateServiceConnection != null) {
                    unbindService(locateServiceConnection);
                }

                mSwipeRefreshLayout.setRefreshing(false);

                Snackbar.make(mSwipeRefreshLayout, "天气数据更新完毕", Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    private void loadWeather() {
        locateServiceConnection = new LocateServiceConnection();
        // 绑定服务
        bindService(new Intent(MainActivity.this, LocateService.class), locateServiceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // 注册广播
        IntentFilter intentFilter = new IntentFilter(LocateService.LOCATE_SERVICE);
        registerReceiver(mLocateReceiver, intentFilter);

        L.d(TAG, "sha1: " + sHA1(this));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                L.d(TAG, "refresh");
                loadWeather();
            }
        });


        // 获取缓存中的位置
        mArea = SP.get(this);

        // 读取天气信息缓存
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String weatherNow = Cache.get(MainActivity.this, WeatherNow.class);
                e.onNext(weatherNow);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Area area = SP.get(MainActivity.this);
                        if (area != null) {
                            mDistrict.setText(String.valueOf(area.getDistrict()));
                        }

                        if (value == null) {
                            return;
                        }

                        Gson gson = new Gson();
                        WeatherNow weatherNow = gson.fromJson(value, WeatherNow.class);

                        if (weatherNow != null) {
                            mTemperature.setText(String.valueOf(weatherNow.getResults().get(0).getNow().getTemperature() + "°"));
                            mWeatherText.setText(String.valueOf(weatherNow.getResults().get(0).getNow().getText()));

                            L.d(TAG, weatherNow.getResults().get(0).getLast_update() + "++++123");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 读取三天天气缓存
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String weatherDaily = Cache.get(MainActivity.this, WeatherDaily.class);
                e.onNext(weatherDaily);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        if (value == null) {
                            return;
                        }
                        Gson gson = new Gson();
                        WeatherDaily weatherDaily = gson.fromJson(value, WeatherDaily.class);

                        if (weatherDaily != null) {
                            WeatherDaily.Results.Daily daily_1 = weatherDaily.getResults().get(0).getDaily().get(0);
                            WeatherDaily.Results.Daily daily_2 = weatherDaily.getResults().get(0).getDaily().get(1);
                            WeatherDaily.Results.Daily daily_3 = weatherDaily.getResults().get(0).getDaily().get(2);

                            // 今天
                            mIcon_1.setImageResource(weatherIcon[daily_1.getCode_day()]);
                            mTemperature_1.setText(String.valueOf(daily_1.getHigh() + "° / " + daily_1.getLow() + "°"));
                            mWeatherText_1.setText(String.valueOf(daily_1.getText_day()));

                            // 明天
                            mIcon_2.setImageResource(weatherIcon[daily_2.getCode_day()]);
                            mTemperature_2.setText(String.valueOf(daily_2.getHigh() + "° / " + daily_2.getLow() + "°"));
                            mWeatherText_2.setText(String.valueOf(daily_2.getText_day()));

                            // 后天
                            mIcon_3.setImageResource(weatherIcon[daily_3.getCode_day()]);
                            mTemperature_3.setText(String.valueOf(daily_3.getHigh() + "° / " + daily_3.getLow() + "°"));
                            mWeatherText_3.setText(String.valueOf(daily_3.getText_day()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 从缓存中读取天气指数
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String weatherSuggestion = Cache.get(MainActivity.this, WeatherSuggestion.class);
                e.onNext(weatherSuggestion);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        if (value == null) {
                            return;
                        }

                        Gson gson = new Gson();
                        WeatherSuggestion weatherSuggestion = gson.fromJson(value, WeatherSuggestion.class);
                        if (weatherSuggestion != null) {
                            WeatherSuggestion.Results.Suggest suggest = weatherSuggestion.getResults().get(0).getSuggestion();

                            mCarWashing.setText(String.valueOf(suggest.getCar_washing().getBrief()));
                            mDressing.setText(String.valueOf(suggest.getDressing().getBrief()));
                            mFlu.setText(String.valueOf(suggest.getFlu().getBrief()));
                            mSport.setText(String.valueOf(suggest.getSport().getBrief()));
                            mTravel.setText(String.valueOf(suggest.getTravel().getBrief()));
                            mUv.setText(String.valueOf(suggest.getUv().getBrief()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        locateServiceConnection = new LocateServiceConnection();
        // 绑定服务
        bindService(new Intent(MainActivity.this, LocateService.class), locateServiceConnection, Service.BIND_AUTO_CREATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 关于
        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (locateServiceConnection != null) {
            unbindService(locateServiceConnection);
        }

        // 注销广播接收者
        unregisterReceiver(mLocateReceiver);
    }

    /**
     * 点击dialog返回的接口
     */
    private interface MyDialogInterface {
        void iTrue();

        void iFalse();
    }

    private void dialog(final MyDialogInterface myDialogInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("位置发生改变")
                .setPositiveButton(
                        "更改位置",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myDialogInterface.iTrue();
                                dialogInterface.dismiss();
                            }
                        })
                .setNegativeButton(
                        "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                myDialogInterface.iFalse();
                                dialogInterface.dismiss();
                            }
                        })
                .show();
    }

    private class LocateServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
