package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.home.HomeViewPagerBean;
import com.iyoyogo.android.contract.HomeContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    public HomePresenter(Context context, HomeContract.View mView) {
        super(context,mView);
    }

    @Override
    public void banner(Context context,String user_id,String user_token,String type,String city) {
        DataManager.getFromRemote()
                .homePager(context,user_id,user_token,type,city)
                .subscribe(new ApiObserver<HomeBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(HomeBean homeViewPagerBean) {
                        HomeBean.DataBean data = homeViewPagerBean.getData();
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
