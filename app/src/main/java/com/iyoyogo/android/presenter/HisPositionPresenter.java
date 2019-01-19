package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.HisPositionBean;
import com.iyoyogo.android.contract.HisPositionContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class HisPositionPresenter extends BasePresenter<HisPositionContract.View> implements HisPositionContract.Presenter {
    public HisPositionPresenter(Context context,HisPositionContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getHisPosition(Context context,String user_id, String user_token, int page, int page_size) {
        DataManager.getFromRemote().setHisPosition(context,user_id, user_token,page,page_size)
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
    public void DelPosition(Context context,String user_id, String user_token) {
        DataManager.getFromRemote().DelPosition(context,user_id, user_token)
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
