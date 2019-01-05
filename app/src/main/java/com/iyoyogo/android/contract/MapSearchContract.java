package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.map.MapBean;

public interface MapSearchContract {
    interface View extends IBaseView {
        void aboutMeSuccess(MapBean data);
    }
    interface Presenter extends IBasePresenter {
        void aboutMe(String user_id, String user_token, String type, String search);
    }
}
