package com.iyoyogo.android.ui.home.yoxiu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.ChannelAdapter;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.yoxiu.channel.ChannelBean;
import com.iyoyogo.android.contract.ChannelContract;
import com.iyoyogo.android.presenter.ChannelPresenter;
import com.iyoyogo.android.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChannelActivity extends BaseActivity<ChannelContract.Presenter> implements ChannelContract.View {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.create_complete)
    TextView createComplete;
    @BindView(R.id.bar)
    RelativeLayout bar;
    @BindView(R.id.gv_channel)
    GridView gv_channel;
    private String user_id;
    private String user_token;
    private int size;
    private Integer[] array;
    private List<Integer> idList;
    private ArrayList<String> channelList;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        idList = new ArrayList<>();
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
    protected ChannelContract.Presenter createPresenter() {
        return new ChannelPresenter(this);
    }

    @Override
    public void getChannelSuccess(List<ChannelBean.DataBean.ListBean> list) {
        ChannelAdapter adapter = new ChannelAdapter(ChannelActivity.this, list);
        gv_channel.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        gv_channel.setAdapter(adapter);
        gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checkedItemPositions = gv_channel.getCheckedItemPositions();
                boolean isChecked = checkedItemPositions.get(position);
                Toast.makeText(ChannelActivity.this, "item " + position + " isChecked=" + isChecked, Toast.LENGTH_SHORT).show();
                        if (isChecked==true&&!idList.contains(list.get(position).getId())&&!channelList.contains(list.get(position).getChannel())){
                            idList.add(list.get(position).getId());
                            channelList.add(list.get(position).getChannel());
                        }else {
                            idList.remove(list.get(position).getId());
                            channelList.remove(list.get(position).getChannel());
                        }
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

    @OnClick({R.id.back, R.id.create_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.create_complete:
                Intent intent = new Intent();
                Integer[] objects = (Integer[]) ifRepeat(array);
                int[] channel_array = new int[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    channel_array[i] = objects[i];
                }
                for (int i = 0; i < channel_array.length; i++) {
                    Log.d("ChannelActivity", "channel_array[i]:" + channel_array[i]);
                }

                intent.putExtra("channel_array", channel_array);
                ArrayList list = removeDuplicate(channelList);
                Log.d("ChannelActivity", "list.size():" + list.size());
                intent.putStringArrayListExtra("channel_list", list);
                Log.d("Test", listToString(list));
                if (list.size() > 0 && list.size() <= 5) {
                    setResult(1, intent);
                    finish();
                } else {
                    Toast.makeText(this, "请选择1~5个频道", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public  String listToString(ArrayList<String> mList) {
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

    public  <T> List<T> removeNull(List<? extends T> oldList) {
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
}
