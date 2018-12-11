package com.iyoyogo.android.utils.imagepicker.component;

/**
 * Created by 肖庆鸿 on 2017/12/4.
 */

public interface OnItemChooseCallback {
    /**
     * 点击单项时
     * @param position 位置
     * @param isChosen 是否选中
     */
    void chooseState(int position, boolean isChosen);

    /**
     * 现在的值
     * @param countNow
     */
    void countNow(int countNow);

    /**
     * 警告不能再选了
     * @param count
     */
    void countWarning(int count);
}
