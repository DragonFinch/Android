package com.iyoyogo.android.bean;

import java.util.List;

public class CitySelectorBean {


    private List<CityBean> City;

    public List<CityBean> getCity() {
        return City;
    }

    public void setCity(List<CityBean> City) {
        this.City = City;
    }

    public static class CityBean {
        /**
         * name : 定位
         * key : 0
         * first :
         * full :
         * code :
         */

        private String name;
        private String key;
        private String first;
        private String full;
        private String code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
