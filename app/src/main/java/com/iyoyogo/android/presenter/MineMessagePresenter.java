package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.MineMessageBean;
import com.iyoyogo.android.contract.MineContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MineMessagePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    public MineMessagePresenter(Context context, MineContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getUserInfo(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getPersonInfo(context,user_id, user_token)
                .subscribe(new ApiObserver<MineMessageBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MineMessageBean mineMessageBean) {
                        MineMessageBean.DataBean data = mineMessageBean.getData();
                        if (data != null) {
                            mView.getUserInfoSuccess(data);
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
    public void punchClock(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .punchClock(context,user_id, user_token)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {

                        mView.punchClockSuccess(baseBean);

                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
