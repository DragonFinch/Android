package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;

public interface MineContract {
    interface View extends IBaseView {
        void getUserInfoSuccess(MineMessageBean.DataBean data);
        void punchClockSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {
        void getUserInfo(String user_id, String user_token);
        void punchClock(String user_id, String user_token);
    }
}
