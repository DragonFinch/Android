package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.HisPositionBean;
import com.iyoyogo.android.bean.mine.AboutMeBean;

/**
 * 关于我们的契约类
 */
public interface HisPositionContract {
    interface View extends IBaseView {
        void setHisPosition(HisPositionBean bean);
        void DelPosition(BaseBean bean);
    }

    interface Presenter extends IBasePresenter {
        void getHisPosition(String user_id, String user_token, int page, int page_size);
        void DelPosition(String user_id, String user_token);
    }
}
