package com.nocompany.frank.weather.bean;

/**
 * Created by Frank on 2017/2/18.
 * 保存地区
 */

public class Area {
    // 省
    private String mProvince;
    // 城市
    private String mCity;
    // 城区
    private String mDistrict;

    /**
     * 判断位置是否一样
     *
     * @param area1 位置1
     * @param area2 位置2
     * @return boolean
     */
    public static boolean isSame(Area area1, Area area2) {
        return !(area1 == null || area2 == null) &&
                area1.getProvince().equals(area2.getProvince()) &&
                        area1.getCity().equals(area2.getCity()) &&
                        area1.getDistrict().equals(area2.getDistrict());
    }

    /**
     * 获取（省）+（市）
     *
     * @param area Area
     * @return 广东（省）广州（市）
     */
    public static String getPC(Area area) {

        if (area == null) {
            return "";
        }

        String pc = "";
        String province = area.getProvince();
        String city = area.getCity();
        if (province.endsWith("省")) {
            pc = province.substring(0, province.length() - 1);
        }
        if (city.endsWith("市")) {
            pc += city.substring(0, city.length() - 1);
        }

        return pc;
    }

    public Area(String province, String city, String district) {
        mProvince = province;
        mCity = city;
        mDistrict = district;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        mProvince = province;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }
}
