package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;

public interface YoXiuListContract {
    interface View extends IBaseView{
        void getYoXiuListSuccess(YouXiuListBean.DataBean data);
        void loadMoreYoXiuListSuccess(YouXiuListBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void getYoXiuList(Context context,String user_id, String user_token, int page);
        void loadMoreYoXiuList(Context context,String user_id,String user_token,int page);
    }
}
