package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoxiu.TypeBean;

public interface CreatePointContract {
    interface View extends IBaseView{
    void setTypeSuccess(TypeBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void setType(String user_id,String user_token);
    }
}
