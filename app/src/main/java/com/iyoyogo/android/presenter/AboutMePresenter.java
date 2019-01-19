package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.AboutMeBean;
import com.iyoyogo.android.contract.AboutMeContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class AboutMePresenter extends BasePresenter<AboutMeContract.View> implements AboutMeContract.Presenter {


    public AboutMePresenter(Context context,AboutMeContract.View mView) {
        super(context,mView);
    }

    @Override
    public void aboutMe() {
        DataManager.getFromRemote().aboutme()
                .subscribe(new ApiObserver<AboutMeBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(AboutMeBean aboutMeBean) {
                        AboutMeBean.DataBean data = aboutMeBean.getData();
                        if (data!=null){
                            mView.aboutMeSuccess(data);
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
