package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.map.MapBean;

public interface MapContract {
    interface View extends IBaseView {
        void map(MapBean map);
    }
    interface Presenter extends IBasePresenter {
        void aboutMe(Context context, String user_id, String user_token, String type, String search);
    }
}
