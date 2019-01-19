package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.contract.ChannelContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class ChannelPresenter extends BasePresenter<ChannelContract.View> implements ChannelContract.Presenter {
    public ChannelPresenter(Context context,ChannelContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getChannel(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getChannel(context,user_id, user_token)
                .subscribe(new ApiObserver<ChannelBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(ChannelBean channelBean) {
                        List<ChannelBean.DataBean.ListBean> list = channelBean.getData().getList();
                        if (list!=null){
                            mView.getChannelSuccess(list);
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
