package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;
import com.iyoyogo.android.adapter.LabelAdapter;
import com.iyoyogo.android.adapter.LabelThreeAdapter;
import com.iyoyogo.android.adapter.LabelTwoAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.contract.ChooseSignContract;
import com.iyoyogo.android.presenter.ChooseSignPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseSignActivity extends BaseActivity<ChooseSignContract.Presenter> implements ChooseSignContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.make_worth_img)
    ImageView makeWorthImg;
    @BindView(R.id.add_make_worth)
    TextView addMakeWorth;
    @BindView(R.id.vp_one)
    ViewPager vpOne;
    @BindView(R.id.pit_guide_img)
    ImageView pitGuideImg;
    @BindView(R.id.add_pit_guide)
    TextView addPitGuide;
    @BindView(R.id.vp_two)
    ViewPager vpTwo;
    @BindView(R.id.exclusive_mine_img)
    ImageView exclusiveMineImg;
    @BindView(R.id.add_exclusive_mine)
    TextView addExclusiveMine;
    @BindView(R.id.vp_third)
    ViewPager vpThird;
    @BindView(R.id.activity_choose_sign)
    LinearLayout activityChooseSign;
    private String user_id;
    private String user_token;
    private PopupWindow popup;
    private TextView tv_type;
    private EditText edit_label;
    private LabelAdapter labelAdapter;
    private LabelTwoAdapter labelTwoAdapter;
    private LabelThreeAdapter labelThreeAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_sign;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        user_id = SpUtils.getString(ChooseSignActivity.this, "user_id", null);
        user_token = SpUtils.getString(ChooseSignActivity.this, "user_token", null);
        mPresenter.getLabelList(user_id, user_token);

    }

    @Override
    protected void initView() {
        super.initView();

        tvTitle.setText("选择标签");
        createComplete.setText(getResources().getString(R.string.str_confirm));

    }

    private void initPopup(int type, String message) {
        View view = getLayoutInflater().inflate(R.layout.popup_add_label, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(ChooseSignActivity.this, 300), DensityUtil.dp2px(ChooseSignActivity.this, 205), true);
        popup.setBackgroundDrawable(new ColorDrawable());

        edit_label = view.findViewById(R.id.edit_label);
        tv_type = view.findViewById(R.id.tv_type);
        ImageView img_cancel = view.findViewById(R.id.img_cancel);
        Button btn_commit = view.findViewById(R.id.btn_commit);
        Button btn_canecel = view.findViewById(R.id.btn_cancel);
        tv_type.setText(message);
        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addLabel(user_id, user_token, 0, type, edit_label.getText().toString().trim());
              /*  labelThreeAdapter.tagAdapter.notifyDataChanged();
                labelTwoAdapter.tagAdapter.notifyDataChanged();*/
                labelAdapter.refresh();
                labelTwoAdapter.refresh();
                labelThreeAdapter.refresh();
                popup.dismiss();
            }
        });
        btn_canecel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        backgroundAlpha(0.6f);
        popup.setOnDismissListener(new poponDismissListener());
    }


    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act 是上下文context

    }


    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
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

    }

