package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
/**
 * yo秀内容的契约类
 */
public interface YoXiuContentContract {
    interface View extends IBaseView{
        void getYoXiuContentSuccess(YoXiuContentBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void getYoXiuContent(Context context,String user_id, String user_token, String his_id, String page, String page_size);
    }
}
