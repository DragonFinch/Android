package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.AboutMeBean;

/**
 * 关于我们的契约类
 */
public interface AboutMeContract {
    interface View extends IBaseView{
        void aboutMeSuccess(AboutMeBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{
        void aboutMe();
    }
}
