package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.publish.MessageBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoJiContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class PublishYoJiPresenter extends BasePresenter<PublishYoJiContract.View> implements PublishYoJiContract.Presenter {
    public PublishYoJiPresenter(PublishYoJiContract.View mView) {
        super(mView);
    }

    @Override
    public void getRecommendTopic(String user_id, String user_token) {
        DataManager.getFromRemote()
                .getRecommend(user_id, user_token)
                .subscribe(new ApiObserver<HotTopicBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(HotTopicBean hotTopicBean) {
                        List<HotTopicBean.DataBean.ListBean> list = hotTopicBean.getData().getList();
                        if (list != null) {
                            mView.getRecommendTopicSuccess(list);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void publishYoJi(String user_id, String user_token, int yo_id, String logo, String title, String desc, int cost, int open, int valid, List<Integer> topic_ids, List<Integer> channel_ids, List<MessageBean> list) {
        DataManager.getFromRemote()
                .publishYoJi(user_id, user_token, yo_id, logo, title, desc, cost, open, valid, topic_ids, channel_ids, list)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.publishYoJiSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
