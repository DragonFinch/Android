package com.iyoyogo.android.contract;

import android.content.Context;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddressBookBean;

/**
 * 的契约类
 */
public interface AddressBookContract {

    interface View extends IBaseView {
        void getAddressBookContractSuccess(AddressBookBean addressBookBean);

        void addAttentionSuccess(AttentionBean attentionBean);

    }

    interface Presenter extends IBasePresenter {

        void getAddressBookContract(Context context,String user_id, String user_token, String search, String list);

        void addAttention(Context context,String user_id, String user_token, String target_id);
    }
}
