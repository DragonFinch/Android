package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.GetUserInfoBean;
/**
 * 编辑用户信息的契约类
 */
public interface EditPersonalContract {
    interface View extends IBaseView{
        void getUserInfoSuccess(GetUserInfoBean.DataBean data);
        void setUserInfoSuccess(BaseBean baseBean);
    }
    interface Presenter extends IBasePresenter{
        void getUserInfo(String user_id, String user_token);
        void setUserInfo(String user_id,
                         String user_token,
                         String user_nickname,
                         String user_logo,
                         String user_sex,
                         String user_birthday,
                         String user_city);
    }
}
