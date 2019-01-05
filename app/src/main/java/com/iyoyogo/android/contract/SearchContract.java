package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.search.SearchBean;

import java.util.List;

public interface SearchContract {
    interface View extends IBaseView {
        void publishYoJiSuccess(SearchBean baseBean);

        void getRecommendTopicSuccess(List<String> list);
    }

    interface Presenter extends IBasePresenter {
        void getSearch(String user_id, String user_token);
    }
}
