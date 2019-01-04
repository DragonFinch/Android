package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.DraftBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

//草稿适配器
public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.ViewHolder> {
    Context context;
    List<DraftBean.DataBean.ListBean> mList;
    private final Activity activity;

    public DraftAdapter(Context context, List<DraftBean.DataBean.ListBean> list) {
        this.context = context;
        this.mList = list;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_draft, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(context, 8));
        Glide.with(context).load(mList.get(position).getFile_path()).apply(myOptions).into(holder.img_draft);
        int yo_type = mList.get(position).getYo_type();
        if (yo_type == 1) {
            holder.img_type.setImageResource(R.mipmap.caogao_yoxiu);
        } else {
            holder.img_type.setImageResource(R.mipmap.caogao_yoji);
        }
        holder.tv_title.setText(mList.get(position).getTitle());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDelete(holder, mList.get(position).getYo_id(),position);
            }
        });

    }
    public interface DeleteOnClickListener {
        void delete();
    }

    DeleteOnClickListener deleteOnClickListener;

    public void setDeleteOnClickListener(DeleteOnClickListener deleteOnClickListener) {
        this.deleteOnClickListener = deleteOnClickListener;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_draft, more, img_type;
        TextView tv_title, tv_commit_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_draft = itemView.findViewById(R.id.img_draft);
            more = itemView.findViewById(R.id.more);
            img_type = itemView.findViewById(R.id.type_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_commit_time = itemView.findViewById(R.id.tv_commit_time);
        }
    }

    private void initDelete(ViewHolder holder, int yo_id, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_delete_or_report, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        String user_id = SpUtils.getString(context, "user_id", null);
        String user_token = SpUtils.getString(context, "user_token", null);
        TextView tv_delete = view.findViewById(R.id.tv_delete);
        TextView tv_report = view.findViewById(R.id.tv_report);

        tv_delete.setVisibility(View.VISIBLE);
        tv_report.setVisibility(View.GONE);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote().deleteYo(user_id, user_token, yo_id)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                mList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                popupWindow.dismiss();
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.6f);
        popupWindow.setOnDismissListener(new poponDismissListener());
        popupWindow.showAsDropDown(holder.more, 0, 0);
    }

    public void backgroundAlpha(float bgAlpha) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        activity.getWindow().setAttributes(lp); //act 是上下文context

    }

    //隐藏事件PopupWindow
    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1.0f);
        }
    }
}
