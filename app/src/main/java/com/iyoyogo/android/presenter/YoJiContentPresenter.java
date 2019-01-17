package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.contract.YoJiContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoJiContentPresenter extends BasePresenter<YoJiContentContract.View> implements YoJiContentContract.Presenter {
    public YoJiContentPresenter(YoJiContentContract.View mView) {
        super(mView);
    }

    @Override
    public void getYoJiContent(String user_id, String user_token, String his_id, String page, String page_size) {
        DataManager.getFromRemote()
                .getYoJiContent(user_id,user_token,his_id,page,page_size)
                .subscribe(new ApiObserver<YoJiContentBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(YoJiContentBean yoJiContentBean) {
                        YoJiContentBean.DataBean data = yoJiContentBean.getData();
                        if (data!=null&&mView!=null){
                            mView.getYoJiContentSuccess(data);
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
