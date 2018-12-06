package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.InterestsAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.contract.InterestContract;
import com.iyoyogo.android.presenter.InterestPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LikePrefencesActivity extends BaseActivity<InterestContract.Presenter> implements InterestContract.View {


    @BindView(R.id.close_btn)
    ImageButton closeBtn;
    @BindView(R.id.layout_interest)
    RelativeLayout layoutInterest;
    @BindView(R.id.tv_least)
    TextView tvLeast;
    @BindView(R.id.gv_interest)
    GridView gv_interest;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    private int[] ids = new int[]{};
    private boolean isVisible = false;
    private List<Integer> idList;
    private ArrayList<String> interestList;
    private Integer[] array;
    private int size;
    private List<InterestBean.DataBean.ListBean> data;
    private String user_token;
    private String user_id;
    private int type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_like_prefences;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        idList = new ArrayList<>();
        interestList = new ArrayList<>();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);

        user_id = SpUtils.getString(LikePrefencesActivity.this, "user_id", null);
        user_token = SpUtils.getString(LikePrefencesActivity.this, "user_token", null);
        Log.d("LikePrefencesActivity", user_id);
        Log.d("LikePrefencesActivity", user_token);
        mPresenter.getInterestSign(user_id, user_token);


    }

    @Override
    protected InterestContract.Presenter createPresenter() {
        return new InterestPresenter(this);
    }

    @Override
    public void loadDataSuccess(List<InterestBean.DataBean.ListBean> list) {

        data = new ArrayList<>();
        data.addAll(list);
//        recyclerInterest.setLayoutManager(new GridLayoutManager(this, 4));
        InterestsAdapter interestAdapter = new InterestsAdapter(LikePrefencesActivity.this, data);
        gv_interest.setAdapter(interestAdapter);
        gv_interest.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        gv_interest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SparseBooleanArray checkedItemPositions = gv_interest.getCheckedItemPositions();
                boolean isChecked = checkedItemPositions.get(position);
                Toast.makeText(LikePrefencesActivity.this, "item " + position + " isChecked=" + isChecked, Toast.LENGTH_SHORT).show();
                idList.add(data.get(position).getId());
                interestList.add(data.get(position).getInterest());
                size = idList.size();
                array = idList.toArray(new Integer[size]);

                for (int i = 0; i < array.length; i++) {
                    Log.d("LikePrefencesActivity", "array[i]:" + array[i]);
                }
            }
        });
    }

    public Integer[] ifRepeat(Integer[] arr) {
        //用来记录去除重复之后的数组长度和给临时数组作为下标索引
        int t = 0;
        //临时数组
        Object[] tempArr = new Object[arr.length];
        //遍历原数组
        for (int i = 0; i < arr.length; i++) {
            //声明一个标记，并每次重置
            boolean isTrue = true;
            //内层循环将原数组的元素逐个对比
            for (int j = i + 1; j < arr.length; j++) {
                //如果发现有重复元素，改变标记状态并结束当次内层循环
                if (arr[i] == arr[j]) {
                    isTrue = false;
                    break;
                }
            }
            //判断标记是否被改变，如果没被改变就是没有重复元素
            if (isTrue) {
                //没有元素就将原数组的元素赋给临时数组
                tempArr[t] = arr[i];
                //走到这里证明当前元素没有重复，那么记录自增
                t++;
            }
        }
        //声明需要返回的数组，这个才是去重后的数组
        Integer[] newArr = new Integer[t];
        //用arraycopy方法将刚才去重的数组拷贝到新数组并返回
        System.arraycopy(tempArr, 0, newArr, 0, t);
        return newArr;
    }

    @Override
    public void addInterestSuccess() {
        startActivity(new Intent(LikePrefencesActivity.this, MainActivity.class));
        finish();
        Log.d("LikePrefencesActivity", "添加成功");
    }


    @OnClick({R.id.close_btn, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_btn:
                finish();
                break;

            case R.id.confirm_btn:
                if (type == 6) {
                    for (int i = 0; i < interestList.size(); i++) {
                        //外循环是循环的次数
                        for (int j = interestList.size() - 1; j > i; j--) {
                            //内循环是 外循环一次比较的次数
                            if (interestList.get(i) == interestList.get(j)) {
                                interestList.remove(j);
                            }

                        }
                    }
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("interestList", interestList);
                    for (int i = 0; i < interestList.size(); i++) {
                        Log.d("LikePrefencesActivity", "interestList" + interestList.get(i));
                    }
                    setResult(4, intent);
                    finish();
                } else {
                    if (array.length < 3 && array == null) {
                        Toast.makeText(this, "亲，兴趣少于三条哦", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("LikePrefencesActivity", "array.length:" + array.length);
                        Integer[] objects = (Integer[]) ifRepeat(array);
                        for (int i = 0; i < objects.length; i++) {
                            Log.d("LikePrefencesActivity", "objects[i]:" + objects[i]);
                        }
                        mPresenter.addInterest(objects, user_id, user_token);
                    }
                }
                break;
        }
    }
}
