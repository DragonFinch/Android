package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;

import java.util.List;
/**
 * 更多话题的契约类
 */
public interface MoreTopicContract {
    interface View extends IBaseView{

        void getHotTopicSuccess(List<HotTopicBean.DataBean.ListBean> list);

        void getNearTopicSuccess(List<HotTopicBean.DataBean.ListBean> list);

        void getClearTopicSuccess();

        void createTopicSuccess(CreateTopicBean.DataBean data);
    }
    interface Presenter extends IBasePresenter{

        void getHotTopic(String user_id,String user_token,String search);

        void getNearTopic(String user_id,String user_token );

        void getClearTopic(String user_id,String user_token);

        void getCreateTopic(String user_id,String user_token,String topic);
    }
}
