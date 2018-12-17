package com.iyoyogo.android.ui.home.yoji;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.widget.FlowGroupView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseSignActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.ll_indicate_one)
    LinearLayout llIndicateOne;
    @BindView(R.id.flow_one)
    FlowGroupView flowOne;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.ll_indicate_two)
    LinearLayout llIndicateTwo;
    @BindView(R.id.flow_two)
    FlowGroupView flowTwo;
    @BindView(R.id.view_third)
    View viewThird;
    @BindView(R.id.ll_indicate_third)
    LinearLayout llIndicateThird;
    @BindView(R.id.flow_third)
    FlowGroupView flowThird;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private TextView child;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_sign;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("选择标签");
        createComplete.setText(getResources().getString(R.string.str_confirm));
    }

    private void initEvents(final TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                //话题内容
//                topic.setObjectText(tv.getText().toString());
//                editEdittextId.setObject(topic);// 设置话题
                Toast.makeText(ChooseSignActivity.this, tv.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
        List<String> mList = new ArrayList<>();
        mList.add("吃货时代");
        mList.add("扎啤");
        mList.add("海边表白胜地打卡");
        mList.add("海鲜饭");
        mList.add("防晒");
        List<String> mList1 = new ArrayList<>();
        List<String> mList2 = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {

            addTextView(mList.get(i),flowOne);
//            child.setTextColor();
        }
    }

    private void addTextView(String str,FlowGroupView flowGroupView) {
        child = new TextView(this);
        child.setCompoundDrawablePadding(4);


        ViewGroup.MarginLayoutParams params =
                new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(15, 15, 15, 15);
        child.setLayoutParams(params);
        child.setBackgroundResource(R.drawable.fillet_style);
        child.setText(str);
        child.setTextColor(Color.BLACK);
        initEvents(child);//监听
        flowGroupView.addView(child);

    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.back_icon, R.id.create_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.create_complete:

                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
