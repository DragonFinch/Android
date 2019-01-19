package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoxiu.TypeBean;
import com.iyoyogo.android.contract.CreatePointContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class CreatePointPresenter extends BasePresenter<CreatePointContract.View> implements CreatePointContract.Presenter {
    public CreatePointPresenter(Context context,CreatePointContract.View mView) {
        super(context,mView);
    }

    @Override
    public void setType(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getType(context,user_id,user_token)
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

    @Override
    public void createPoint(Context context,String user_id, String user_token, String name, String en_name, String areas, String address, String lng, String lat, String type_id) {
            DataManager.getFromRemote()
                    .create_point(context,user_id,user_token,name,en_name,areas,address,lng,lat,type_id)
                    .subscribe(new ApiObserver<BaseBean>(mView,this) {
                        @Override
                        protected void doOnSuccess(BaseBean baseBean) {
                            mView.createPointSuccess();
                        }
                    });
    }
}
