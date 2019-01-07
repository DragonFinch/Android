package com.iyoyogo.android.ui.mine.homepage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iyoyogo.android.Manifest;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddressBookAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.AddressBookPhoneInfoBean;
import com.iyoyogo.android.contract.AddressBookContract;
import com.iyoyogo.android.presenter.AddressBookPresenter;
import com.iyoyogo.android.ui.common.MainActivity;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddressBookFriendsActivity extends BaseActivity<AddressBookContract.Presenter> implements AddressBookContract.View {
    /**
     *
     */
    @BindView(R.id.address_book_search)
    EditText addressBookSearch;
    @BindView(R.id.address_book_RecyclerView)
    RecyclerView addressBookRecyclerView;
    private String name;
    private String number;
    private String json;
    private List<AddressBookBean.DataBean.ListBean> list;
    private AddressBookAdapter adapter;


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
//        读取通讯录好友
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
        list = addressBookBean.getData().getList();
        adapter = new AddressBookAdapter(R.layout.item_add_book_friends, list);
        addressBookRecyclerView.setAdapter(adapter);
        addressBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        //检测程序是否开启权限
//        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_CONTACTS) !=
//                PackageManager.PERMISSION_GRANTED) {//没有权限需要动态获取
//            //动态请求权限
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
//
//        } else {
//            adapter = new AddressBookAdapter(R.layout.item_add_book_friends, list);
//            addressBookRecyclerView.setAdapter(adapter);
//        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//判断是否给于权限
//                adapter.notifyDataSetChanged();
//                adapter = new AddressBookAdapter(R.layout.item_add_book_friends, list);
//
//            } else {
//                Toast.makeText(this, "请开启权限", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
