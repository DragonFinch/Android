package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.home.HomeBean;
/**
 * 首页的契约类
 */
public interface HomeContract {
    interface View extends IBaseView{
        void  bannerSuccess(HomeBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void banner(Context context, String user_id, String user_token, String type, String city);
    }
}
