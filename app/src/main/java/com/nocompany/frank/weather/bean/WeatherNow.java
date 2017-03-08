package com.nocompany.frank.weather.bean;

import java.util.List;

/**
 * Created by Frank on 2016/12/25 0025.
 * 保存天气信息的bean
 */

public class WeatherNow {

    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public class Results {

        private Location location;
        private Now now;
        private String last_update;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        @Override
        public String toString() {
            return "Rsults{" +
                    "location=" + location +
                    ", now=" + now +
                    ", last_update='" + last_update + '\'' +
                    '}';
        }
    }


    public class Now {
        private String text;
        private int code;
        private int temperature;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return "WeatherNow{" +
                    "text='" + text + '\'' +
                    ", code=" + code +
                    ", temperature=" + temperature +
                    '}';
        }
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

}

