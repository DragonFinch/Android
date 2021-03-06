package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.PublishSucessBean;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.PublishYoXiuBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;

import java.util.List;
/**
 * 发布yo秀的契约类
 */
public interface PublishYoXiuContract {
    interface View extends IBaseView {

        void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list);

        void publishYoXiuSuccess(PublishSucessBean data);

        void onYoXiuData(PublishYoXiuBean data);

        void onError(String message);
    }

    interface Presenter extends IBasePresenter {

        void getRecommendTopic(Context context, String user_id, String user_token);

        void publishYoXiu(Context context, String user_id,
                          String user_token,
                          int yo_id,
                          String file_path,
                          int file_type,
                          String file_desc,
                          String channel_ids,
                          int open,
                          int valid,
                          String position_name,
                          String position_areas,
                          String position_address,
                          String position_city,
                          String lng,
                          String lat,
                          String filter_id,String size);

        void getYoXiuData(Context context, String user_id,
                         String user_token,
                         int yo_id);
    }
}
