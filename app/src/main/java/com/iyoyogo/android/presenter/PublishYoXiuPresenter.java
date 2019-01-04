package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.PublishYoXiuBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoXiuContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class PublishYoXiuPresenter extends BasePresenter<PublishYoXiuContract.View> implements PublishYoXiuContract.Presenter {
    public PublishYoXiuPresenter(PublishYoXiuContract.View mView) {
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
    public void publishYoXiu(String user_id,
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
                             String filter_id,String size) {
        DataManager.getFromRemote().publish_yoXiu(user_id, user_token, yo_id, file_path, file_type, file_desc, channel_ids, open, valid, position_name, position_areas, position_address, position_city, lng, lat, filter_id, size)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                                mView.publishYoXiuSuccess();
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void getYoXiuData(String user_id, String user_token, int yo_id) {
        DataManager.getFromRemote()
                .getYoXiuData(user_id, user_token, yo_id+"")
                .subscribe(new ApiObserver<PublishYoXiuBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(PublishYoXiuBean publishYoXiuBean) {
                        mView.onYoXiuData(publishYoXiuBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
