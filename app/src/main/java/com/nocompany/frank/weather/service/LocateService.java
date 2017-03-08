package com.nocompany.frank.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.nocompany.frank.weather.utils.L;

/**
 * Created by Frank on 2017/1/25 0025.
 * 定位服务
 */

public class LocateService extends Service implements AMapLocationListener {

    public static final String TAG = "LocateService";

    public static final String LOCATE_SERVICE = "com.nocompany.frank.weather.locate_service";

    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String DISTRICT = "District";

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    // 用于定位后的信息保存
    private AMapLocation mAMapLocation = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        new Thread() {
            @Override
            public void run() {
                locate();
            }
        }.start();

        return mLocateBinder;
    }

    private LocateBinder mLocateBinder = new LocateBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        L.d(TAG, "onDestroy");
    }

    /**
     * 定位
     */
    private void locate() {
        mLocationClient = new AMapLocationClient(getApplicationContext());

        //设置定位监听
        mLocationClient.setLocationListener(this);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        // 该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //mLocationOption.setOnceLocationLatest(true);

        // 超时30秒
        mLocationOption.setHttpTimeOut(30000);

        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 获取定位后的信息
     *
     * @return AMapLocation
     */
    public AMapLocation getAMapLocation() {
        return mAMapLocation;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息

                mAMapLocation = aMapLocation;

                Intent intent = new Intent();
                intent.setAction(LOCATE_SERVICE);
                intent.setPackage(getPackageName());

                // 填充省+城市+城区
                intent.putExtra(PROVINCE, aMapLocation.getProvince());
                intent.putExtra(CITY, aMapLocation.getCity());
                intent.putExtra(DISTRICT, aMapLocation.getDistrict());

                // 发送广播
                sendBroadcast(intent);

                L.d(TAG,
                        String.valueOf(
                                "获取当前定位结果来源，如网络定位结果，详见定位类型表 " +
                                        aMapLocation.getLocationType()//获取当前定位结果来源，如网络定位结果，详见定位类型表
                                        + "\n" + "获取纬度 " +
                                        aMapLocation.getLatitude()//获取纬度
                                        + "\n" + "获取经度 " +
                                        aMapLocation.getLongitude()//获取经度
                                        + "\n" + "获取精度信息 " +
                                        aMapLocation.getAccuracy()//获取精度信息
                                        + "\n" + "地址 " +
                                        aMapLocation.getAddress()//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                                        + "\n" + "国家信息 " +
                                        aMapLocation.getCountry()//国家信息
                                        + "\n" + "省信息 " +
                                        aMapLocation.getProvince()//省信息
                                        + "\n" + "城市信息 " +
                                        aMapLocation.getCity()//城市信息
                                        + "\n" + "城区信息 " +
                                        aMapLocation.getDistrict()//城区信息
                                        + "\n" + "街道信息 " +
                                        aMapLocation.getStreet()//街道信息
                                        + "\n" + "街道门牌号信息 " +
                                        aMapLocation.getStreetNum()//街道门牌号信息
                                        + "\n" + "城市编码 " +
                                        aMapLocation.getCityCode()//城市编码
                                        + "\n" + "地区编码 " +
                                        aMapLocation.getAdCode()//地区编码
                                        + "\n" + "获取当前定位点的AOI信息 " +
                                        aMapLocation.getAoiName()//获取当前定位点的AOI信息
                                        + "\n" + "获取当前室内定位的建筑物Id " +
                                        aMapLocation.getBuildingId()//获取当前室内定位的建筑物Id
                                        + "\n" + "获取当前室内定位的楼层 " +
                                        aMapLocation.getFloor()//获取当前室内定位的楼层
                                //aMapLocation.getGpsStatus()//获取GPS的当前状态
                        ));

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                L.d(TAG, "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
        //stopSelf();
        //onDestroy();
    }

    public class LocateBinder extends Binder {
        /**
         * 获取服务
         *
         * @return LocationService
         */
        public LocateService getService() {
            return LocateService.this;
        }
    }
}
