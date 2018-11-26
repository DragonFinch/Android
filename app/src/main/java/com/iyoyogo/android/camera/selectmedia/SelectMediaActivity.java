package com.iyoyogo.android.camera.selectmedia;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constants;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.camera.base.BaseFragmentPagerAdapter;
import com.iyoyogo.android.camera.data.BackupData;
import com.iyoyogo.android.camera.edit.VideoEditActivity;
import com.iyoyogo.android.camera.selectmedia.bean.MediaData;
import com.iyoyogo.android.camera.selectmedia.fragment.MediaFragment;
import com.iyoyogo.android.camera.selectmedia.interfaces.OnTotalNumChangeForActivity;
import com.iyoyogo.android.camera.selectmedia.view.CustomPopWindow;
import com.iyoyogo.android.camera.utils.AppManager;
import com.iyoyogo.android.camera.utils.MediaConstant;
import com.iyoyogo.android.camera.utils.Util;
import com.iyoyogo.android.camera.utils.dataInfo.ClipInfo;
import com.iyoyogo.android.camera.utils.dataInfo.TimelineData;
import com.iyoyogo.android.widget.CustomTitleBar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.iyoyogo.android.app.Constants.POINT16V9;
import static com.iyoyogo.android.app.Constants.POINT1V1;
import static com.iyoyogo.android.app.Constants.POINT3V4;
import static com.iyoyogo.android.app.Constants.POINT4V3;
import static com.iyoyogo.android.app.Constants.POINT9V16;

