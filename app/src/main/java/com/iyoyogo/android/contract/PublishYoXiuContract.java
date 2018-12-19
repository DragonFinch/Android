package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;

import java.util.List;

public interface PublishYoXiuContract {
    interface View extends IBaseView {

        void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list);

        void publishYoXiuSuccess();
    }

    interface Presenter extends IBasePresenter {

        void getRecommendTopic(String user_id, String user_token);

        void publishYoXiu(String user_id,
                          String user_token,
                          int yo_id,
                          String file_path,
                          int file_type,
                          String file_desc,
                          int[] channel_ids,
                          Integer[] topic_ids,
                          int open,
                          int valid,
                          String position_name,
                          String position_areas,
                          String position_address,
                          String filter_id);
    }
}
