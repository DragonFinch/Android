package com.iyoyogo.android.presenter;

import android.util.Log;

import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.contract.MapContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class MapPresenter extends BasePresenter<MapContract.View> implements MapContract.Presenter {


    public MapPresenter(MapContract.View mView) {
        super(mView);
    }

    @Override
    public void aboutMe(String user_id, String user_token, String type, String search) {
        DataManager.getFromRemote()
                .mapDiTu(user_id,user_token,type,search)
                .subscribe(new ApiObserver<MapBean>(mView, this) {
                  @Override
                  protected void doOnSuccess(MapBean mapBean) {
                      mView.map(mapBean);
                      Log.e("asdazxc", "doOnSuccess: "+mapBean );
                  }
              })  ;
    }


}
