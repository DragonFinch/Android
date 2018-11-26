package com.iyoyogo.android.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iyoyogo.android.R;
import com.iyoyogo.android.app.App;
import com.iyoyogo.android.widget.MLImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class GuoLvAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Bitmap bitmap = ((BitmapDrawable) App.getmContext().getResources().getDrawable(
            R.mipmap.fengjing)).getBitmap();
    Context context;
    List<String> data;

    public GuoLvAdapter1(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.list_guolv, parent, false);
        holder = new MyRecler(view);

        return holder;
    }

    class MyRecler extends RecyclerView.ViewHolder {
        TextView text;
        MLImageView image;
        ImageView gou;

        public MyRecler(View view) {
            super(view);
            text = view.findViewById(R.id.text);
            image = view.findViewById(R.id.image);
            gou = view.findViewById(R.id.gou);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyRecler hol = (MyRecler) holder;
        String s = data.get(position);
        hol.text.setText(s);
        hol.text.getBackground().setAlpha(100);
        String type = data.get(position);
        Bitmap b = Bitmap.createBitmap(bitmap);

//            GPUImage image = new GPUImage(context);
//            image.setImage(bitmap);
//            image.setFilter(MagicFilterFactory.initFilters(type));
//            b = image.getBitmapWithFilterApplied();

        hol.image.setImageBitmap(b);
        hol.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilterChangeListener.onFilterChanged(position);
            }
        });
        hol.gou.setVisibility(View.GONE);
        if (weizhi == position) {
            hol.gou.setVisibility(View.VISIBLE);
        } else {
            hol.gou.setVisibility(View.GONE);
        }
    }

    int weizhi = -1;

    public int getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(int weizhi) {
        this.weizhi = weizhi;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface onFilterChangeListener {
        void onFilterChanged(int filterType);
    }

    private onFilterChangeListener onFilterChangeListener;

    public void setOnFilterChangeListener(onFilterChangeListener onFilterChangeListener) {
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
