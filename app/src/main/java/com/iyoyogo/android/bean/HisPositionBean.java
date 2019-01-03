package com.iyoyogo.android.bean;

import java.util.List;

/***
 * 获取用户历史自定义位置
 */
public class HisPositionBean extends BaseBean{

    /**
     * data : {"list":[{"id":"3","user_id":"80002","name":"妳","en_name":"","areas":"中国,北京市,北京市,昌平区","address":"北京市昌平区小汤山镇","lng":"116.480934","lat":"40.15327","type_id":"5","create_time":"2019-01-02 14:54:39","update_time":null,"longitude":"","latitude":"","type_name":"","r":"0"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 3
             * user_id : 80002
             * name : 妳
             * en_name :
             * areas : 中国,北京市,北京市,昌平区
             * address : 北京市昌平区小汤山镇
             * lng : 116.480934
             * lat : 40.15327
             * type_id : 5
             * create_time : 2019-01-02 14:54:39
             * update_time : null
             * longitude :
             * latitude :
             * type_name :
             * r : 0
             */

            private String id;
            private String user_id;
            private String name;
            private String en_name;
            private String areas;
            private String address;
            private String lng;
            private String lat;
            private String type_id;
            private String create_time;
            private Object update_time;
            private String longitude;
            private String latitude;
            private String type_name;
            private String r;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEn_name() {
                return en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public String getAreas() {
                return areas;
            }

            public void setAreas(String areas) {
                this.areas = areas;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public Object getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(Object update_time) {
                this.update_time = update_time;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getR() {
                return r;
            }

            public void setR(String r) {
                this.r = r;
            }
        }
    }
}
