package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoxiu.topic.CreateTopicBean;
import com.iyoyogo.android.bean.yoxiu.topic.HotTopicBean;
import com.iyoyogo.android.contract.MoreTopicContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class MoreTopicPresenter extends BasePresenter<MoreTopicContract.View> implements MoreTopicContract.Presenter {
    public MoreTopicPresenter(MoreTopicContract.View mView) {
        super(mView);
    }

    @Override
    public void getHotTopic(String user_id, String user_token, String search) {
        DataManager.getFromRemote()
                .getHotTopic(user_id,user_token,search)
                .subscribe(new ApiObserver<HotTopicBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(HotTopicBean hotTopicBean) {
                        List<HotTopicBean.DataBean.ListBean> list = hotTopicBean.getData().getList();
                        if (list!=null){
                            mView.getHotTopicSuccess(list);
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
    public void getNearTopic(String user_id, String user_token) {
        DataManager.getFromRemote()
                .getNearTopic(user_id,user_token)
                .subscribe(new ApiObserver<HotTopicBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(HotTopicBean nearTopicBean) {
                        List<HotTopicBean.DataBean.ListBean> list = nearTopicBean.getData().getList();
                        if (list!=null){
                            mView.getNearTopicSuccess(list);
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
    public void getClearTopic(String user_id, String user_token) {
        DataManager.getFromRemote()
                .clearTopic(user_id,user_token)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.getClearTopicSuccess();
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;

                    }
                });
    }

    @Override
    public void getCreateTopic(String user_id, String user_token,String topic) {
        DataManager.getFromRemote()
                .createTopic(user_id,user_token,topic)
                .subscribe(new ApiObserver<CreateTopicBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(CreateTopicBean createTopicBean) {
                        CreateTopicBean.DataBean data = createTopicBean.getData();
                        if (data!=null){
                            mView.createTopicSuccess(data);
                        }
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;

                    }
                });
    }
}
