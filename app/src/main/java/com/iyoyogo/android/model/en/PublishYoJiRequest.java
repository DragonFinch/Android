package com.iyoyogo.android.model.en;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class PublishYoJiRequest extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        List<String> logos;
        String start_date;
        String end_date;
        String position_name;
        String position_areas;
        String position_address;
        List<Integer> label_ids;
        String lng;
        String lat;

        public void setPosition_address(String position_address) {
            this.position_address = position_address;
        }

        public void setLogos(List<String> logos) {
            this.logos = logos;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public void setPosition_name(String position_name) {
            this.position_name = position_name;
        }

        public void setPosition_areas(String position_areas) {
            this.position_areas = position_areas;
        }

        public void setLabel_ids(List<Integer> label_ids) {
            this.label_ids = label_ids;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
    /**
     * start_date	str	是	开始日期 xxxx-xx-xx
     * end_date	str	是	结束日期 xxxx-xx-xx
     * position_name	str	是	位置名称 例如 : 金星公园
     * position_areas	str	是	位置地区 例如 : 中国,河北,保定
     * position_address	str	是	位置地址 例如 : 金星西路13号
     * label_ids	list	是	标签id list
     * lng	list	是	lng
     * lat	list	是	lat
     */

}
