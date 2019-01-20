package com.iyoyogo.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.comment.CommentBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.ReplyDiscussActivity;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.util.MyConversionUtil;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 回复评论的适配器
 */
public class ReplyDiscussAdapter extends RecyclerView.Adapter<ReplyDiscussAdapter.ViewHolder> {
    private Context context;
    private String userName;
    List<CommentBean.DataBean.ListBean> mList;

    public ReplyDiscussAdapter(Context context, List<CommentBean.DataBean.ListBean> mList, String user_nickname) {
        this.context = context;
        this.mList = mList;
        this.userName = user_nickname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reply_comment, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.medal.setVisibility(View.VISIBLE);
        int partner_type = mList.get(position).getPartner_type();
        if (partner_type == 0) {
            mList.get(position).setPartner_type(0);
            holder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type == 1) {
            mList.get(position).setPartner_type(1);
            holder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type == 2) {
            mList.get(position).setPartner_type(2);
            holder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type == 3) {
            mList.get(position).setPartner_type(3);
            holder.medal.setImageResource(R.mipmap.kol);
        } else {
            holder.medal.setVisibility(mList.get(position).getPartner_type() == 0 ? View.INVISIBLE : View.VISIBLE);
        }

        //设置字体(default,default-bold,monospace,serif,sans-serif)
//        msp.setSpan(new TypefaceSpan("monospace"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (position == 0) {
            SpannableString msp1 = new SpannableString(mList.get(position).getUser_nickname() + " 回复" + userName + ":" + mList.get(position).getContent());
            msp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FA800A")), 0, mList.get(position).getUser_nickname().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
            msp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FA800A")), mList.get(position).getUser_nickname().length() + 3, mList.get(position).getUser_nickname().length() + 4 + userName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
            holder.user_content.setText(msp1);
            holder.tv_time.setText(mList.get(position).getCreate_time());
            holder.tv_comment_like_num.setText(mList.get(position).getCount_praise() + "");
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.default_touxiang);
            requestOptions.placeholder(R.mipmap.default_touxiang);
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(requestOptions).into(holder.img_user_icon);
            if (mList.get(position).getIs_my_praise() == 0) {
                holder.img_comment_like.setImageResource(R.mipmap.zan_select);
            } else {
                holder.img_comment_like.setImageResource(R.mipmap.zan_selected);
            }
            holder.img_comment_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.zan_select : R.mipmap.zan_selected);
            holder.img_comment_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = SpUtils.getString(context, "user_id", null);
                    String user_token = SpUtils.getString(context, "user_token", null);

                    int count_praise = mList.get(position).getCount_praise();
                    mList.get(position).setIs_my_praise(mList.get(position).getIs_my_praise() == 1 ? 0 : 1);
                    if (mList.get(position).getIs_my_praise() == 1) {
                        count_praise += 1;
                    } else if (count_praise > 0) {
                        count_praise -= 1;
                    }
                    mList.get(position).setCount_praise(count_praise);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataManager.getFromRemote().praise(context, user_id, user_token, 0, mList.get(position).getId())
                                    .subscribe(new Consumer<BaseBean>() {
                                        @Override
                                        public void accept(BaseBean baseBean) throws Exception {
                                            notifyDataSetChanged();
                                        }
                                    });
                        }
                    }).start();

                }
            });
        } else {
            SpannableString msp = new SpannableString(mList.get(position).getUser_nickname() + " 回复" + ":" +  mList.get(position).getContent());
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#FA800A")), 0, mList.get(position).getUser_nickname().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#FA800A")), 0, mList.get(position).getUser_nickname().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色为洋红色
            holder.user_content.setText(msp);

            holder.tv_time.setText(mList.get(position).getCreate_time());
            holder.tv_comment_like_num.setText(mList.get(position).getCount_praise() + "");
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.default_touxiang);
            requestOptions.placeholder(R.mipmap.default_touxiang);
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(requestOptions).into(holder.img_user_icon);
            if (mList.get(position).getIs_my_praise() == 0) {
                holder.img_comment_like.setImageResource(R.mipmap.zan_select);
            } else {
                holder.img_comment_like.setImageResource(R.mipmap.zan_selected);
            }
            holder.img_comment_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.zan_select : R.mipmap.zan_selected);
            holder.img_comment_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user_id = SpUtils.getString(context, "user_id", null);
                    String user_token = SpUtils.getString(context, "user_token", null);

                    int count_praise = mList.get(position).getCount_praise();
                    mList.get(position).setIs_my_praise(mList.get(position).getIs_my_praise() == 1 ? 0 : 1);
                    if (mList.get(position).getIs_my_praise() == 1) {
                        count_praise += 1;
                    } else if (count_praise > 0) {
                        count_praise -= 1;
                    }
                    mList.get(position).setCount_praise(count_praise);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataManager.getFromRemote().praise(context, user_id, user_token, 0, mList.get(position).getId())
                                    .subscribe(new Consumer<BaseBean>() {
                                        @Override
                                        public void accept(BaseBean baseBean) throws Exception {
                                            notifyDataSetChanged();
                                        }
                                    });
                        }
                    }).start();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_content, tv_time, tv_comment_like_num;
        CircleImageView img_user_icon;
        ImageView img_comment_like, medal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_content = itemView.findViewById(R.id.user_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_comment_like_num = itemView.findViewById(R.id.tv_comment_like_num);
            img_user_icon = itemView.findViewById(R.id.img_user_icon);
            img_comment_like = itemView.findViewById(R.id.img_comment_like);
            medal = itemView.findViewById(R.id.medal);
        }
    }
}
