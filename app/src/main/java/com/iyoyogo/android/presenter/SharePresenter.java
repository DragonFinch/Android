package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.AboutMeBean;
import com.iyoyogo.android.contract.AboutMeContract;
import com.iyoyogo.android.contract.ShareContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;
import com.umeng.socialize.media.Base;

public class SharePresenter extends BasePresenter<ShareContract.View> implements ShareContract.Presenter {


    public SharePresenter(Context context, ShareContract.View mView) {
        super(context,mView);
    }


    @Override
    public void share(Context context,String user_id, String user_token, String id) {
        DataManager.getFromRemote().share(context,user_id,user_token,id)
                .subscribe(new ApiObserver<BaseBean>(mView,this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                            mView.onShareSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
