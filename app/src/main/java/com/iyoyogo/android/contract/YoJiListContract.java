package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoji.list.YoJiListBean;

import java.util.List;

public interface YoJiListContract {
    interface View extends IBaseView {
        void getYoJiListSuccess(List<YoJiListBean.DataBean.ListBean> list);
    }

    interface Presenter extends IBasePresenter {
        void getYoJiList(Context context,String user_id, String user_token, int page, int page_size);
    }
}
