package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.contract.CreatePointContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class CreatePointPresenter extends BasePresenter<CreatePointContract.View> implements CreatePointContract.Presenter {
    public CreatePointPresenter(CreatePointContract.View mView) {
        super(mView);
    }

    @Override
    public void setType(String user_id, String user_token) {
        DataManager.getFromRemote()
                .getType(user_id,user_token)
                .subscribe(new ApiObserver<TypeBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(TypeBean typeBean) {
                        TypeBean.DataBean data = typeBean.getData();
                        if (data!=null){
                            mView.setTypeSuccess(data);
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
