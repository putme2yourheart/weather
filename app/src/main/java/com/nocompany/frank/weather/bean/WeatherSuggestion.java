package com.nocompany.frank.weather.bean;

import java.util.List;

/**
 * Created by Frank on 2017/1/25 0025.
 * 生活指数
 */

public class WeatherSuggestion {

    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public class Results {

        private Location location;
        private Suggest suggestion;
        private String last_update;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Suggest getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(Suggest suggestion) {
            this.suggestion = suggestion;
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

        public class Suggest {
            private Info car_washing;
            private Info dressing;
            private Info flu;
            private Info sport;
            private Info travel;
            private Info uv;

            public Info getCar_washing() {
                return car_washing;
            }

            public void setCar_washing(Info car_washing) {
                this.car_washing = car_washing;
            }

            public Info getDressing() {
                return dressing;
            }

            public void setDressing(Info dressing) {
                this.dressing = dressing;
            }

            public Info getFlu() {
                return flu;
            }

            public void setFlu(Info flu) {
                this.flu = flu;
            }

            public Info getSport() {
                return sport;
            }

            public void setSport(Info sport) {
                this.sport = sport;
            }

            public Info getTravel() {
                return travel;
            }

            public void setTravel(Info travel) {
                this.travel = travel;
            }

            public Info getUv() {
                return uv;
            }

            public void setUv(Info uv) {
                this.uv = uv;
            }
        }

        public class Info {
            private String brief;
            private String details;

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
            }
        }

    }

}