//    private void addTextView(String str, FlowGroupView flowGroupView) {
//        child = new TextView(this);
//        child.setCompoundDrawablePadding(4);
//
//
//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                        DensityUtil.dp2px(ChooseSignActivity.this, 30));
//        params.setMargins(15, 15, 15, 15);
//        child.setLayoutParams(params);
//        child.setGravity(Gravity.CENTER);
//        child.setBackgroundResource(R.drawable.fillet_style);
//        child.setText(str);
//        child.setTextColor(Color.BLACK);
//        initEvents(child);//监听
//        flowGroupView.addView(child);
//        child.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(ChooseSignActivity.this, "长按事件", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//
//    }

    @Override
    protected ChooseSignContract.Presenter createPresenter() {
        return new ChooseSignPresenter(this);
    }

    @OnClick({R.id.back, R.id.create_complete, R.id.add_make_worth, R.id.add_pit_guide, R.id.add_exclusive_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.create_complete:
                ArrayList<LabelListBean.DataBean.List1Bean> strings = labelAdapter.selectSign();
                ArrayList<LabelListBean.DataBean.List2Bean> strings1 = labelTwoAdapter.selectSign();
                ArrayList<LabelListBean.DataBean.List3Bean> strings2 = labelThreeAdapter.selectSign();
                ArrayList<Bean> list = new ArrayList<>();

               if (strings!=null&&strings1!=null&&strings2!=null){
                    for (int i = 0; i < strings1.size(); i++) {
                        Bean bean = new Bean();
                        bean.setLabel(strings1.get(i).getLabel());
                        bean.setLabel_id((strings1.get(i).getLabel_id()));
                        bean.setLogo(strings1.get(i).getLogo());
                        bean.setType(strings1.get(i).getType());
                        bean.setUser_id(strings1.get(i).getUser_id());
                        bean.setSelect(strings1.get(i).isSelect());
                        list.add(bean);
                    }
                    for (int i = 0; i < strings2.size(); i++) {
                        Bean bean = new Bean();
                        bean.setLabel(strings2.get(i).getLabel());
                        bean.setLabel_id((strings2.get(i).getLabel_id()));
                        bean.setLogo(strings2.get(i).getLogo());
                        bean.setType(strings2.get(i).getType());
                        bean.setUser_id(strings2.get(i).getUser_id());
                        bean.setSelect(strings2.get(i).isSelect());
                        list.add(bean);
                    }
                    for (int i = 0; i < strings.size(); i++) {
                        Bean bean = new Bean();
                        bean.setLabel(strings.get(i).getLabel());
                        bean.setLabel_id((strings.get(i).getLabel_id()));
                        bean.setLogo(strings.get(i).getLogo());
                        bean.setType(strings.get(i).getType());
                        bean.setUser_id(strings.get(i).getUser_id());
                        bean.setSelect(strings.get(i).isSelect());
                        list.add(bean);
                    }
                }else {
                       //判断1和2是空的就添加3
                    if (strings==null&&strings1==null){
                        for (int i = 0; i < strings2.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings2.get(i).getLabel());
                            bean.setLabel_id((strings2.get(i).getLabel_id()));
                            bean.setLogo(strings2.get(i).getLogo());
                            bean.setType(strings2.get(i).getType());
                            bean.setUser_id(strings2.get(i).getUser_id());
                            bean.setSelect(strings2.get(i).isSelect());
                            list.add(bean);
                        }

                    }else
                    // //判断1和3是空的就添加2
                    if (strings==null&&strings2==null){
                        for (int i = 0; i < strings1.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings1.get(i).getLabel());
                            bean.setLabel_id((strings1.get(i).getLabel_id()));
                            bean.setLogo(strings1.get(i).getLogo());
                            bean.setType(strings1.get(i).getType());
                            bean.setUser_id(strings1.get(i).getUser_id());
                            bean.setSelect(strings1.get(i).isSelect());
                            list.add(bean);
                        }
                    }else
                    if (strings1==null&&strings2==null){
                    //判断2和3是空的，就添加1
                        for (int i = 0; i < strings.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings.get(i).getLabel());
                            bean.setLabel_id((strings.get(i).getLabel_id()));
                            bean.setLogo(strings.get(i).getLogo());
                            bean.setType(strings.get(i).getType());
                            bean.setUser_id(strings.get(i).getUser_id());
                            bean.setSelect(strings.get(i).isSelect());
                            list.add(bean);
                        }
                    }else
                    //判断1是空的
                    if (strings==null){
                        for (int i = 0; i < strings1.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings1.get(i).getLabel());
                            bean.setLabel_id((strings1.get(i).getLabel_id()));
                            bean.setLogo(strings1.get(i).getLogo());
                            bean.setType(strings1.get(i).getType());
                            bean.setUser_id(strings1.get(i).getUser_id());
                            bean.setSelect(strings1.get(i).isSelect());
                            list.add(bean);
                        }
                        for (int i = 0; i < strings2.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings2.get(i).getLabel());
                            bean.setLabel_id((strings2.get(i).getLabel_id()));
                            bean.setLogo(strings2.get(i).getLogo());
                            bean.setType(strings2.get(i).getType());
                            bean.setUser_id(strings2.get(i).getUser_id());
                            bean.setSelect(strings2.get(i).isSelect());
                            list.add(bean);
                        }
                    }else
                    // 判断2是空的
                    if (strings1==null){
                        for (int i = 0; i < strings.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings.get(i).getLabel());
                            bean.setLabel_id((strings.get(i).getLabel_id()));
                            bean.setLogo(strings.get(i).getLogo());
                            bean.setType(strings.get(i).getType());
                            bean.setUser_id(strings.get(i).getUser_id());
                            bean.setSelect(strings.get(i).isSelect());
                            list.add(bean);
                        }
                        for (int i = 0; i < strings2.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings2.get(i).getLabel());
                            bean.setLabel_id((strings2.get(i).getLabel_id()));
                            bean.setLogo(strings2.get(i).getLogo());
                            bean.setType(strings2.get(i).getType());
                            bean.setUser_id(strings2.get(i).getUser_id());
                            bean.setSelect(strings2.get(i).isSelect());
                            list.add(bean);
                        }
                    }else
                    // 判断3是空的
                    if (strings2==null){
                        for (int i = 0; i < strings.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings.get(i).getLabel());
                            bean.setLabel_id((strings.get(i).getLabel_id()));
                            bean.setLogo(strings.get(i).getLogo());
                            bean.setType(strings.get(i).getType());
                            bean.setUser_id(strings.get(i).getUser_id());
                            bean.setSelect(strings.get(i).isSelect());
                            list.add(bean);
                        }
                        for (int i = 0; i < strings1.size(); i++) {
                            Bean bean = new Bean();
                            bean.setLabel(strings1.get(i).getLabel());
                            bean.setLabel_id((strings1.get(i).getLabel_id()));
                            bean.setLogo(strings1.get(i).getLogo());
                            bean.setType(strings1.get(i).getType());
                            bean.setUser_id(strings1.get(i).getUser_id());
                            bean.setSelect(strings1.get(i).isSelect());
                            list.add(bean);
                        }
                    }
                }

                Intent intent = new Intent();

                for (int i = 0; i < list.size(); i++) {

                }

                for (int i = 0; i < list.size(); i++) {
                    Log.d("ChooseSignActivity", list.get(i).getLabel());
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("sign_list", list);
                intent.putExtras(bundle);
                Log.d("ChooseSignActivity", "list.size():" + list.size());
                setResult(55, intent);
                finish();
                break;
            case R.id.add_make_worth:
                initPopup(1, "什么值得做");
                popup.showAtLocation(findViewById(R.id.activity_choose_sign), Gravity.CENTER, 0, 0);
                break;
            case R.id.add_pit_guide:
                initPopup(2, "防坑指南");
                popup.showAtLocation(findViewById(R.id.activity_choose_sign), Gravity.CENTER, 0, 0);
                break;
            case R.id.add_exclusive_mine:
                initPopup(3, "我的专属");
                popup.showAtLocation(findViewById(R.id.activity_choose_sign), Gravity.CENTER, 0, 0);
                break;
        }
    }


    @Override
    public void getLabelListSuccess(LabelListBean.DataBean data) {

        List<LabelListBean.DataBean.List1Bean> list1 = data.getList1();
        List<LabelListBean.DataBean.List2Bean> list2 = data.getList2();
        List<LabelListBean.DataBean.List3Bean> list3 = data.getList3();


        if (labelAdapter == null) {
            labelAdapter = new LabelAdapter(ChooseSignActivity.this, list1);
            vpOne.setAdapter(labelAdapter);
        } else {
            labelAdapter.setLabels(list1);
        }
        if (labelTwoAdapter == null) {
            labelTwoAdapter = new LabelTwoAdapter(ChooseSignActivity.this, list2);
            vpTwo.setAdapter(labelTwoAdapter);
        } else {
            labelTwoAdapter.setLabels(list2);
        }
        if (labelThreeAdapter == null) {
            labelThreeAdapter = new LabelThreeAdapter(ChooseSignActivity.this, list3);
            vpThird.setAdapter(labelThreeAdapter);
        } else {
            labelThreeAdapter.setLabels(list3);
        }
        labelAdapter.refresh();
    }

    @Override
    public void addLabelSuccess(AddLabelBean.DataBean data) {

    }

    @Override
    public void deleteLabelSuccess(BaseBean baseBean) {

    }


}
