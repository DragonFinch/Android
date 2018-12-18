package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;

public interface ChooseSignContract {
    interface View extends IBaseView {

        void getLabelListSuccess(LabelListBean.DataBean data);

        void addLabelSuccess(AddLabelBean.DataBean data);

        void deleteLabelSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {

        void getLabelList(String user_id, String user_token);

        void addLabel(String user_id, String user_token, int label_id, int type, String label);

        void deleteLabel(String user_id, String user_token, int label_id);

    }
}
