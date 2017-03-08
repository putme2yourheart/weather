package com.nocompany.frank.weather.bean;

import java.util.List;

/**
 * Created by Frank on 2017/1/25 0025.
 * 未来三天的天气状况
 */

public class WeatherDaily {

    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public class Results {

        private Location location;
        private List<Daily> daily;
        private String last_update;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public List<Daily> getDaily() {
            return daily;
        }

        public void setDaily(List<Daily> daily) {
            this.daily = daily;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public class Location {
            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }

            @Override
            public String toString() {
                return "Location{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", country='" + country + '\'' +
                        ", path='" + path + '\'' +
                        ", timezone='" + timezone + '\'' +
                        ", timezone_offset='" + timezone_offset + '\'' +
                        '}';
            }
        }

        public class Daily {
            // 日期
            private String date;
            //白天天气现象文字
            private String text_day;
            //白天天气现象代码
            private int code_day;
            //晚间天气现象文字
            private String text_night;
            //晚间天气现象代码
            private int code_night;
            // 当天最高温度
            private int high;
            //当天最低温度
            private int low;
            // 降水概率，范围0~100，单位百分比
            private String precip;
            // 风向文字
            private String wind_direction;
            // 风向角度，范围0~360
            private String wind_direction_degree;
            // 风速，单位km/h（当unit=c时）、mph（当unit=f时）
            private int wind_speed;
            // 风力等级
            private String wind_scale;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getText_day() {
                return text_day;
            }

            public void setText_day(String text_day) {
                this.text_day = text_day;
            }

            public int getCode_day() {
                return code_day;
            }

            public void setCode_day(int code_day) {
                this.code_day = code_day;
            }

            public String getText_night() {
                return text_night;
            }

            public void setText_night(String text_night) {
                this.text_night = text_night;
            }

            public int getCode_night() {
                return code_night;
            }

            public void setCode_night(int code_night) {
                this.code_night = code_night;
            }

            public int getHigh() {
                return high;
            }

            public void setHigh(int high) {
                this.high = high;
            }

            public int getLow() {
                return low;
            }

            public void setLow(int low) {
                this.low = low;
            }

            public String getPrecip() {
                return precip;
            }

            public void setPrecip(String precip) {
                this.precip = precip;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public int getWind_speed() {
                return wind_speed;
            }

            public void setWind_speed(int wind_speed) {
                this.wind_speed = wind_speed;
            }

            public String getWind_direction_degree() {
                return wind_direction_degree;
            }

            public void setWind_direction_degree(String wind_direction_degree) {
                this.wind_direction_degree = wind_direction_degree;
            }

            public String getWind_scale() {
                return wind_scale;
            }

            public void setWind_scale(String wind_scale) {
                this.wind_scale = wind_scale;
            }

            @Override
            public String toString() {
                return "Daily{" +
                        "date='" + date + '\'' +
                        ", text_day='" + text_day + '\'' +
                        ", code_day=" + code_day +
                        ", text_night='" + text_night + '\'' +
                        ", code_night=" + code_night +
                        ", high=" + high +
                        ", low=" + low +
                        ", precip=" + precip +
                        ", wind_direction='" + wind_direction + '\'' +
                        ", wind_direction_degree=" + wind_direction_degree +
                        ", wind_speed=" + wind_speed +
                        ", wind_scale=" + wind_scale +
                        '}';
            }
        }

    }

}
