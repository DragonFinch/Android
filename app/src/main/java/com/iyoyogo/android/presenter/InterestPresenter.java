package com.iyoyogo.android.presenter;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.contract.InterestContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.ArrayList;
import java.util.List;

public class InterestPresenter extends BasePresenter<InterestContract.View> implements InterestContract.Presenter {


    public InterestPresenter(Context context,InterestContract.View mView) {
        super(context,mView);
    }



    @Override
    public void getInterestSign(Context context, String user_id, String user_token) {
        DataManager.getFromRemote()
                .getInterestSign(context,user_id,user_token)
                .subscribe(new ApiObserver<InterestBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(InterestBean interestBean) {
                        List<InterestBean.DataBean.ListBean> list = interestBean.getData().getList();
                        if (list!=null){
                            if (mView!=null)
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
    public void addInterest(Context context,ArrayList<Integer> ids, String user_id, String user_token) {
        DataManager.getFromRemote()
                .addInterest(context,ids,user_id,user_token)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        if (mView!=null)
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
