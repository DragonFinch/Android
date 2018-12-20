package com.iyoyogo.android.bean.yoji.publish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageBean implements Serializable {
    private ArrayList<String> logos;
    private String start_date;
    private String end_date;
    private String position_name;
    private String position_areas;
    private String position_address;
    private List<Integer> label_ids;
    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public ArrayList<String> getLogos() {
        return logos;
    }

    public void setLogos(ArrayList<String> logos) {
        this.logos = logos;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getPosition_areas() {
        return position_areas;
    }

    public void setPosition_areas(String position_areas) {
        this.position_areas = position_areas;
    }

    public String getPosition_address() {
        return position_address;
    }

    public void setPosition_address(String position_address) {
        this.position_address = position_address;
    }

    public List<Integer> getLabel_ids() {
        return label_ids;
    }

    public void setLabel_ids(List<Integer> label_ids) {
        this.label_ids = label_ids;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "logos=" + logos +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", position_name='" + position_name + '\'' +
                ", position_areas='" + position_areas + '\'' +
                ", position_address='" + position_address + '\'' +
                ", label_ids=" + label_ids +
                '}';
    }
}
