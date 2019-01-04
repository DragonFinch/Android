package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.HisPositionBean;
import com.iyoyogo.android.contract.HisPositionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class HisPositionPresenter extends BasePresenter<HisPositionContract.View> implements HisPositionContract.Presenter {
    public HisPositionPresenter(HisPositionContract.View mView) {
        super(mView);
    }

    @Override
    public void getHisPosition(String user_id, String user_token, int page, int page_size) {
        DataManager.getFromRemote().setHisPosition(user_id, user_token,page,page_size)
                .subscribe(new ApiObserver<HisPositionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(HisPositionBean hisPositionBean) {
                        mView.setHisPosition(hisPositionBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void DelPosition(String user_id, String user_token) {
        DataManager.getFromRemote().DelPosition(user_id, user_token)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean bean) {
                        mView.DelPosition(bean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
