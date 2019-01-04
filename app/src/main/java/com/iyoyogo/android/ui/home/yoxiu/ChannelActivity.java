package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ChannelAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.contract.ChannelContract;
import com.iyoyogo.android.presenter.ChannelPresenter;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GridSpacingItemDecoration;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.iyoyogo.android.app.App.context;

/**
 * 频道
 */
public class
ChannelActivity extends BaseActivity<ChannelContract.Presenter> implements ChannelContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.gv_channel)
    RecyclerView gv_channel;
    private String user_id;
    private String user_token;
    private int size;
    private Integer[] array;
    private List<Integer> idList;
    private ArrayList<String> channelList;
    private ChannelAdapter adapter;
    private int type;
    private TextView tv_message;
    private TextView tv_message_two;
    private TextView tv_message_three;
    private ImageView img_tip;
    private PopupWindow popup;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        idList = new ArrayList<>();
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        channelList = new ArrayList<>();
        createComplete.setText("确定");
        tvTitle.setText("选择频道");
        user_id = SpUtils.getString(ChannelActivity.this, "user_id", null);
        user_token = SpUtils.getString(ChannelActivity.this, "user_token", null);
        mPresenter.getChannel(user_id, user_token);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected ChannelContract.Presenter createPresenter() {
        return new ChannelPresenter(this);
    }

    @Override
    public void getChannelSuccess(List<ChannelBean.DataBean.ListBean> list) {
        adapter = new ChannelAdapter(ChannelActivity.this, list);
        int spanCount = 4; // 3 columns
        int spacing = 24; // 50px
        boolean includeEdge = false;
        gv_channel.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        MyChooseCallback callback = new MyChooseCallback();
        MyOnItemClickListener listener = new MyOnItemClickListener();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        adapter.setMaxNum(5);
        adapter.setChooseCallback(callback);
        adapter.setOnRecyclerViewItemClickListener(listener);
        gv_channel.setLayoutManager(layoutManager);
        gv_channel.setAdapter(adapter);
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

    @OnClick({R.id.back, R.id.create_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.create_complete:
                Intent intent = new Intent();

                ArrayList<String> integerList = adapter.selectPhoto();
                for (int i = 0; i < integerList.size(); i++) {
                    Log.d("ChannelActivity", integerList.get(i));
                }
                ArrayList<String> strings = adapter.selectChannel();
                if (integerList!=null){
                    int size = integerList.size();
                    Integer[] channel_ids=new Integer[size];
                    for (int i = 0; i <integerList.size() ; i++) {
                        channel_ids[i]=Integer.parseInt(integerList.get(i));
                    }
                    for (int i = 0; i < channel_ids.length; i++) {
                        Log.d("channel_ids", "channel_ids[i]:" + channel_ids[i]);
                    }
//                Integer[] integers = integerList.toArray(new Integer[size]);
                    int[] channel_array = new int[integerList.size()];
                    for (int i = 0; i < integerList.size(); i++) {
                        channel_array[i]=channel_ids[i];
                        Log.d("channel_array", "channel_array[i]:" + channel_array[i]);
                    }
                    intent.putExtra("channel_array", channel_ids);
                    intent.putStringArrayListExtra("channel_list", strings);
               /* for (int i = 0; i < integers.length; i++) {
                    channel_array[i] = integers[i];
                }
                for (int i = 0; i < strings.size(); i++) {
                    Log.d("ChannelActivity", "channel_array[i]:" + strings.get(i));
                }

                Log.d("ChannelActivity", "list.size():" + strings.size());

                Log.d("Test", listToString(strings));*/
                    if (type == 1) {
                        setResult(1, intent);
                    } else {
                        setResult(66, intent);
                    }


                    finish();
                }

                break;
        }
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
    public void report() {
        tv_message.setText("请为你的yo·秀");
        tv_message.setTextColor(Color.parseColor("#333333"));
        tv_message_two.setTextColor(Color.parseColor("#333333"));
        tv_message_three.setTextColor(Color.parseColor("#333333"));
        img_tip.setImageResource(R.mipmap.stamp_report);
        tv_message_two.setText("选择1-5个「频道」");
        tv_message_three.setText("让更多小伙伴看到哦~");
        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口

        backgroundAlpha(0.6f);

        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
        popup.showAtLocation(findViewById(R.id.activity_channel), Gravity.CENTER, 0, 0);
    }
    public void initPopup() {
        View view = LayoutInflater.from(ChannelActivity.this).inflate(R.layout.like_layout, null);
        popup = new PopupWindow(view, DensityUtil.dp2px(context, 300), DensityUtil.dp2px(context, 145), true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable());
        tv_message = view.findViewById(R.id.tv_message);
        tv_message_two = view.findViewById(R.id.tv_message_two);

        tv_message_three = view.findViewById(R.id.tv_message_three);
        img_tip = view.findViewById(R.id.tip_img);

        popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口


        //添加pop窗口关闭事件
        popup.setOnDismissListener(new poponDismissListener());
    }
    public String listToString(ArrayList<String> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<String> strings = adapter.selectPhoto();
        if (strings!=null){
            strings.clear();

        }
    }

    public ArrayList removeDuplicate(ArrayList list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public <T> List<T> removeNull(List<? extends T> oldList) {
        // 临时集合
        List<T> listTemp = new ArrayList();
        for (int i = 0; i < oldList.size(); i++) {
            // 保存不为空的元素
            if (oldList.get(i) != null) {
                listTemp.add(oldList.get(i));
            }
        }
        return listTemp;
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
            initPopup();
            report();
        }

    }
}
