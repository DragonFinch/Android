package com.iyoyogo.android.presenter;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.contract.FeedBackContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class FeedBackPresenter extends BasePresenter<FeedBackContract.View> implements FeedBackContract.Presenter {
    public FeedBackPresenter(FeedBackContract.View mView) {
        super(mView);
    }

    @Override
    public void addFeedBack(String user_id, String user_token, String desc) {
        DataManager.getFromRemote().feedBack(user_id,user_token,desc)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.addFeedBackSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        return true;
                    }
                });
    }
}
