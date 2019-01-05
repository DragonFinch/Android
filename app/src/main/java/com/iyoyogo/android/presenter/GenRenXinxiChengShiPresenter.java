package com.iyoyogo.android.presenter;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.contract.GeRenChengShiContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class GenRenXinxiChengShiPresenter extends BasePresenter<GeRenChengShiContract.View> implements GeRenChengShiContract.Presenter {

    public GenRenXinxiChengShiPresenter(GeRenChengShiContract.View mView) {
        super(mView);
    }


    @Override
    public void aboutMe(String user_id, String user_token, String type, String search) {
        DataManager.getFromRemote().chengShi(user_id,user_token,type,search)
                .subscribe(new ApiObserver<MapBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MapBean mapBean) {
                        mView.aboutMeSuccess(mapBean);
                    }
                });
    }
}
