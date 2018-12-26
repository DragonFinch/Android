package com.iyoyogo.android.ui.mine.homepage;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddressBookAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.AddressBookPhoneInfoBean;
import com.iyoyogo.android.contract.AddressBookContract;
import com.iyoyogo.android.presenter.AddressBookPresenter;
import com.iyoyogo.android.utils.SpUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressBookFriendsActivity extends BaseActivity<AddressBookContract.Presenter> implements AddressBookContract.View {

    @BindView(R.id.address_book_search)
    EditText addressBookSearch;
    @BindView(R.id.address_book_RecyclerView)
    RecyclerView addressBookRecyclerView;
    private String name;
    private String number;
    private String json;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_book_friends;
    }

    @Override
    protected AddressBookContract.Presenter createPresenter() {
        return new AddressBookPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(this, "user_id", null);
        String user_token = SpUtils.getString(this, "user_token", null);
        String search = addressBookSearch.getText().toString().trim();
        //读取通讯录好友
        getPhoneNumber();
        mPresenter.getAddressBookContract(user_id, user_token, search, json);
    }

    private void getPhoneNumber() {
        List<AddressBookPhoneInfoBean> list = new ArrayList<AddressBookPhoneInfoBean>();
        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            AddressBookPhoneInfoBean phoneInfo = new AddressBookPhoneInfoBean(name, number);
            list.add(phoneInfo);
        }
        json = new Gson().toJson(list);
    }

    @Override
    public void getAddressBookContractSuccess(AddressBookBean addressBookBean) {
        List<AddressBookBean.DataBean.ListBean> list = addressBookBean.getData().getList();
        AddressBookAdapter adapter = new AddressBookAdapter(R.layout.item_add_book_friends, list);
        addressBookRecyclerView.setAdapter(adapter);
        addressBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
