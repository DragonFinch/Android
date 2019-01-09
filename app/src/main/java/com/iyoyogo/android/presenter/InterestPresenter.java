package com.iyoyogo.android.presenter;


import android.util.Log;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.contract.InterestContract;

import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class InterestPresenter extends BasePresenter<InterestContract.View> implements InterestContract.Presenter {


    public InterestPresenter(InterestContract.View mView) {
        super(mView);
    }



    @Override
    public void getInterestSign(String user_id,String user_token) {
        DataManager.getFromRemote()
                .getInterestSign(user_id,user_token)
                .subscribe(new ApiObserver<InterestBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(InterestBean interestBean) {
                        List<InterestBean.DataBean.ListBean> list = interestBean.getData().getList();
                        if (list!=null){
                            mView.loadDataSuccess(list);
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
    public void addInterest(String[] ids, String user_id, String user_token) {
        DataManager.getFromRemote()
                .addInterest(ids,user_id,user_token)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.addInterestSuccess();
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                     Log.d("InterestPresenter", message);
                        return true;
                    }
                });

    }
}
