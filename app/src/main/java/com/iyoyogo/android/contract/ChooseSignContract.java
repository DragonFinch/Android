package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
/**
 * 选择标签的契约类
 */
public interface ChooseSignContract {
    interface View extends IBaseView {
        //获取标签
        void getLabelListSuccess(LabelListBean.DataBean data);
        //添加标签
        void addLabelSuccess(AddLabelBean.DataBean data);
        //删除标签
        void deleteLabelSuccess(BaseBean baseBean);
    }

    interface Presenter extends IBasePresenter {

        void getLabelList(Context context,String user_id, String user_token);

        void addLabel(Context context,String user_id, String user_token, int label_id, int type, String label);

        void deleteLabel(Context context,String user_id, String user_token, int label_id);

    }
}
