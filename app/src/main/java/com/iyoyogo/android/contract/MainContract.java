package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.home.VersionBean;

public interface MainContract {
    interface View extends IBaseView{
        void getVersionSuccess(VersionBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void getVersion(String user_id, String user_token, String type);
    }
}
