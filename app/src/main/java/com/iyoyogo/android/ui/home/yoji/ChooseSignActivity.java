package com.iyoyogo.android.ui.home.yoji;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.Bean;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.yoji.label.AddLabelBean;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.contract.ChooseSignContract;
import com.iyoyogo.android.presenter.ChooseSignPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.view.flowlayout.JLHorizontalScrollView;
import com.iyoyogo.android.widget.flow.TagAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择标签
 */
public class ChooseSignActivity extends BaseActivity<ChooseSignContract.Presenter> implements ChooseSignContract.View {


    @BindView(R.id.back)
    ImageView              back;
    @BindView(R.id.tv_title)
    TextView               tvTitle;
    @BindView(R.id.create_complete)
    TextView               createComplete;
    @BindView(R.id.make_worth_img)
    ImageView              makeWorthImg;
    @BindView(R.id.add_make_worth)
    TextView               addMakeWorth;
    @BindView(R.id.jhs)
    JLHorizontalScrollView jhs;
    @BindView(R.id.ll)
    LinearLayout           ll;
    @BindView(R.id.pit_guide_img)
    ImageView              pitGuideImg;
    @BindView(R.id.add_pit_guide)
    TextView               addPitGuide;
    @BindView(R.id.jhs1)
    JLHorizontalScrollView jhs1;
    @BindView(R.id.ll1)
    LinearLayout           ll1;
    @BindView(R.id.exclusive_mine_img)
    ImageView              exclusiveMineImg;
    @BindView(R.id.add_exclusive_mine)
    TextView               addExclusiveMine;
    @BindView(R.id.jhs2)
    JLHorizontalScrollView jhs2;
    @BindView(R.id.ll2)
    LinearLayout           ll2;
    @BindView(R.id.activity_choose_sign)
    LinearLayout           activityChooseSign;
    private String      user_id;
    private String      user_token;
    private PopupWindow popup;
    private TextView    tv_type;
    private EditText    edit_label;

