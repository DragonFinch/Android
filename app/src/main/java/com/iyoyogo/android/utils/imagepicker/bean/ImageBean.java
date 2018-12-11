package com.iyoyogo.android.utils.imagepicker.bean;

/**
 * Created by eric on 2017/12/3.
 */

public class ImageBean {
    private String imagePath;
    private String imageName;
    private boolean isChoose = false;
    public ImageBean(String path, String name){
        this.imagePath = path;
        this.imageName = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
