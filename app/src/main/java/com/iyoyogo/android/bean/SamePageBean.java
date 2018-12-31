package com.iyoyogo.android.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class SamePageBean implements MultiItemEntity {

    public static final int Type1 = 1;
    public static final int Type2 = 2;
    public static final int Type3 = 3;
    private int itemType;

    private int type;//标识
    private int img;//图片/视频
    private String title;//标题
    private String name;//排序
    private int view_type;//布局标识

    public SamePageBean(int type, int img, String title, String name, int view_type) {
        this.type = type;
        this.img = img;
        this.title = title;
        this.name = name;
        this.view_type = view_type;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public int getType() {
        return type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getItemType() {
        if (view_type == 1) {
            return Type1;
        } else if (view_type == 2) {
            return Type2;
        } else if (view_type == 3) {
            return Type3;
        }
        return itemType;
    }
}
