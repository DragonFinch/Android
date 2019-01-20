package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.contract.YoXiuContentContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class YoXiuContentPresenter extends BasePresenter<YoXiuContentContract.View> implements YoXiuContentContract.Presenter {
    public YoXiuContentPresenter(Context context, YoXiuContentContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getYoXiuContent(Context context,String user_id, String user_token, String his_id, String page, String page_size) {
        DataManager.getFromRemote()
                .getYoXiuContent(context,user_id,user_token,his_id,page,page_size)
                .subscribe(new ApiObserver<YoXiuContentBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(YoXiuContentBean yoXiuContentBean) {
                        YoXiuContentBean.DataBean data = yoXiuContentBean.getData();
                        if (data!=null){
                            mView.getYoXiuContentSuccess(data);
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
