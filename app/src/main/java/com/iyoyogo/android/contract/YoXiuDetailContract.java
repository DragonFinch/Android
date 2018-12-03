package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoxiu.YoXiuDetailBean;

public interface YoXiuDetailContract {
    interface View extends IBaseView{
        void getDetailSuccess(YoXiuDetailBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void getDetail(String user_id,String user_token,int id);
    }
}
