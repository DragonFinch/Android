package com.iyoyogo.android.presenter;

import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.contract.DraftContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

import java.util.List;

public class DraftPresenter extends BasePresenter<DraftContract.View> implements DraftContract.Presenter {
    public DraftPresenter(DraftContract.View mView) {
        super(mView);
    }

    @Override
    public void getDraft(String user_id, String user_token, int page, int page_size) {
        DataManager.getFromRemote().getDraft(user_id,user_token,page,page_size)
        .subscribe(new ApiObserver<DraftBean>(mView, this) {
            @Override
            protected void doOnSuccess(DraftBean draftBean) {
                List<DraftBean.DataBean.ListBean> list = draftBean.getData().getList();
                if (list!=null){
                    mView.getDraftSuccess(list);
                }
            }

            @Override
            protected boolean doOnFailure(int code, String message) {
                Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                return true;
            }
        })    ;
    }
}