    TagAdapter<LabelListBean.DataBean.List1Bean> tagAdapter1;
    TagAdapter<LabelListBean.DataBean.List2Bean> tagAdapter2;
    TagAdapter<LabelListBean.DataBean.List3Bean> tagAdapter3;
    private static ArrayList<LabelListBean.DataBean.List1Bean> mSelectImg  = new ArrayList<>();
    private static ArrayList<LabelListBean.DataBean.List2Bean> mSelectImg1 = new ArrayList<>();
    private static ArrayList<LabelListBean.DataBean.List3Bean> mSelectImg2 = new ArrayList<>();

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
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);


        tvTitle.setText("选择标签");
        createComplete.setText(getResources().getString(R.string.str_confirm));

    }

    private void initPopup(int type, String message) {
        View view = getLayoutInflater().inflate(R.layout.popup_add_label, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(ChooseSignActivity.this, 300), DensityUtil.dp2px(ChooseSignActivity.this, 205), true);
        popup.setBackgroundDrawable(new ColorDrawable());

        edit_label = view.findViewById(R.id.edit_label);
        tv_type = view.findViewById(R.id.tv_type);
        ImageView img_cancel  = view.findViewById(R.id.img_cancel);
        Button    btn_commit  = view.findViewById(R.id.btn_commit);
        Button    btn_canecel = view.findViewById(R.id.btn_cancel);
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
//                labelAdapter.refresh();
//                labelTwoAdapter.refresh();
//                labelThreeAdapter.refresh();
                popup.dismiss();
                mPresenter.getLabelList(user_id, user_token);
//                labelAdapter.notifyDataSetChanged();
//                labelTwoAdapter.notifyDataSetChanged();
//                labelThreeAdapter.notifyDataSetChanged();
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

                List<Bean> strings = jhs.getData();
                List<Bean> strings1 = jhs1.getData();
                List<Bean> strings2 = jhs2.getData();
                ArrayList<Bean> list = new ArrayList<>();

                if (strings != null && strings1 != null && strings2 != null) {
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
                } else {
                    if (strings == null && strings1 == null && strings2 == null) {
                        Toast.makeText(this, "请至少选择一个标签", Toast.LENGTH_SHORT).show();
                    }
                    //判断1和2是空的就添加3
                    else if (strings == null && strings1 == null) {
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

                    } else
                        // //判断1和3是空的就添加2
                        if (strings == null && strings2 == null) {
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
                        } else if (strings1 == null && strings2 == null) {
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
                        } else
                            //判断1是空的
                            if (strings == null) {
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
                            } else
                                // 判断2是空的
                                if (strings1 == null) {
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
                                } else
                                    // 判断3是空的
                                    if (strings2 == null) {
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
                Collections.reverse(list);
                bundle.putSerializable("sign_list", list);
                if (list.size() > 10) {
                    Toast.makeText(this, "标签最多选择10个", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtras(bundle);
                    intent.putExtra("type", 1);
                    Log.d("ChooseSignActivity", "list.size():" + list.size());
                    setResult(55, intent);
                    finish();
                }

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

    public ArrayList<LabelListBean.DataBean.List1Bean> selectSign1() {

        if (!mSelectImg.isEmpty()) {
            return mSelectImg;
        }
        return null;
    }

    public ArrayList<LabelListBean.DataBean.List2Bean> selectSign2() {

        if (!mSelectImg1.isEmpty()) {
            return mSelectImg1;
        }
        return null;
    }

    public ArrayList<LabelListBean.DataBean.List3Bean> selectSign3() {

        if (!mSelectImg2.isEmpty()) {
            return mSelectImg2;
        }
        return null;
    }

    @Override
    public void getLabelListSuccess(LabelListBean.DataBean data) {
        ArrayList<Integer>                     select = getIntent().getIntegerArrayListExtra("data");
        List<Bean>                             mList1 = new ArrayList<>();
        List<Bean>                             mList2 = new ArrayList<>();
        List<Bean>                             mList3 = new ArrayList<>();
        List<LabelListBean.DataBean.List1Bean> list1  = data.getList1();
        List<LabelListBean.DataBean.List2Bean> list2  = data.getList2();
        List<LabelListBean.DataBean.List3Bean> list3  = data.getList3();
        for (int i = 0; i < list1.size(); i++) {
            Bean bean = new Bean();
            bean.setLabel(list1.get(i).getLabel());
            bean.setLabel_id((list1.get(i).getLabel_id()));
            bean.setLogo(list1.get(i).getLogo());
            bean.setType(list1.get(i).getType());
            bean.setUser_id(list1.get(i).getUser_id());
            if (select != null) {
                for (Integer id : select) {
                    if (id == list1.get(i).getLabel_id()) {
                        bean.setSelect(true);
                        break;
                    }
                }
            } else {
                bean.setSelect(list1.get(i).isSelect());
            }
            mList1.add(bean);
        }
        jhs.setData(mList1, new JLHorizontalScrollView.OnCompleteCallback() {
            @Override
            public void onComplete(int count) {
                ll.removeAllViews();
                for (int i = 0; i < count; i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RadioButton               radioButton  = new RadioButton(ChooseSignActivity.this);
                    layoutParams.leftMargin = 20;
                    radioButton.setButtonDrawable(R.drawable.radiobutton_selector);
                    radioButton.setLayoutParams(layoutParams);
                    radioButton.setClickable(false);
                    if (i == 0) {
                        radioButton.setChecked(true);
                    }
                    ll.addView(radioButton);
                }
            }

            @Override
            public void onScroll(int index) {
                for (int i = 0; i < ll.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) ll.getChildAt(i);
                    if (i == index) {
                        radioButton.setChecked(true);
                    } else {
                        radioButton.setChecked(false);
                    }
                }
            }
        });
        for (int i = 0; i < list2.size(); i++) {
            Bean bean = new Bean();
            bean.setLabel(list2.get(i).getLabel());
            bean.setLabel_id((list2.get(i).getLabel_id()));
            bean.setLogo(list2.get(i).getLogo());
            bean.setType(list2.get(i).getType());
            bean.setUser_id(list2.get(i).getUser_id());
            if (select != null) {
                for (Integer id : select) {
                    if (id == list2.get(i).getLabel_id()) {
                        bean.setSelect(true);
                        break;
                    }
                }
            } else {
                bean.setSelect(list2.get(i).isSelect());
            }
            mList2.add(bean);
        }
        jhs1.setData(mList2, new JLHorizontalScrollView.OnCompleteCallback() {
            @Override
            public void onComplete(int count) {
                ll1.removeAllViews();
                for (int i = 0; i < count; i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RadioButton               radioButton  = new RadioButton(ChooseSignActivity.this);
                    layoutParams.leftMargin = 20;
                    radioButton.setButtonDrawable(R.drawable.radiobutton_two_selector);
                    radioButton.setLayoutParams(layoutParams);
                    radioButton.setClickable(false);
                    if (i == 0) {
                        radioButton.setChecked(true);
                    }
                    ll1.addView(radioButton);
                }
            }

            @Override
            public void onScroll(int index) {
                for (int i = 0; i < ll1.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) ll1.getChildAt(i);
                    if (i == index) {
                        radioButton.setChecked(true);
                    } else {
                        radioButton.setChecked(false);
                    }
                }
            }
        });
        for (int i = 0; i < list3.size(); i++) {
            Bean bean = new Bean();
            bean.setLabel(list3.get(i).getLabel());
            bean.setLabel_id((list3.get(i).getLabel_id()));
            bean.setLogo(list3.get(i).getLogo());
            bean.setType(list3.get(i).getType());
            bean.setUser_id(list3.get(i).getUser_id());
            if (select != null) {
                for (Integer id : select) {
                    if (id == list3.get(i).getLabel_id()) {
                        bean.setSelect(true);
                        break;
                    }
                }
            } else {
                bean.setSelect(list3.get(i).isSelect());
            }
            mList3.add(bean);
        }
        jhs2.setData(mList3, new JLHorizontalScrollView.OnCompleteCallback() {
            @Override
            public void onComplete(int count) {
                ll2.removeAllViews();
                for (int i = 0; i < count; i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    RadioButton               radioButton  = new RadioButton(ChooseSignActivity.this);
                    layoutParams.leftMargin = 20;
                    radioButton.setButtonDrawable(R.drawable.radiobutton_three_selector);
                    radioButton.setLayoutParams(layoutParams);
                    radioButton.setClickable(false);
                    if (i == 0) {
                        radioButton.setChecked(true);
                    }
                    ll2.addView(radioButton);
                }
            }

            @Override
            public void onScroll(int index) {
                for (int i = 0; i < ll2.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) ll2.getChildAt(i);
                    if (i == index) {
                        radioButton.setChecked(true);
                    } else {
                        radioButton.setChecked(false);
                    }
                }
            }
        });
       /* jhs.setOnClickListener(new JLHorizontalScrollView.OnClickListeners() {
            @Override
            public void setOnDeleteClick(View v, int position) {
                mPresenter.deleteLabel(user_id, user_token, mList.get(position).getLabel_id());
            }

            @Override
            public void setOnAddClick(View v, int position) {

            }
        });*/

//            View view = getLayoutInflater().inflate(R.layout.item_viewpager_lable, null);
//            final TagFlowLayout tagFlowLayout = view.findViewById(R.id.tab_flowLayout);


    /*    tagAdapter1 = new TagAdapter<LabelListBean.DataBean.List1Bean>(list1) {

            @Override
            public View getView(FlowLayout parent, int position, LabelListBean.DataBean.List1Bean list1Bean) {

                View contentView = getLayoutInflater().inflate(R.layout.item_label, flowOne, false);
                TextView tv = contentView.findViewById(R.id.lable_name_tv);
                ImageView img_delete = contentView.findViewById(R.id.img_delete);
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        img_delete.setVisibility(View.VISIBLE);
                        return true;
                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list1.get(position).isSelect()) {
                            contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                            list1.get(position).setSelect(false);
                            mSelectImg.remove(list1.get(position));
                        } else {
                            contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                            list1.get(position).setSelect(true);
                            mSelectImg.add(list1.get(position));
                        }
                    }
                });

                contentView.findViewById(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteLabel(user_id,user_token,list1.get(position).getLabel_id());
                    }
                });
                if (list1Bean.isSelect()) {
                    contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                } else {
                    contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                }
                tv.setText(list1Bean.getLabel());
                return contentView;
            }
        };
        flowOne.setAdapter(tagAdapter1);


        tagAdapter2 = new TagAdapter<LabelListBean.DataBean.List2Bean>(list2) {
            @Override
            public View getView(FlowLayout parent, int position, LabelListBean.DataBean.List2Bean list2Bean) {

                View contentView = getLayoutInflater().inflate(R.layout.item_label_two, flowTwo, false);
                TextView tv = contentView.findViewById(R.id.lable_name_tv);
                ImageView img_delete = contentView.findViewById(R.id.img_delete);
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        img_delete.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list2.get(position).isSelect()) {
                            contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                            list2.get(position).setSelect(false);
                            mSelectImg1.remove(list2.get(position));
                        } else {
                            contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                            list2.get(position).setSelect(true);
                            mSelectImg1.add(list2.get(position));
                        }
                    }
                });
                contentView.findViewById(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteLabel(user_id,user_token,list2.get(position).getLabel_id());
                    }
                });
                if (list2Bean.isSelect()) {
                    contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                } else {
                    contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                }
                tv.setText(list2Bean.getLabel());
                return contentView;
            }
        };
        flowTwo.setAdapter(tagAdapter2);

        tagAdapter3 = new TagAdapter<LabelListBean.DataBean.List3Bean>(list3) {
            @Override
            public View getView(FlowLayout parent, int position, LabelListBean.DataBean.List3Bean list3Bean) {

                View contentView = getLayoutInflater().inflate(R.layout.item_label_three, flowTwo, false);
                TextView tv = contentView.findViewById(R.id.lable_name_tv);
                ImageView img_delete = contentView.findViewById(R.id.img_delete);
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        img_delete.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list3.get(position).isSelect()) {
                            contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                            list3.get(position).setSelect(false);
                            mSelectImg2.remove(list3.get(position));
                        } else {
                            contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                            list3.get(position).setSelect(true);
                            mSelectImg2.add(list3.get(position));
                        }
                    }
                });
                contentView.findViewById(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteLabel(user_id,user_token,list3.get(position).getLabel_id());
                    }
                });
                if (list3Bean.isSelect()) {
                    contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                } else {
                    contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                }
                tv.setText(list3Bean.getLabel());
                return contentView;
            }
        };
        flowThird.setAdapter(tagAdapter3);*/


    }

    @Override
    public void addLabelSuccess(AddLabelBean.DataBean data) {
        mPresenter.getLabelList(user_id, user_token);
    }

    @Override
    public void deleteLabelSuccess(BaseBean baseBean) {
        mPresenter.getLabelList(user_id, user_token);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<LabelListBean.DataBean.List1Bean> list1Beans = selectSign1();
        ArrayList<LabelListBean.DataBean.List2Bean> list2Beans = selectSign2();
        ArrayList<LabelListBean.DataBean.List3Bean> list3Beans = selectSign3();
        if (list1Beans != null) {
            list1Beans.clear();

        }
        if (list2Beans != null) {
            list2Beans.clear();

        }
        if (list3Beans != null) {
            list3Beans.clear();

        }
    }
}
