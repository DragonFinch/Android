package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;

/**
 * yo记内容的契约类
 */
public interface YoJiContentContract {
    interface View extends IBaseView {
        void getYoJiContentSuccess(YoJiContentBean.DataBean data);
    }

    interface Presenter extends IBasePresenter {
        void getYoJiContent(Context context, String user_id, String user_token, String his_id, String page, String page_size);
    }
}
