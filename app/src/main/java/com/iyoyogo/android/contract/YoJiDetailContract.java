package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;

public interface YoJiDetailContract {
    interface View extends IBaseView {
        void getYoJiDetailSuccess(YoJiDetailBean.DataBean data);
    }

    interface Presenter extends IBasePresenter {
        void getYoJiDetail(String user_id, String user_token, int yo_id);
    }
}
