package com.iyoyogo.android.ui.mine.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MyAdapter1;
import com.iyoyogo.android.base.BaseFragment;
import com.iyoyogo.android.bean.map.MapBean;
import com.iyoyogo.android.contract.GeRenChengShiContract;
import com.iyoyogo.android.presenter.GenRenXinxiChengShiPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.view.MyQuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class GuiJiFragment extends BaseFragment<GeRenChengShiContract.Presenter> implements GeRenChengShiContract.View{


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.qwe)
    LinearLayout bbbbbqwe;
    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.my_quickIndexbar)
    MyQuickIndexBar mQuickIndexBar;
  /*  @BindView(R.id.tv_window)
    TextView mTv_window;*/
    Unbinder unbinder;
    public List<MapBean.DataBean.ListBean> list = new ArrayList<>();
    private final String name1;

    @SuppressLint("ValidFragment")
    public GuiJiFragment(String name) {
        // Required empty public constructor
        name1 = name;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }


    private Handler mHandler = new Handler();
    private boolean mFlag;

    private void showCurrentTvWindow(String letter) {
        if (!mFlag) {
            mFlag = true;
         /*   ViewPropertyAnimator.animate(mTv_window).setDuration(450).scaleX(1.0F).start();
            ViewPropertyAnimator.animate(mTv_window).setDuration(450).scaleY(1.0F).start();*/
        }
        // mTv_window.setText(letter);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFlag = false;
             /*   ViewPropertyAnimator.animate(mTv_window).setDuration(450).scaleX(0.0F).start();
                ViewPropertyAnimator.animate(mTv_window).setDuration(450).scaleY(0.0F).start();*/
            }
        }, 2000);
    }

    @Override
    protected GeRenChengShiContract.Presenter createPresenter() {
        return new GenRenXinxiChengShiPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        name.setText(name1);
        String user_id = SpUtils.getString(getActivity(), "user_id", null);
        String user_token = SpUtils.getString(getActivity(), "user_token", null);
        mPresenter.aboutMe(user_id, user_token, "international", "");
    }
    //q请求成功的回调
    @Override
    public void aboutMeSuccess(MapBean mapBean) {
        List<MapBean.DataBean.ListBean> list1 = mapBean.getData().getList();
        /*Log.e("aboutMeSuccess", "aboutMeSuccess: " + list1.);
        */
      /*  for (int i = 0; i < list1.size(); i++) {
         //   Log.e("aboutMeSuccess", "aboutMeSuccess: " + list1.get(i).getChina_name()+"+++++"+list1.get(i).getCode());
           // Log.e("aboutMeSuccess", "aboutMeSuccess: " + list1.get(i).getCode());
        }*/
       // Log.e("knowsall", "aboutMeSuccess: " + list1.size());
        Collections.sort(list1);
        MyAdapter1 adapter = new MyAdapter1(getActivity(), list1);
        mLv.setAdapter(adapter);
        mQuickIndexBar.setOnItemtClickListenner(new MyQuickIndexBar.onItemClickListenner() {
            @Override
            public void getOnImtemPosition(String letter) {
                for (int i = 0; i < list1.size(); i++) {
                    String strLetter = list1.get(i).getEnglish_name().charAt(0) + "";
                    if (letter.equalsIgnoreCase(strLetter)) {
                        mLv.setSelection(i);
                       /* for (int j = 0; j <list1.size() ; j++) {
                            Log.e("getOnImtemPosition", "getOnImtemPosition: "+list1.get(j).getChina_name()+"==="+list1.get(j).getCode() );
                            Log.e("qw", "ViewHolder: "+tv_name.toString()+"++++++++"+tv_name1.toString() );
                        }*/
                      /*  mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getActivity(), (CharSequence) list1.get(position),Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        break;
                    }
                }
                showCurrentTvWindow(letter);
            }

        });
       /* ViewHelper.setScaleX(mTv_window, 0F);
        ViewHelper.setScaleY(mTv_window, 0F);*/

       mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if (setData != null){
                   setData.getData(list1.get(position).getChina_name());
               }
           }
       });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public  interface setData{
        void getData(String name);
    }
    private setData setData;

    public void setSetData(setData setData) {
        this.setData = setData;
    }
}
