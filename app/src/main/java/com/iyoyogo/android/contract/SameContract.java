package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.SameBean;
import com.iyoyogo.android.bean.mine.AboutMeBean;

import java.util.List;

public interface SameContract {
    interface View extends IBaseView {
        void onSameList(SameBean data);

        void onMoreSameList(SameBean data);
    }

    interface Presenter extends IBasePresenter {
        void getSameList(String user_id, String user_token, String lng, String lat, int page, String page_size);
    }
}
