package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.setting.MineSettingBean;
import com.iyoyogo.android.contract.MineSettingContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MineSettingPresenter extends BasePresenter<MineSettingContract.View> implements MineSettingContract.Presenter {
    public MineSettingPresenter(Context context, MineSettingContract.View mView) {
        super(context,mView);
    }

    @Override
    public void logout(Context context,String user_id, String user_token, String addr, String phone_info, String app_version) {
        DataManager.getFromRemote()
                .logout(context,user_id, user_token, addr, phone_info, app_version)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.logoutSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void getMineSetting(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getMineSetting(context,user_id, user_token)
                .subscribe(new ApiObserver<MineSettingBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MineSettingBean mineSettingBean) {
                        MineSettingBean.DataBean data = mineSettingBean.getData();
                        if (data != null) {
                            if(mView!=null)
                            mView.getMineSettingSuccess(data);
                        }

                    }


                });
    }

    @Override
    public void setMineSetting(Context context,String user_id, String user_token, int wifi_auto_play_video, int notice, int address_list) {
        DataManager.getFromRemote()
                .setMineSetting(context,user_id, user_token, wifi_auto_play_video, notice, address_list)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        if(mView!=null)
                        mView.setMineSettingSuccess(baseBean);

                    }

                });
    }
}
