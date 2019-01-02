package com.iyoyogo.android.adapter.viewholder;

import android.view.View;

/**
 * 移动收藏夹内容的点击
 */
public interface OnItemClickLitener {
	
	void onItemClick(View view, int position);
	void onItemLongClick(View view, int position);
}