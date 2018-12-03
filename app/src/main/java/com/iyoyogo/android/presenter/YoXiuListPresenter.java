package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.contract.YoXiuListContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoXiuListPresenter extends BasePresenter<YoXiuListContract.View> implements YoXiuListContract.Presenter {
    public YoXiuListPresenter(YoXiuListContract.View mView) {
        super(mView);
    }

    @Override
    public void getYoXiuList(String user_id, String user_token, int page) {
        DataManager.getFromRemote()
                .getYoXiuList(user_id,user_token,page)
                .subscribe(new ApiObserver<YouXiuListBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(YouXiuListBean youXiuListBean) {
                        YouXiuListBean.DataBean data = youXiuListBean.getData();
                        if (data!=null){
                           mView.getYoXiuListSuccess(data);
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
