package com.iyoyogo.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iyoyogo.android.R;
import com.iyoyogo.android.app.Constant;
import com.iyoyogo.android.utils.ImagePickAction;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, String>> listImage;
    private int selectedPosition=-1;//保存当前选中的position 重点！
    public MediaAdapter(Context context, List<HashMap<String, String>> listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public  String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if (vh==null){
        convertView = LayoutInflater.from(context).inflate(R.layout.item_picture, null);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        HashMap<String, String> stringStringHashMap = listImage.get(position);
        String path = stringStringHashMap.get("_data");
        LocalMedia mMedia = new LocalMedia();
        mMedia.setPath(path);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(path);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        if (mimeType != null && mimeType.contains("video")) {
            mMedia.setPictureType(Constant.PARAM_VIDEO_MP4);
            Glide.with(context).load(ImagePickAction.getVideoThumb(mMedia.getPath(), 1)).into(vh.imgPicture);
            vh.videoDuration.setVisibility(View.VISIBLE);
            String duration = stringStringHashMap.get("duration");
            String s = generateTime(Long.valueOf(duration));
            vh.videoDuration.setText(s);
            //对应的ImageView
        }else {
            vh.videoPlay.setVisibility(View.GONE);
           Glide.with(context).load(path).into(vh.imgPicture);
        }

        if(position%2==0){
            if (selectedPosition==position) {
                convertView.setSelected(true);
                convertView.setPressed(true);
//     convertView.setBackgroundColor(Color.parseColor("#0097e0"));
                vh.iv.setImageResource(R.mipmap.xz);
            }else{
                convertView.setSelected(false);
                convertView.setPressed(false);
//     convertView.setBackgroundColor(Color.parseColor("#e4ebf1"));
                vh.iv.setImageResource(R.mipmap.log_unselect);
            }
        }else{
            if (selectedPosition==position) {
                convertView.setSelected(true);
                convertView.setPressed(true);
                vh.iv.setImageResource(R.mipmap.xz);
//     convertView.setBackgroundColor(Color.parseColor("#0097e0"));
            }else{
                convertView.setSelected(false);
                convertView.setPressed(false);
//     convertView.setBackgroundColor(Color.parseColor("#ced7de"));
                vh.iv.setImageResource(R.mipmap.log_unselect);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_picture)
        ImageView imgPicture;
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.video_play)
        ImageView videoPlay;
        @BindView(R.id.video_duration)
        TextView videoDuration;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
