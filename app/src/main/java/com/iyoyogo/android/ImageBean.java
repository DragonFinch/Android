package com.iyoyogo.android;

import java.util.List;

public class ImageBean {
    List<ItemBean> imgList;
    String url;

    public List<ItemBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ItemBean> imgList) {
        this.imgList = imgList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageBean(String url) {
        this.url = url;
    }
}
