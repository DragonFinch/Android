package com.iyoyogo.android.ui.common;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.iyoyogo.android.utils.imagepicker.adapter.ImagesListAdapter;
import com.iyoyogo.android.utils.imagepicker.bean.ImageBean;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;
import com.iyoyogo.android.utils.imagepicker.util.ImageFinder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PictureEditActivity extends BaseActivity {

    @BindView(R.id.main_back_id)
    ImageView mainBackId;
    @BindView(R.id.main_image_but_id)
    TextView mainImageButId;
    @BindView(R.id.main_toolbar_id)
    Toolbar mainToolbarId;
    @BindView(R.id.main_recyclerview_id)
    RecyclerView mainRecyclerviewId;
    @BindView(R.id.main_yuantu)
    ImageView mainYuantu;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.send_yoji)
    ImageView sendYoji;
    @BindView(R.id.send_yoxiu)
    ImageView sendYoxiu;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    int maxCount=9;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_edit;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    /**
     * Item点击事件的监听类
     */
    private class MyOnItemClickListener implements OnRecyclerViewItemClickListener {

        @Override
        public void onItemClick(int position) {
//            ImagesPreviewActivity.startPreView(PictureEditActivity.this, list.get(position).getImagePath());
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
            Toast.makeText(PictureEditActivity.this, "最多选择" + count + "张图片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        List<ImageBean> list = ImageFinder.getImages(this, ImageFinder.TYPE_GIF);
        List<ImageBean> video = ImageFinder.getVideo(getApplicationContext());


        MyChooseCallback callback = new MyChooseCallback();
        MyOnItemClickListener listener = new MyOnItemClickListener();
        ImagesListAdapter adapter = new ImagesListAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);

        adapter.setMaxNum(maxCount);
        adapter.setChooseCallback(callback);
        adapter.setOnRecyclerViewItemClickListener(listener);
        mainRecyclerviewId.setLayoutManager(layoutManager);
        mainRecyclerviewId.setAdapter(adapter);
    }




    @OnClick({R.id.main_yuantu, R.id.delete, R.id.send_yoji, R.id.send_yoxiu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_yuantu:

                break;
            case R.id.delete:

                break;
            case R.id.send_yoji:

                break;
            case R.id.send_yoxiu:

                break;
        }
    }

}