package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ChannelAdapter;
import com.iyoyogo.android.adapter.ChannelAdapter1;
import com.iyoyogo.android.adapter.InterestsAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.login.interest.InterestBean;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.contract.InterestContract;
import com.iyoyogo.android.presenter.InterestPresenter;
import com.iyoyogo.android.ui.home.yoxiu.ChannelActivity;
import com.iyoyogo.android.ui.mine.EditPersonalMessageActivity;
import com.iyoyogo.android.utils.GridSpacingItemDecoration;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 兴趣
 */
public class LikePrefencesActivity extends BaseActivity<InterestContract.Presenter> implements InterestContract.View {


    @BindView(R.id.close_btn)
    ImageButton closeBtn;
    @BindView(R.id.layout_interest)
    RelativeLayout layoutInterest;
    @BindView(R.id.tv_least)
    TextView tvLeast;
    @BindView(R.id.gv_interest)
    RecyclerView gv_interest;
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
    private ArrayList<Integer> chosenList = new ArrayList<>();
    private InterestsAdapter adapter;
    private ChannelAdapter1 adapter1;
    private ArrayList<String> integerList;
    private String[] strings;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_like_prefences;
    }

    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

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
        ArrayList<Integer> data = getIntent().getIntegerArrayListExtra("data");
        if (data != null && data.size() > 0) {
            for (InterestBean.DataBean.ListBean listBean : list) {
                for (Integer datum : data) {
                    if (datum == listBean.getId()) {
                        listBean.setChoose(true);
                    }
                }
            }
        }
        adapter1 = new ChannelAdapter1(LikePrefencesActivity.this, list);
        int spanCount = 4; // 3 columns
        int spacing = 24; // 50px
        boolean includeEdge = false;
        gv_interest.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        MyChooseCallback callback = new MyChooseCallback();
        MyOnItemClickListener listener = new MyOnItemClickListener();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        adapter1.setMaxNum(list.size());
        adapter1.setChooseCallback(callback);
        adapter1.setOnRecyclerViewItemClickListener(listener);
        gv_interest.setLayoutManager(layoutManager);
        gv_interest.setAdapter(adapter1);
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
        finish();
    }


    @OnClick({R.id.close_btn, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_btn:
                finish();
                break;
            case R.id.confirm_btn:
                Intent intent = new Intent(LikePrefencesActivity.this,EditPersonalMessageActivity.class);
                integerList = adapter1.selectPhoto();
                ArrayList<Integer> ids = adapter1.selectChannelIds();
                strings = integerList.toArray(new String[size]);
                for (int i = 0; i < strings.length; i++) {
                    Log.d("TAG", i + "");
                }
                intent.putIntegerArrayListExtra("channel_array", ids);
                intent.putStringArrayListExtra("channel_list", integerList);
                if (strings.length < 3) {
                    Toast.makeText(this, "亲，兴趣少于三条哦", Toast.LENGTH_SHORT).show();
                } else {
                    mPresenter.addInterest(ids, user_id, user_token);
                    setResult(4, intent);
                    finish();
                }
                break;
        }
    }

    /**
     * Item选则事件的监听类
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<String> strings = adapter1.selectPhoto();
        if (strings != null) {
            strings.clear();
        }
    }


    /**
     * Item点击事件的监听类
     */
    private class MyOnItemClickListener implements OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(int position) {

        }
    }

    /**
     * Item选则事件的监听类
     */
    private class MyChooseCallback implements OnItemChooseCallback {

        @Override
        public void chooseState(int position, boolean isChosen) {

        }

        @Override
        public void countNow(int countNow) {
//            btnContinue.setText("继续" + "（" + countNow + "）");
        }

        @Override
        public void countWarning(int count) {
//            Toast.makeText(ChannelActivity.this, "最多选择" + count + "张图片", Toast.LENGTH_SHORT).show();
//            initPopup();
//            report();
        }

    }

    //数量限制
    public void report() {
        Toast.makeText(this, "亲，兴趣少于三条哦", Toast.LENGTH_SHORT).show();
    }
}
