package com.iyoyogo.android.presenter;

import android.content.Context;
import android.widget.Toast;

import com.iyoyogo.android.app.App;
import com.iyoyogo.android.base.BasePresenter;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.contract.AddressBookContract;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.net.ApiObserver;

public class AddressBookPresenter extends BasePresenter<AddressBookContract.View> implements AddressBookContract.Presenter {

    public AddressBookPresenter(Context context,AddressBookContract.View mView) {
        super(context,mView);
    }

    @Override
    public void getAddressBookContract(Context context,String user_id, String user_token, String search, String list) {
        DataManager.getFromRemote().setAddressBook(context,user_id, user_token, search, list)
                .subscribe(new ApiObserver<AddressBookBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AddressBookBean addressBookBean) {
                        mView.getAddressBookContractSuccess(addressBookBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
//                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }

    @Override
    public void addAttention(Context context,String user_id, String user_token, String target_id) {
        DataManager.getFromRemote().addAttention1(context,user_id, user_token, target_id)
                .subscribe(new ApiObserver<AttentionBean>(mView, this) {
                    @Override
                    protected void doOnSuccess(AttentionBean attentionBean) {
                        mView.addAttentionSuccess(attentionBean);
                    }

                    @Override
                    protected boolean doOnFailure(int code, String message) {
                        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
    }
}
