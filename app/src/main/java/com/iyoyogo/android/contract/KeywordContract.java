package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.search.GuanZhuBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.bean.search.KeywordUserBean;

public interface KeywordContract {
    interface View extends IBaseView {
        void keyWordMessage(KeywordBean keywordBean);
        void guanZhu(GuanZhuBean keywordBean);
        void search(KeywordUserBean keywordBean);
    }
    interface Presenter extends IBasePresenter {
        void getKeyWord(String user_id, String user_token, String search, String type);
        void getGuanZhu(String user_id, String user_token, String target_id);
        void getSearch(String user_id, String user_token, String search);
    }
}
