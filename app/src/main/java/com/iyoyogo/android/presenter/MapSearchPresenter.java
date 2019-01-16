package com.iyoyogo.android.presenter;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.bean.map.MapRenMei;
import com.iyoyogo.android.contract.MapSearchContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MapSearchPresenter extends BasePresenter<MapSearchContract.View> implements MapSearchContract.Presenter {


    public MapSearchPresenter(MapSearchContract.View mView) {
        super(mView);
    }

    @Override
    public void aboutMe(String user_id, String user_token, String type, String search) {
        DataManager.getFromRemote().CPS(user_id,user_token,type,search)
                .subscribe(new ApiObserver<MapBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(MapBean mapBean) {
                        mView.aboutMeSuccess(mapBean);
                    }
                });
    }

    @Override
    public void renMei(String user_id, String user_token) {
        DataManager.getFromRemote().remei(user_id,user_token)
                .subscribe(new ApiObserver<MapRenMei>(mView,this) {
                    @Override
                    protected void doOnSuccess(MapRenMei mapRenMei) {
                    mView.renMeiChengshi(mapRenMei);
                    }
                });
    }
}
