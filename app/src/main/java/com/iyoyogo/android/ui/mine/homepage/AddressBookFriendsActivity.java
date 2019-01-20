package com.iyoyogo.android.ui.mine.homepage;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.AddressBookAdapter;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.collection.AddressBookBean;
import com.iyoyogo.android.bean.collection.AddressBookPhoneInfoBean;
import com.iyoyogo.android.contract.AddressBookContract;
import com.iyoyogo.android.presenter.AddressBookPresenter;
import com.iyoyogo.android.ui.common.MainActivity;
import com.iyoyogo.android.utils.ContentUtil;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressBookFriendsActivity extends BaseActivity<AddressBookContract.Presenter> implements AddressBookContract.View {
    /**
     *
     */
    @BindView(R.id.address_book_search)
    EditText addressBookSearch;
    @BindView(R.id.address_book_RecyclerView)
    RecyclerView addressBookRecyclerView;
    @BindView(R.id.lin_Search)
    LinearLayout linSearch;
    private String name;
    private String number;
    private String json;
    private List<AddressBookBean.DataBean.ListBean> list;
    private List<AddressBookPhoneInfoBean> mlist = new ArrayList<>();
    private AddressBookAdapter adapter;
    String user_id;
    String user_token;
    String search;
    TextView btu_guanzhu;
    private int REQUEST_PERMISSION_TIMES = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_book_friends;
    }

    @Override
    protected void initView() {
        super.initView();
//        statusbar();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

    }

    @Override
    protected AddressBookContract.Presenter createPresenter() {
        return new AddressBookPresenter(AddressBookFriendsActivity.this,this);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.READ_CONTACTS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        user_id = SpUtils.getString(this, "user_id", null);
        user_token = SpUtils.getString(this, "user_token", null);
        search = addressBookSearch.getText().toString();
        //读取通讯录好友
        getPhoneNumber();
        mPresenter.getAddressBookContract(AddressBookFriendsActivity.this,user_id, user_token, search, json);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 110){
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                getPhoneNumber();
            }else{
                Toast.makeText(this,"您没有授权读取通讯录好友~",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getPhoneNumber() {
        if (REQUEST_PERMISSION_TIMES >=2){
            return;
        }else{
            REQUEST_PERMISSION_TIMES++;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)) {
            //需要授权
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 110);
        }else{
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
                mlist.add(phoneInfo);
            }
            json = new Gson().toJson(mlist);
        }
    }

    @Override
    public void getAddressBookContractSuccess(AddressBookBean addressBookBean) {
        list = addressBookBean.getData().getList();
        adapter = new AddressBookAdapter(R.layout.item_add_book_friends, list);
        addressBookRecyclerView.setAdapter(adapter);
        addressBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                btu_guanzhu = view.findViewById(R.id.btu_guanzhu);
                int user_id1 = list.get(position).getUser_id();
                if (user_id1 == 0) {
                    try{
                        ContentUtil.sendSmsWithBody(AddressBookFriendsActivity.this, list.get(position).getPhone(), Constants.BASE_URL+ "index.php/home/share/download_all.html");
                    }catch (ActivityNotFoundException a){
                        a.getMessage();
                    }
                }else {
                    mPresenter.addAttention(AddressBookFriendsActivity.this,user_id, user_token, list.get(position).getUser_id() + "");
                }
            }
        });
    }

    @Override
    public void addAttentionSuccess(AttentionBean attentionBean) {
        int status = attentionBean.getData().getStatus();
        if (status == 0) {//未关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_collection);
            btu_guanzhu.setText("+关注");
            btu_guanzhu.setTextColor(Color.parseColor("#ffffff"));
        }
        if (status == 1) {//已关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("已关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888888"));
        }
        if (status == 2) {//互相关注
            btu_guanzhu.setBackgroundResource(R.drawable.bg_delete_yoji);
            btu_guanzhu.setText("互相关注");
            btu_guanzhu.setTextColor(Color.parseColor("#888888"));
        }
    }


    @OnClick(R.id.message_center_back_im_id)
    public void onViewClicked() {
        finish();
    }

    @OnClick(R.id.lin_Search)
    public void Search() {
        mPresenter.getAddressBookContract(AddressBookFriendsActivity.this,user_id, user_token, addressBookSearch.getText().toString(), json);
    }

    @Override
    protected void onDestroy() {
        REQUEST_PERMISSION_TIMES=0;
        super.onDestroy();
    }
}
