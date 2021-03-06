package com.iyoyogo.android.camera.edit.animatesticker;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.camera.interfaces.OnItemClickListener;
import com.iyoyogo.android.camera.utils.asset.NvAsset;
import com.iyoyogo.android.camera.utils.asset.NvAssetManager;

import java.util.ArrayList;

/**
 * Created by admin on 2018/5/25.
 */

public class StickerRecycleViewAdaper extends RecyclerView.Adapter<StickerRecycleViewAdaper.ViewHolder>  {
    private ArrayList<NvAsset> mAssetList = new ArrayList<>();
    private ArrayList<NvAssetManager.NvCustomStickerInfo> mCustomStickerAssetList = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener = null;

    private int mSelectedPos = -1;
    private boolean mIsCutomStickerAsset = false;

    public StickerRecycleViewAdaper(Context context) {
        mContext = context;
    }
    public void setAssetList(ArrayList<NvAsset> assetList) {
        this.mAssetList = assetList;
    }
    public void setCustomStickerAssetList(ArrayList<NvAssetManager.NvCustomStickerInfo> assetList) {
        this.mCustomStickerAssetList = assetList;
    }
    public void setIsCutomStickerAsset(boolean isCutomStickerAsset) {
        mIsCutomStickerAsset = isCutomStickerAsset;
    }
    public void setSelectedPos(int selectedPos) {
        this.mSelectedPos = selectedPos;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_asset_animatesticker, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String assetCoverUrl =  mIsCutomStickerAsset ? mCustomStickerAssetList.get(position).imagePath : mAssetList.get(position).coverUrl;
        //加载图片
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.mipmap.default_sticker);
        Glide.with(mContext)
                .asBitmap()
                .load(assetCoverUrl)
                .apply(options)
                .into(holder.mStickerAssetCover);

        holder.mSelecteItem.setVisibility(mSelectedPos == position ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v,position);
                if(mSelectedPos>= 0 && mSelectedPos == position)
                    return;
                mSelectedPos = position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mIsCutomStickerAsset ? mCustomStickerAssetList.size() : mAssetList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mStickerAssetCover;
        View mSelecteItem;
        public ViewHolder(View itemView) {
            super(itemView);
            mStickerAssetCover = (ImageView) itemView.findViewById(R.id.stickerAssetCover);
            mSelecteItem = itemView.findViewById(R.id.selectedItem);
        }
    }
}
