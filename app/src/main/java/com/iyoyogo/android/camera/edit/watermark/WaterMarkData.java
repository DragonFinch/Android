package com.iyoyogo.android.camera.edit.watermark;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CaoZhiChao on 2018/9/21 one_one:03
 */
public class WaterMarkData {
    private List<PointF> pointFInLiveWindow;
    private int excursionX ;
    private int excursionY ;
    private int picWidth ;
    private int picHeight ;
    private String picPath ;

    public WaterMarkData(List<PointF> pointFInLiveWindow, int excursionX, int excursionY, int picWidth, int picHeight, String picPath, Point pointOfLiveWindow) {
        this.pointFInLiveWindow = pointFInLiveWindow;
        this.excursionX = excursionX;
        this.excursionY = excursionY;
        this.picWidth = picWidth;
        this.picHeight = picHeight;
        this.picPath = picPath;
        this.pointOfLiveWindow = pointOfLiveWindow;
    }

    public Point getPointOfLiveWindow() {
        return pointOfLiveWindow;
    }

    public void setPointOfLiveWindow(Point pointOfLiveWindow) {
        this.pointOfLiveWindow = pointOfLiveWindow;
    }

    private Point pointOfLiveWindow ;


    public List<PointF> getPointFInLiveWindow() {
        if (pointFInLiveWindow == null) {
            return new ArrayList<>();
        }
        return pointFInLiveWindow;
    }

    public void setPointFInLiveWindow(List<PointF> pointFInLiveWindow) {
        this.pointFInLiveWindow = pointFInLiveWindow;
    }

    public int getExcursionX() {
        return excursionX;
    }

    public void setExcursionX(int excursionX) {
        this.excursionX = excursionX;
    }

    public int getExcursionY() {
        return excursionY;
    }

    public void setExcursionY(int excursionY) {
        this.excursionY = excursionY;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public String getPicPath() {
        return picPath == null ? "" : picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? "" : picPath;
    }
}
