package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.search.ClerBean;
import com.iyoyogo.android.bean.search.SearchBean;
import com.iyoyogo.android.bean.search.searchInfo;

import java.util.List;

public interface SearchContract {
    interface View extends IBaseView {

        void getRecommendTopicSuccess(searchInfo list);
        void getData(ClerBean clerBean);
    }

    interface Presenter extends IBasePresenter {
        void getSearch(String user_id, String user_token);
        void getSearchCler(String user_id, String user_token);
    }
}
