package com.iyoyogo.android.utils.emoji;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.iyoyogo.android.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by zhanghuan on 2016/3/9.
 *
 * 表情填充器
 */
public class FaceAdapter extends BaseAdapter {



    private List<ChatEmoji> data;
    private int popupWidth;
    private int popupHeight;
    private LayoutInflater inflater;

    private int size=0;
    private Context context;
    public ImageView aa;
    private GridView mGridView;

    public FaceAdapter(Context context, List<ChatEmoji> list, GridView view) {
        this.context = context;
        this.data=list;
        this.size=list.size();
        this.mGridView = view;
    }

    @Override
    public int getCount() {
        return this.size;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChatEmoji emoji=data.get(position);
        ViewHolder viewHolder=null;
        if(convertView == null) {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_face, null);
            aa = (ImageView)convertView.findViewById(R.id.item_iv_face);
            viewHolder.iv_face = aa;
            convertView.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if(emoji.getId() == R.drawable.face_del_icon) {
            convertView.setBackgroundDrawable(null);
            viewHolder.iv_face.setImageResource(emoji.getId());
        } else if(TextUtils.isEmpty(emoji.getCharacter())) {
            convertView.setBackgroundDrawable(null);
            viewHolder.iv_face.setImageDrawable(null);
        } else {
            viewHolder.iv_face.setTag(emoji);
            viewHolder.iv_face.setImageResource(emoji.getId());
        }
        ViewHolder finalViewHolder = viewHolder;
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

               return false;
           }
       });
    viewHolder.iv_face.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            View v1 = LayoutInflater.from(context).inflate(R.layout.pop,null);
//            PopupWindow popupWindow =  new PopupWindow(v1);
//            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//            TextView item_iv_face = (TextView) v1.findViewById(R.id.item_iv_face);
//            v1.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//            popupHeight = v1.getMeasuredHeight();
//            popupWidth = v1.getMeasuredWidth();
//            final int[] location = new int[2];
//            item_iv_face.setText(data.get(position).getCharacter());
//            Drawable drawable = context.getResources().getDrawable(data.get(position).getId());
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
//            // param 左上右下
//            item_iv_face.setCompoundDrawables(null,drawable,null,null);
//            finalViewHolder.iv_face.getLocationOnScreen(location);
//            popupWindow.setOutsideTouchable(true);//设置点击空白消失
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));//设置背景;点击返回按钮,关闭PopupWindow
//            popupWindow.showAtLocation(finalViewHolder.iv_face,Gravity.NO_GRAVITY, (location[0] + v1.getWidth() / 2) - popupWidth / 2160, location[1] - popupHeight-150 );
            return false;
        }
    });

        return convertView;

    }

  public class ViewHolder {
        public ImageView iv_face;
    }
}
