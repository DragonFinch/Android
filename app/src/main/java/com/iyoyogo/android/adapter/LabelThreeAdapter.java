package com.iyoyogo.android.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.yoji.label.LabelListBean;
import com.iyoyogo.android.view.AddLabelDialog;
import com.iyoyogo.android.widget.flow.FlowLayout;
import com.iyoyogo.android.widget.flow.TagAdapter;
import com.iyoyogo.android.widget.flow.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
/**
 * 标签选择我的专属
 */
public class LabelThreeAdapter extends PagerAdapter {
    private Context mContext;
    private List<LabelListBean.DataBean.List3Bean> labels;
    public TagAdapter<LabelListBean.DataBean.List3Bean> tagAdapter;
    static ArrayList<LabelListBean.DataBean.List3Bean> mSelectImg = new ArrayList<>();

    //值得做自定义：AC  防坑值得做BC 我的专属C
    public LabelThreeAdapter(Context mContext, List<LabelListBean.DataBean.List3Bean> labels) {
        this.mContext = mContext;
        this.labels = labels;

    }

    public ArrayList<LabelListBean.DataBean.List3Bean> selectSign() {
        if (!mSelectImg.isEmpty()) {
            return mSelectImg;
        }
        return null;
    }

    public void setLabels(List<LabelListBean.DataBean.List3Bean> labels) {
        this.labels = labels;
        tagAdapter.notifyDataChanged();
//        notifyDataSetChanged();
    }

    // 显示多少个页面
    @Override
    public int getCount() {
        return labels == null ? 0 : labels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 初始化显示的条目对象
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_lable, null);
        final TagFlowLayout tagFlowLayout = view.findViewById(R.id.tab_flowLayout);
        if (tagAdapter == null) {
            tagAdapter = new TagAdapter<LabelListBean.DataBean.List3Bean>(labels) {
                @Override
                public View getView(FlowLayout parent, int position, LabelListBean.DataBean.List3Bean list3Bean) {

                    View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_label_three, tagFlowLayout, false);
                    TextView tv = contentView.findViewById(R.id.lable_name_tv);
                   /* if (labels.get(position).getType().equals("A")) {
                        tv.setBackgroundResource(R.drawable.label_bg_deserve_to_do);
                        tv.setTextColor(mContext.getResources().getColor(R.color.orgeen_color));
                    } else if (labels.get(position).getTag_type().equals("B")) {

                        tv.setBackgroundResource(R.drawable.label_bg_fkzn);
                        tv.setTextColor(mContext.getResources().getColor(R.color.blue_color));
                    } else if (labels.get(position).getTag_type().equals("C")) {
                        tv.setBackgroundResource(R.drawable.label_bg_deserve_to_do);
                        tv.setTextColor(mContext.getResources().getColor(R.color.black));
                    }*/

                    if (list3Bean.isSelect()) {
                        contentView.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                    } else {
                        contentView.findViewById(R.id.img_choice).setVisibility(View.GONE);
                    }
                    tv.setText(list3Bean.getLabel());
                    return contentView;
                }
            };
            tagFlowLayout.setAdapter(tagAdapter);
        } else {
            tagAdapter.notifyDataChanged();
        }
        tagFlowLayout.setOnTagClickListener((view1, tag_position, parent) -> {

            if (labels.size() - 1 == tag_position) {
//                showAddTagLabelDialog(position, labels.get(position).getTag_type(), labels.get(position).getStoreTag());
                return true;
            }
            if (labels.get(tag_position).isSelect()) {
                view1.findViewById(R.id.img_choice).setVisibility(View.GONE);
                labels.get(tag_position).setSelect(false);
                mSelectImg.remove(labels.get(tag_position));
            } else {
                view1.findViewById(R.id.img_choice).setVisibility(View.VISIBLE);
                labels.get(tag_position).setSelect(true);
                mSelectImg.add(labels.get(tag_position));
            }
            return true;
        });


        container.addView(view);
        return view;
    }

    AddLabelDialog addLabelDialog;

    /**
     * 添加标签弹窗
     */
    /*private void showAddTagLabelDialog(int postion, String tagType, List<LabelInfoBean.ListBean.StoreTagBean> list) {
        if (null == addLabelDialog) {
            addLabelDialog = new AddLabelDialog(mContext);
        }
        addLabelDialog.setLabelDialogCallback(new AddLabelDialog.AddLabelDialogCallback() {
            @Override
            public void onConfirm(String content) {

                LabelInfoBean.ListBean.StoreTagBean storeTagBean = new LabelInfoBean.ListBean.StoreTagBean();
                storeTagBean.setTag_name(content);
                if ("A".equals(tagType)) {
                    customType = "AC";
                } else if ("B".equals(tagType)) {
                    customType = "BC";
                } else if ("C".equals(tagType)) {
                    customType = "C";
                }
                storeTagBean.setTag_type(customType);
                list.add(list.size() - 1, storeTagBean);
                LabelInfoBean.ListBean listBean = labels.get(postion);
                listBean.setStoreTag(list);
                labels.set(postion, listBean);
                tagAdapter.notifyDataChanged();
            }

            @Override
            public void onCancel() {

            }
        });
        addLabelDialog.show();
    }*/

    // 销毁条目对象
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void refresh() {
        tagAdapter.notifyDataChanged();
    }
}
