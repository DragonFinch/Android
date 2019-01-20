package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.PublishSucessBean;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;

import java.util.List;
/**
 * 发布yo记的契约类
 */
public interface PublishYoJiContract {
    interface View extends IBaseView {
        void publishYoJiSuccess(PublishSucessBean baseBean);

        void getRecommendTopicSuccess(List<HotTopicBean.DataBean.ListBean> list);

        void onYoJiData(PublishYoJiBean data);

        void onError(String message);
    }

    interface Presenter extends IBasePresenter {
        void getRecommendTopic(Context context, String user_id, String user_token);

        void publishYoJi(Context context, String user_id,
                         String user_token,
                         int yo_id,
                         String logo,
                         String title,
                         String desc,
                         int cost,
                         int open,
                         int valid,
                         String channel_ids,
                         String json);

        void getYoJiData(Context context, String user_id,
                         String user_token,
                         int yo_id);
    }
}
