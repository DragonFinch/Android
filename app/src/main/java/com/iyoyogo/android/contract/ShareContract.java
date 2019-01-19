package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.AboutMeBean;

public interface ShareContract {
    interface View extends IBaseView{
        void onShareSuccess(BaseBean data);
    }
    interface Presenter extends IBasePresenter{
        void share(Context context, String user_id, String user_token, String id);
    }
}
