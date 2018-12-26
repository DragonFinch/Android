package com.iyoyogo.android.contract;

import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.base.IBaseView;
import com.iyoyogo.android.bean.collection.AddCollectionBean1;
import com.iyoyogo.android.bean.collection.AddressBookBean;

public interface AddressBookContract {

    interface View extends IBaseView {
        void getAddressBookContractSuccess(AddressBookBean addressBookBean);
    }

    interface Presenter extends IBasePresenter {

        void getAddressBookContract(String user_id, String user_token, String search, String list);
    }
}
