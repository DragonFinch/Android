package com.iyoyogo.android.camera.edit.filter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.camera.data.FilterItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2017/12/19 0019.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private Context mContext;
    private Boolean mIsArface = false;
    private OnItemClickListener mClickListener;
    private List<FilterItem> mFilterDataList = new ArrayList<>();

    private int mSelectPos = 0;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onSameItemClick();
    }

    public FilterAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setFilterDataList(List<FilterItem> filterDataList) {
        this.mFilterDataList = filterDataList;
        notifyDataSetChanged();
    }

    public void setSelectPos(int pos) {
        this.mSelectPos = pos;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout item_assetLayout;
        private ImageView item_assetImage;
        private TextView item_assetName;

        public ViewHolder(View view) {
            super(view);
            item_assetLayout = (RelativeLayout) view.findViewById(R.id.layoutAsset);
            item_assetName = (TextView) view.findViewById(R.id.nameAsset);
            item_assetImage = (ImageView) view.findViewById(R.id.imageAsset);
        }
    }

    public void isArface(Boolean isArface) {
        mIsArface = isArface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fx, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FilterItem itemData = mFilterDataList.get(position);
        if (itemData == null)
            return;
//明快、精致、清晰、蕾丝、质感、元气、薄荷、草莓、粉嫩、清凉
        String name = itemData.getFilterName();
        if (name != null && !mIsArface) {
            if (name.equals("Sage")) {
                name = "明快";
            } else if (name.equals("Maid")) {
                name = "精致";
            } else if (name.equals("Mace")) {
                name = "清晰";
            } else if (name.equals("Lace")) {
                name = "蕾丝";
            } else if (name.equals("Mall")) {
                name = "质感";
            } else if (name.equals("Sap")) {
                name = "元气";
            } else if (name.equals("Sara")) {
                name = "薄荷";
            } else if (name.equals("Pinky")) {
                name = "草莓";
            } else if (name.equals("Sweet")) {
                name = "粉嫩";
            } else if (name.equals("Fresh")) {
                name = "清凉";
            }

            holder.item_assetName.setText(name);
        }
        if (mIsArface) {
            holder.item_assetName.setText("");
        }

        int filterMode = itemData.getFilterMode();
        if (filterMode == FilterItem.FILTERMODE_BUILTIN) {
            int imageId = itemData.getImageId();
            if (imageId != 0)
                holder.item_assetImage.setImageResource(imageId);
        } else {
            String imageUrl = itemData.getImageUrl();
            if (imageUrl != null) {
                if (filterMode == FilterItem.FILTERMODE_BUNDLE) {
                    try {
                        InputStream inStream = mContext.getAssets().open(imageUrl);
                        Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                        holder.item_assetImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //加载图片
                    RequestOptions options = new RequestOptions();
                    options.centerCrop();
                    options.placeholder(R.mipmap.default_filter);
                    Glide.with(mContext)
                            .asBitmap()
                            .load(imageUrl)
                            .apply(options)
                            .into(holder.item_assetImage);
                }
            }
        }
        if (mSelectPos == position) {
            holder.item_assetLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.fx_item_radius_shape_select));
            holder.item_assetName.setTextColor(Color.parseColor("#4a90e2"));
        } else {
            holder.item_assetLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.fx_item_radius_shape_unselect));
            holder.item_assetName.setTextColor(Color.parseColor("#ffffff"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectPos == position) {
                    if (mClickListener != null) {
                        mClickListener.onSameItemClick();
                    }
                    return;
                }

                mSelectPos = position;
                notifyDataSetChanged();
                if (mClickListener != null) {
                    mClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilterDataList.size();
    }
}

