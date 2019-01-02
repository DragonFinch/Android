package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.VipCenterBean;
/**
 * 会员中心的契约类
 */
public interface VipCenterContract {

    interface View extends IBaseView {
        void getVipCenterSuccess(VipCenterBean vipCenterBean);
    }

    interface Presenter extends IBasePresenter {

        void getVipCenter(String user_id, String user_token);
    }
}
