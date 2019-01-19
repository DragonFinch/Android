package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.contract.ChooseSignContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class ChooseSignPresenter extends BasePresenter<ChooseSignContract.View> implements ChooseSignContract.Presenter {
    public ChooseSignPresenter(Context context,ChooseSignContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getLabelList(Context context,String user_id, String user_token) {
        DataManager.getFromRemote()
                .getLabelList(context,user_id, user_token)
                .subscribe(new ApiObserver<LabelListBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(LabelListBean labelListBean) {
                        LabelListBean.DataBean data = labelListBean.getData();
                        if (data != null) {
                            mView.getLabelListSuccess(data);
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
    public void addLabel(Context context,String user_id, String user_token, int label_id, int type, String label) {
        DataManager.getFromRemote()
                .addLabel(context,user_id, user_token, label_id, type, label)
                .subscribe(new ApiObserver<AddLabelBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AddLabelBean labelListBean) {
                        AddLabelBean.DataBean data = labelListBean.getData();
                        if (data != null) {
                            mView.addLabelSuccess(data);
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
    public void deleteLabel(Context context,String user_id, String user_token, int label_id) {
        DataManager.getFromRemote()
                .deleteLabel(context,user_id, user_token, label_id)
                .subscribe(new ApiObserver<BaseBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(BaseBean baseBean) {
                        mView.deleteLabelSuccess(baseBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
