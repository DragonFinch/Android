package com.iyoyogo.android.adapter.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.search.KeywordBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.mine.homepage.Personal_homepage_Activity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.PileLayout;

import java.util.List;

import io.reactivex.functions.Consumer;

public class SearchYoJiListHorizontalAdapter extends RecyclerView.Adapter<SearchYoJiListHorizontalAdapter.Holder> implements View.OnClickListener {
    private List<KeywordBean.DataBean.YojListBean> mList;
    private Context context;

    public SearchYoJiListHorizontalAdapter(Context context, List<KeywordBean.DataBean.YojListBean> data) {
        this.mList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.seatch_yoji_item_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setoncli != null) {
                    setoncli.setoncli(position);
                }
            }
        });

        holder.typeImageView.setVisibility(View.INVISIBLE);
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic)
                .override(DensityUtil.dp2px(context, ViewGroup.LayoutParams.MATCH_PARENT), DensityUtil.dp2px(context, 200))
                .transform(new GlideRoundTransform(context, 8));
        Glide.with(context).load(mList.get(position).getFile_path()).apply(myOptions).into(holder.zuji_image);
        holder.location.setText(mList.get(position).getP_start());
        holder.num_look.setText(mList.get(position).getCount_view() + "");
        LayoutInflater inflater = LayoutInflater.from(context);
        Glide.with(context).load(mList.get(position).getUser_info().getUser_logo()).into(holder.user_icon);
        holder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());
        holder.title.setText(mList.get(position).getTitle());
        holder.tv_cost.setText(" ￥" + mList.get(position).getCost() + "/人");
        holder.tv_day.setText(mList.get(position).getCount_dates() + "天");
        holder.tv_num_comment.setText("全部评论(" + mList.get(position).getCount_comment() + ")");
        holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
        //holder.tv_num_like.setVisibility(View.GONE);
        List<KeywordBean.DataBean.YojListBean.CommentListBeanX> comment_list = mList.get(position).getComment_list();
        //Log.e("ada", "onBindViewHolder: "+comment_list.get(position).getContent());
        //  Log.e("ada", "onBindViewHolder: "+comment_list.get(position).getUser_nickname());
        SearchYoJiListInnerAdapter adapter = new SearchYoJiListInnerAdapter(context, comment_list);
        Log.d("YoJiAdapter", "comment_list:" + comment_list.size());
        holder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        holder.recycler_comment.setAdapter(adapter);
        holder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, UserHomepageActivity.class);
                intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUser_info().getUser_id()));
                context.startActivity(intent);
            }
        });
        //全部评论的接口
        holder.tv_num_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllCommentActivity.class);
                intent.putExtra("id", mList.get(position).getYo_id());
                context.startActivity(intent);
            }
        });

        holder.medal.setVisibility(View.VISIBLE);
        holder.img_level.setVisibility(View.VISIBLE);
        String partner_type = mList.get(position).getUser_info().getPartner_type();
        if (partner_type.equals("0")) {
            mList.get(position).getUser_info().setPartner_type("0");
            holder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type.equals("1")) {
            mList.get(position).getUser_info().setPartner_type("1");
            holder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type.equals("2")) {
            mList.get(position).getUser_info().setPartner_type("2");
            holder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type.equals("3")) {
            mList.get(position).getUser_info().setPartner_type("3");
            holder.medal.setImageResource(R.mipmap.kol);
        } else {
            holder.medal.setVisibility(mList.get(position).getUser_info().getPartner_type().equals("0") ? View.INVISIBLE : View.VISIBLE);
        }

        int user_level = mList.get(position).getUser_info().getUser_level();
        if (user_level == 0) {
            holder.img_level.setVisibility(View.GONE);
        } else if (user_level == 1) {
            mList.get(position).getUser_info().setUser_level(1);
            holder.img_level.setImageResource(R.mipmap.lv1);

        } else if (user_level == 2) {
            mList.get(position).getUser_info().setUser_level(2);
            holder.img_level.setImageResource(R.mipmap.lv2);
        } else if (user_level == 3) {
            mList.get(position).getUser_info().setUser_level(3);
            holder.img_level.setImageResource(R.mipmap.lv3);
        } else if (user_level == 4) {
            mList.get(position).getUser_info().setUser_level(4);
            holder.img_level.setImageResource(R.mipmap.lv4);
        } else if (user_level == 5) {
            mList.get(position).getUser_info().setUser_level(5);
            holder.img_level.setImageResource(R.mipmap.lv5);
        } else {
            holder.img_level.setVisibility(View.INVISIBLE);
        }

        LayoutInflater inflater1 = LayoutInflater.from(context);
        if (mList.get(position).getUsers_praise().size() == 0) {
            holder.pile_layout.setVisibility(View.GONE);
            holder.tv_num_like.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < mList.get(position).getUsers_praise().size(); i++) {
                com.iyoyogo.android.view.CircleImageView imageView = (com.iyoyogo.android.view.CircleImageView) inflater1.inflate(R.layout.item_head_image, holder.pile_layout, false);
                Glide.with(context).load(mList.get(position).getUsers_praise().get(i).getUser_logo()).into(imageView);
                holder.pile_layout.addView(imageView);
                int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Personal_homepage_Activity.class);
                        intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUsers_praise().get(finalI).getUser_id()));
                        context.startActivity(intent);
                    }
                });

            }
        }
        holder.dt_like.setImageResource(mList.get(position).isIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        int yo_id = mList.get(position).getYo_id();
        holder.dt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);
                int count_praise = mList.get(position).getCount_praise();
                mList.get(position).setIs_my_praise(mList.get(position).isIs_my_praise() == 1 ? 0 : 1);
                if (mList.get(position).isIs_my_praise() == 1) {
                    count_praise += 1;
                    mList.get(position).setCount_praise(count_praise);
                    mList.get(position).setIs_my_praise(1);
                    holder.tv_num_like.setText("等(" + count_praise + ")人喜欢过");
                    holder.dt_like.setImageResource(mList.get(position).isIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);

                } else {
                    count_praise -= 1;
                    mList.get(position).setIs_my_praise(0);
                    mList.get(position).setCount_praise(count_praise);
                    holder.tv_num_like.setText("等(" + count_praise + ")人喜欢过");
                    holder.dt_like.setImageResource(mList.get(position).isIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);

                }
                DataManager.getFromRemote().praise(context,user_id, user_token, yo_id, 0).subscribe(new Consumer<BaseBean>() {
                    @Override
                    public void accept(BaseBean baseBean) throws Exception {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }

    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v, (Integer) v.getTag());
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView zuji_image, typeImageView, dt_like, medal, img_level;
        CircleImageView user_icon;
        TextView num_look, user_name, title, tv_cost, location, tv_day, tv_num_like, tv_num_comment;
        RelativeLayout view_like;
        PileLayout pile_layout;
        RecyclerView recycler_comment;

        public Holder(@NonNull View itemView) {
            super(itemView);
            zuji_image = itemView.findViewById(R.id.zuji_image);
            tv_num_comment = itemView.findViewById(R.id.tv_num_comment);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            tv_num_like = itemView.findViewById(R.id.tv_num_like);
            pile_layout = itemView.findViewById(R.id.pile_layout);
            tv_day = itemView.findViewById(R.id.tv_day);
            location = itemView.findViewById(R.id.location);
            title = itemView.findViewById(R.id.title);
            tv_cost = itemView.findViewById(R.id.tv_cost);
            view_like = itemView.findViewById(R.id.view_like);
            num_look = itemView.findViewById(R.id.num_look);
            user_name = itemView.findViewById(R.id.user_name);
            typeImageView = itemView.findViewById(R.id.img_type);
            user_icon = itemView.findViewById(R.id.user_icon);
            dt_like = itemView.findViewById(R.id.dt_like);
            medal = itemView.findViewById(R.id.medal);
            img_level = itemView.findViewById(R.id.user_level_img);
        }
    }

    private setoncli setoncli;

    public void setSetoncli(SearchYoJiListHorizontalAdapter.setoncli setoncli) {
        this.setoncli = setoncli;
    }

    public interface setoncli {
        void setoncli(int p);

        void set(int position);
    }
}
