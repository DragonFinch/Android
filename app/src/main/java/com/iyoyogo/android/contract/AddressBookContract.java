package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.collection.AddressBookBean;
/**
 * 的契约类
 */
public interface AddressBookContract {

    interface View extends IBaseView {
        void getAddressBookContractSuccess(AddressBookBean addressBookBean);
    }

    interface Presenter extends IBasePresenter {

        void getAddressBookContract(String user_id, String user_token, String search, String list);
    }
}
