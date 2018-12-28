package com.iyoyogo.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constant;
import com.iyoyogo.android.utils.ImagePickAction;
import com.iyoyogo.android.utils.imagepicker.bean.ImageBean;
import com.iyoyogo.android.utils.imagepicker.component.BaseRecyclerAdapter;
import com.iyoyogo.android.utils.imagepicker.component.BaseViewHolder;
import com.iyoyogo.android.utils.imagepicker.component.OnItemChooseCallback;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemClickListener;
import com.iyoyogo.android.utils.imagepicker.component.OnRecyclerViewItemLongClickListener;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 2017/12/3.
 */

public class ImageAdapter extends BaseRecyclerAdapter {

    private int count = 0;
    private int maxNum = 1;
    private Context context;
    private List<ImageBean> listImage;
    private static ArrayList<String> mSelectImg = new ArrayList<>();

    public ImageAdapter(Context context, List<ImageBean> listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture,null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }


    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
    public ArrayList<String> selectPhoto() {
        if (!mSelectImg.isEmpty()) {
            return mSelectImg;
        }
        return null;
    }
    public String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
    private class MyViewHolder extends BaseViewHolder {


        ImageView imgPicture;
        ImageView iv;
        ImageView videoPlay;
        TextView videoDuration;
        private MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void intOnItemChooseCallback(final OnItemChooseCallback chooseCallback, final int position) {
            String path = listImage.get(position).getImagePath();

            iv.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (count < maxNum){
                       if (!listImage.get(position).isChoose()&&!mSelectImg.contains(path)){
                           iv.setImageResource(R.mipmap.zp_xz);
                           mSelectImg.add(path);
                           listImage.get(position).setChoose(true);
                           chooseCallback.chooseState(position,true);
                           count ++;
                           notifyDataSetChanged();
                       } else {
                           mSelectImg.remove(path);
                           iv.setImageResource(R.mipmap.pic_wxz);
                           listImage.get(position).setChoose(false);
                           chooseCallback.chooseState(position,false);
                           count--;
                           notifyDataSetChanged();
                       }

                   } else { //count >= maxNum
                       if (!listImage.get(position).isChoose()){
                           chooseCallback.countWarning(count);
                       } else {
                           iv.setImageResource(R.mipmap.pic_wxz);
                           listImage.get(position).setChoose(false);
                           chooseCallback.chooseState(position,false);
                       }
                   }
                   chooseCallback.countNow(count);
               }
           });
        }

        @Override
        public void initOnItemClickListener(final OnRecyclerViewItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }

        @Override
        public void iniOnItemLongClickListener(OnRecyclerViewItemLongClickListener longClickListener, int position) {

        }


        @Override
        protected void findViewById(View itemView) {


            imgPicture = itemView.findViewById(R.id.img_picture);
            iv = itemView.findViewById(R.id.iv);
            videoPlay = itemView.findViewById(R.id.video_play);
            videoDuration = itemView.findViewById(R.id.video_duration);
        }

        @Override
        public void onBind(int position) {
            String path = listImage.get(position).getImagePath();
            if (listImage.get(position).isChoose()){
                iv.setImageResource(R.mipmap.zp_xz);
            } else {
                iv.setImageResource(R.mipmap.pic_wxz);
            }
            Glide.with(context)
                    .load(listImage.get(position).getImagePath())
                    .into(imgPicture);
            if (position == 0) {
                videoPlay.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
                imgPicture.setImageResource(R.mipmap.xiangce_xiangji);
            } else {


                LocalMedia mMedia = new LocalMedia();
                mMedia.setPath(path);
                String fileExtension = MimeTypeMap.getFileExtensionFromUrl(path);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
                if (mimeType != null && mimeType.contains("video")) {
                    mMedia.setPictureType(Constant.PARAM_VIDEO_MP4);

                    if (listImage.get(position).getDuration() != 0) {

                        Glide.with(context).load(ImagePickAction.getVideoThumb(mMedia.getPath(), 1)).into(imgPicture);
                        videoDuration.setVisibility(View.VISIBLE);
                        videoPlay.setVisibility(View.VISIBLE);
                        long duration =listImage.get(position).getDuration();

                        String s = generateTime(duration);
                        Log.d("MediaAdapter", s);
                        videoDuration.setText(s);
                    }
                } else {
                    videoPlay.setVisibility(View.GONE);
                    Glide.with(context).load(path).into(imgPicture);
                }
            }
        }
    }

}
