package com.iyoyogo.android.ui.common;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
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
    private List<String> mList;
    ArrayList names = null;
    ArrayList descs = null;
    ArrayList fileNames = null;
    ArrayList video_names = null;
    ArrayList video_descs = null;
    ArrayList video_fileNames = null;
    private List<LocalMedia> selectList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_edit;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    private RecyclerView recyclerView;

    @Override
    protected void initView() {
        super.initView();
        PictureSelector.create(PictureEditActivity.this)
                .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考 demo values/styles 下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
//                .compressGrade()// luban 压缩档次，默认 3 档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认 true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1 之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
//                .compress(true// 是否压缩 true or false
//                .compressMode()//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(16,9)// int 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示 uCrop 工具栏，默认不显示 true or false
                .isGif(true)// 是否显示 gif 图片 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为 false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为 false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认 90 int
//                .compressMaxKB()//压缩最大值 kb compressGrade()为 Luban.CUSTOM_GEAR 有效 int
//                .compressWH() // 压缩宽高比 compressGrade()为 Luban.CUSTOM_GEAR 有效  int
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
//                .videoSecond()// 显示多少秒以内的视频 or 音频也可适用 int
                .recordVideoSecond(10)//视频秒数录制 默认 60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调 onActivityResult code
        names = new ArrayList();
        descs = new ArrayList();
        fileNames = new ArrayList();
        video_names = new ArrayList();
        video_descs = new ArrayList();
        video_fileNames = new ArrayList();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
//            cursor.getString(cursor.getColumnIndex(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursor.getBlob(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            //获取图片的详细信息
            String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION));
            names.add(name);
            descs.add(desc);
            fileNames.add(new String(data, 0, data.length - 1));


        }
        Cursor cursors = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursors.moveToNext()) {
//            cursor.getString(cursor.getColumnIndex(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            //获取图片的名称
            String name = cursors.getString(cursors.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            //获取图片的生成日期
            byte[] data = cursors.getBlob(cursors.getColumnIndex(MediaStore.Video.Media.DATA));
            //获取图片的详细信息
            String desc = cursors.getString(cursors.getColumnIndex(MediaStore.Video.Media.DESCRIPTION));
//            long aLong = cursors.getLong(cursors.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            video_names.add(name);
            video_descs.add(desc);
            video_fileNames.add(new String(data, 0, data.length - 1));


        }
        for (int i = 0; i < fileNames.size(); i++) {
            Log.d("EmptyActivity", "fileNames.get(i):" + fileNames.get(i));
        }
        for (int i = 0; i < video_fileNames.size(); i++) {
            Log.d("EmptyActivity", "video_fileNames.get(i):" + video_fileNames.get(i));
        }
        List<String> mediaAll = new ArrayList<>();
        mediaAll.addAll(fileNames);
        mediaAll.addAll(video_fileNames);
        Log.d("PictureEditActivity", "mediaAll.size():" + mediaAll.size());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种 path
                    // 1.media.getPath(); 为原图 path
                    // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
                    // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
//                    DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
    }
}