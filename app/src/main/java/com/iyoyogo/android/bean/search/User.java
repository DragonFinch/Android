package com.iyoyogo.android.bean.search;

import com.iyoyogo.android.bean.BaseBean;

public class User extends BaseBean {
    String name;
    String img;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public User(String name, String img) {

        this.name = name;
        this.img = img;
    }
}
