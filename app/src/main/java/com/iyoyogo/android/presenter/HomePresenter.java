package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.contract.HomeContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(HomeContract.View mView) {
        super(mView);
    }

    @Override
    public void banner(String user_id,String user_token,String type) {
        DataManager.getFromRemote()
                .homePager(user_id,user_token,type)
                .subscribe(new ApiObserver<HomeViewPagerBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(HomeViewPagerBean homeViewPagerBean) {
                        HomeViewPagerBean.DataBean data = homeViewPagerBean.getData();
                        if(data!=null){
                            mView.bannerSuccess(data);
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
