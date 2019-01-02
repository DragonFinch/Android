package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.center.UserCenterBean;
/**
 * 个人中心的契约类
 */
public interface PersonalCenterContract {
    interface View extends IBaseView{
        void getPersonalCenterSuccess(UserCenterBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void getPersonalCenter(String user_id,String user_token,String his_id);
    }
}
