package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.mine.DraftBean;

import java.util.List;

public interface DraftContract {
    interface View extends IBaseView{
        void getDraftSuccess(List<DraftBean.DataBean.ListBean> list);
    }
    interface Presenter extends IBasePresenter{
        void getDraft(String user_id, String user_token, int page, int page_size);
    }
}
