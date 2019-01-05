package com.iyoyogo.android.utils.util;

/**
 * 爱生活，爱代码
 * 创建于：2018/11/12 14:51
 * 作 者：T
 * 微信：704003376
 */
public class PinYinInfo implements Comparable<PinYinInfo> {
    private String mName;
    private String mPinYinName;

    public String getmPinYinName() {
        return mPinYinName;
    }

    public void setmPinYinName(String mPinYinName) {
        this.mPinYinName = mPinYinName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;

    }

    public PinYinInfo() {
        super();
    }

    public PinYinInfo(String mName) {
        this.mName = mName;
        setmPinYinName(PinYinUtils.getPinYin(mName));
    }

    @Override
    public int compareTo(PinYinInfo anotherPinYinIno) {
        return getmPinYinName().compareTo(anotherPinYinIno.getmPinYinName());  //负数   左边<右边  0 左边=右边  正数 左边>右边
    }
}
