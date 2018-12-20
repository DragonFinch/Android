package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;

import java.util.List;

public interface PublishYoJiContract {
    interface View extends IBaseView {
        void publishYoJiSuccess(BaseBean baseBean);

        void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list);

    }

    interface Presenter extends IBasePresenter {
        void getRecommendTopic(String user_id, String user_token);

        void publishYoJi(String user_id,
                         String user_token,
                         int yo_id,
                         String logo,
                         String title,
                         String desc,
                         int cost,
                         int open,
                         int valid,
                         List<Integer> topic_ids,
                         List<Integer> channel_ids,
                       String json);
    }
}
