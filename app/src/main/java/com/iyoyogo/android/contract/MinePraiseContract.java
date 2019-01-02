package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.PraiseBean;
/**
 * 我的喜欢的契约类
 */
public interface MinePraiseContract {
    interface View extends IBaseView{
        void getPraiseSuccess(PraiseBean praiseBean);
    }
    interface Presenter extends IBasePresenter{
        void getPraise(String user_id, String user_token, int page, int page_size);
    }
}
