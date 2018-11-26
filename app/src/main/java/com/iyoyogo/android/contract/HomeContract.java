package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;

public interface HomeContract {
    interface View extends IBaseView{
        void  bannerSuccess(HomeViewPagerBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void banner(String user_id, String user_token, String type);
    }
}
