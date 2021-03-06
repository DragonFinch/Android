package com.iyoyogo.android.contract;


import android.content.Context;

import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.login.interest.InterestBean;

import java.util.ArrayList;
import java.util.List;
/**
 * 用户兴趣的契约类
 */
public interface InterestContract  {
    interface View extends IBaseView {
        void loadDataSuccess(List<InterestBean.DataBean.ListBean> list);
        void addInterestSuccess();
    }
    interface Presenter extends IBasePresenter {
        void getInterestSign(Context context, String user_id, String user_token);
        void addInterest(Context context,ArrayList<Integer> interest_ids, String user_id, String user_token);
    }
}
