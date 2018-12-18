package com.iyoyogo.android.bean.yoji.publish;

import java.util.List;

public class MessageBean {
    private String start_time;
    private String end_time;
    private List<String> image_list;
    private String position_name;
    private List<String> sign_list;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public List<String> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<String> image_list) {
        this.image_list = image_list;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public List<String> getSign_list() {
        return sign_list;
    }

    public void setSign_list(List<String> sign_list) {
        this.sign_list = sign_list;
    }
}