public class SelectMediaActivity extends BaseActivity implements OnTotalNumChangeForActivity, CustomPopWindow.OnViewClickListener ,View.OnClickListener {
    private String TAG = getClass().getName();
    private CustomTitleBar mTitleBar;
    private TabLayout tlSelectMedia;
    private ViewPager vpSelectMedia;
    private List<Fragment> fragmentLists = new ArrayList<>();
    private List<String> fragmentTabTitles = new ArrayList<>();
    private BaseFragmentPagerAdapter fragmentPagerAdapter;
    private int visitMethod = Constants.FROMMAINACTIVITYTOVISIT;
    private List<MediaData> mMediaDataList = new ArrayList<>();
    private static int total = 0;
    private TextView meidaTVOfStart;
    private float alphaOnPop = 0.6f;

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        SelectMediaActivity.total = total;
    }



    @Override
    protected void initView() {
        mTitleBar = (CustomTitleBar) findViewById(R.id.title_bar);
        tlSelectMedia = (TabLayout) findViewById(R.id.tl_select_media);
        vpSelectMedia = (ViewPager) findViewById(R.id.vp_select_media);
        meidaTVOfStart = (TextView) findViewById(R.id.media_tv_startEdit);
        meidaTVOfStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visitMethod == Constants.FROMMAINACTIVITYTOVISIT) {
                    new CustomPopWindow.PopupWindowBuilder(SelectMediaActivity.this)
                            .setView(R.layout.pop_select_makesize)
//                            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
//                            .setBgDarkAlpha(alphaOnPop) // 控制亮度
                            .setViewClickListener(SelectMediaActivity.this)
                            .create()
                            .showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
                } else if (visitMethod == Constants.FROMCLIPEDITACTIVITYTOVISIT) {
                    ArrayList<ClipInfo> clipInfos = getClipInfoList();
                    BackupData.instance().setAddClipInfoList(clipInfos);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    AppManager.getInstance().finishActivity();
                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_media;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initTitle() {
        mTitleBar.setTextCenter(R.string.selectMedia);
    }

    public void setTitleText(int count) {
        if (count > 0) {
            String txt = getResources().getString(R.string.setSelectMedia);
            @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String txtWidthCount = String.format(txt, count);
            mTitleBar.setTextCenter(txtWidthCount);
        } else {
            mTitleBar.setTextCenter(R.string.selectMedia);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                visitMethod = bundle.getInt("visitMethod", Constants.FROMMAINACTIVITYTOVISIT);
            }
        }
        String[] tabList = getResources().getStringArray(R.array.select_media);
        checkDataCountAndTypeCount(tabList, MediaConstant.MEDIATYPECOUNT);
        for (int i = 0; i < tabList.length; i++) {
            MediaFragment mediaFragment = new MediaFragment(this, this, MediaConstant.TYPE_ITEMCLICK_MULTIPLE);
            Bundle bundle = new Bundle();
            bundle.putInt(MediaConstant.MEDIA_TYPE, MediaConstant.MEDIATYPECOUNT[i]);
            bundle.putInt("visitMethod", visitMethod);
            mediaFragment.setArguments(bundle);
            fragmentLists.add(mediaFragment);
            fragmentTabTitles.add(tabList[i]);
        }

        //禁止预加载
        vpSelectMedia.setOffscreenPageLimit(3);
        //测试提交
        fragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentLists, fragmentTabTitles);
        vpSelectMedia.setAdapter(fragmentPagerAdapter);
        vpSelectMedia.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                notifyFragmentDataSetChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tlSelectMedia.setupWithViewPager(vpSelectMedia);
    }

    /**
     * 校验一次数据，使得item标注的数据统一
     *
     * @param position 碎片对应位置0.1.2
     */
    private void notifyFragmentDataSetChanged(int position) {
        MediaFragment fragment = (MediaFragment) fragmentLists.get(position);
        List<MediaData> currentFragmentList = checkoutSelectList(fragment);
        fragment.getAdapter().setSelectList(currentFragmentList);
        setTitleText(fragment.getAdapter().getSelectList().size());
        Logger.e(TAG, "onPageSelected: " + fragment.getAdapter().getSelectList().size());
    }

    private List<MediaData> checkoutSelectList(MediaFragment fragment) {
        List<MediaData> currentFragmentList = fragment.getAdapter().getSelectList();
        List<MediaData> totalSelectList = getmMediaDataList();
        for (MediaData mediaData : currentFragmentList) {
            for (MediaData data : totalSelectList) {
                if (data.getPath().equals(mediaData.getPath()) && data.isState() == mediaData.isState()) {
                    mediaData.setPosition(data.getPosition());
                }
            }
        }
        return currentFragmentList;
    }

    private void checkDataCountAndTypeCount(String[] tabList, int[] mediaTypeCount) {
        if (tabList.length != mediaTypeCount.length) {
            return;
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //判断如果同意的情况下就去 吧权限请求设置给当前fragment的
        for (int i = 0; i < fragmentLists.size(); i++) {
            fragmentLists.get(i).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onTotalNumChangeForActivity(List selectList, Object tag) {
        meidaTVOfStart.setVisibility(total > 0 ? View.VISIBLE : View.GONE);
        int index = (int) tag;
        Logger.e("2222", "onTotalNumChangeForActivity对应的碎片：  " + index);
        for (int i = 0; i < fragmentLists.size(); i++) {
            if (i != index) {
                Logger.e("2222", "要刷新的碎片：  " + i);
                MediaFragment fragment = (MediaFragment) fragmentLists.get(i);
                fragment.refreshSelect(selectList, index);
            }
        }
        Logger.e(TAG, "onTotalNumChangeForActivity  " + selectList.size());
    }


    public List<MediaData> getmMediaDataList() {
        if (mMediaDataList == null) {
            return new ArrayList<>();
        }
        MediaFragment fragment = (MediaFragment) fragmentLists.get(0);
        return fragment.getAdapter().getSelectList();
    }

    @Override
    protected void onStop() {
        setTotal(0);
        super.onStop();
        Logger.e(TAG, "onStop");
    }


    @Override
    public void onViewClick(CustomPopWindow popWindow, View view) {
        switch (view.getId()) {
            case R.id.button16v9:
                selectCreateRatio(POINT16V9);
                break;
            case R.id.button1v1:
                selectCreateRatio(POINT1V1);
                break;
            case R.id.button9v16:
                selectCreateRatio(POINT9V16);
                break;
            case R.id.button3v4:
                selectCreateRatio(POINT3V4);
                break;
            case R.id.button4v3:
                selectCreateRatio(POINT4V3);
                break;
            default:
                break;
        }
    }

    private void selectCreateRatio(int makeRatio) {
        ArrayList<ClipInfo> pathList = getClipInfoList();
        TimelineData.instance().setVideoResolution(Util.getVideoEditResolution(makeRatio));
        TimelineData.instance().setClipInfoData(pathList);
        TimelineData.instance().setMakeRatio(makeRatio);
        AppManager.getInstance().jumpActivity(SelectMediaActivity.this, VideoEditActivity.class, null);
        AppManager.getInstance().finishActivity();
    }

    private ArrayList<ClipInfo> getClipInfoList() {
        ArrayList<ClipInfo> pathList = new ArrayList<>();
        for (MediaData mediaData : getmMediaDataList()) {
            ClipInfo clipInfo = new ClipInfo();
            clipInfo.setFilePath(mediaData.getPath());
            pathList.add(clipInfo);
        }
        return pathList;
    }
}
