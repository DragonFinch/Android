package com.iyoyogo.android.bean.map;

public class MapInfo {
    private String name;
    private String key;
    private String pinyin;  //全拼
    private String first;   //首字母
    private String cityCode;
    private String china_name;

    public MapInfo(String name, String key, String pinyin, String first, String cityCode, String china_name) {
        this.name = name;
        this.key = key;
        this.pinyin = pinyin;
        this.first = first;
        this.cityCode = cityCode;
        this.china_name = china_name;
    }

    public MapInfo() {
        super();
    }

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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getChina_name() {
        return china_name;
    }

    public void setChina_name(String china_name) {
        this.china_name = china_name;
    }
}
