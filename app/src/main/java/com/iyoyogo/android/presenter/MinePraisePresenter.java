package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.PraiseBean;
import com.iyoyogo.android.contract.MinePraiseContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class MinePraisePresenter extends BasePresenter<MinePraiseContract.View> implements MinePraiseContract.Presenter {
    public MinePraisePresenter(Context context,MinePraiseContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getPraise(Context context,String user_id, String user_token, int page, int page_size) {
        DataManager.getFromRemote()
        .getPraise(context,user_id,user_token,page,page_size)
        .subscribe(new ApiObserver<PraiseBean>(mView,this) {
            @Override
            protected void doOnSuccess(PraiseBean praiseBean) {
                List<PraiseBean.DataBean.ListBean> list = praiseBean.getData().getList();
                if (list!=null){
                    mView.getPraiseSuccess(praiseBean);
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
