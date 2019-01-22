package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.search.ClerBean;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;
import com.iyoyogo.android.bean.search.searchInfo;

public interface KeywordContract {
    interface View extends IBaseView {
        void keyWordMessage(KeywordBean keywordBean);
        void guanZhu(GuanZhuBean keywordBean);
        void search(KeywordUserBean keywordBean);

        void getRecommendTopicSuccess(searchInfo list);
        void getData(ClerBean clerBean);
    }
    interface Presenter extends IBasePresenter {
        void getKeyWord(Context context, String user_id, String user_token, String search, String type, String key_type);
        void getGuanZhu(String user_id, String user_token, String target_id);
        void getSearch(Context context,String user_id, String user_token, String search);
        void getSearch(Context context, String user_id, String user_token);
        void getSearchCler(Context context, String user_id, String user_token);
    }
}
