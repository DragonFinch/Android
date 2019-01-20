package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.PublishSucessBean;
import com.iyoyogo.android.bean.yoji.publish.PublishYoJiBean;
import com.iyoyogo.android.bean.yoxiu.PublishYoXiuBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.PublishYoXiuContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;
import com.iyoyogo.android.view.LoadingDialog;

import java.util.List;

public class PublishYoXiuPresenter extends BasePresenter<PublishYoXiuContract.View> implements PublishYoXiuContract.Presenter {
    public PublishYoXiuPresenter(Context context, PublishYoXiuContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getRecommendTopic(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getRecommend(context,user_id, user_token)
                .subscribe(new ApiObserver<HotTopicBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(HotTopicBean hotTopicBean) {
                        List<HotTopicBean.DataBean.ListBean> list = hotTopicBean.getData().getList();
                        if (list != null&&mView!=null) {
                            mView.getRecommendTopicSuccess(list);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        if (mView!=null) {
                            Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                            mView.onError(message);
                        }
                        return true;
                    }
                });
    }

    @Override
    public void publishYoXiu(Context context,String user_id,
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
        DataManager.getFromRemote().publish_yoXiu(context,user_id, user_token, yo_id, file_path, file_type, file_desc, channel_ids, open, valid, position_name, position_areas, position_address, position_city, lng, lat, filter_id, size)
                .subscribe(new ApiObserver<PublishSucessBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(PublishSucessBean baseBean) {
                                mView.publishYoXiuSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        mView.onError(message);
                        return true;
                    }
                });
    }

    @Override
    public void getYoXiuData(Context context,String user_id, String user_token, int yo_id) {
        DataManager.getFromRemote()
                .getYoXiuData(context,user_id, user_token, yo_id+"")
                .subscribe(new ApiObserver<PublishYoXiuBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(PublishYoXiuBean publishYoXiuBean) {
                        mView.onYoXiuData(publishYoXiuBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        mView.onError(message);
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
