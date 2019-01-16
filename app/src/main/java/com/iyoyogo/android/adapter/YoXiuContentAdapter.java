package com.iyoyogo.android.adapter;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.mine.center.YoXiuContentBean;
import com.iyoyogo.android.bean.yoxiu.YouXiuListBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuListActivity;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * yo秀用户主页的适配器
 */
public class YoXiuContentAdapter extends RecyclerView.Adapter<YoXiuContentAdapter.ViewHolder> implements View.OnClickListener {
    List<YoXiuContentBean.DataBean.ListBean> mList;
    private Context context;
    private int yo_id;

    public YoXiuContentAdapter(Context context, List<YoXiuContentBean.DataBean.ListBean> list) {
        this.context=context;
        this.mList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.yoxiu_content_recycler, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.tv_yoxiu_desc.setText(mList.get(position).getPosition_name());
        viewHolder.num_like.setText(mList.get(position).getCount_praise() + "");
        viewHolder.user_name.setText(mList.get(position).getUser_nickname());
        viewHolder.comment_all.setText("全部评论(" + mList.get(position).getCount_comment() + ")");
        viewHolder.num_browse.setText(mList.get(position).getCount_view()+"");
        int file_type = mList.get(position).getFile_type();
        RequestOptions requestOptions1 = new RequestOptions().centerCrop().transform(new GlideRoundTransform(context, 8));
        requestOptions1.placeholder(R.mipmap.default_touxiang);
        requestOptions1.error(R.mipmap.default_touxiang);
        if (file_type == 2) {
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(requestOptions1).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
            viewHolder.img_video.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(requestOptions1).into(viewHolder.user_icon);
            RequestOptions myOptions = new RequestOptions()
                    .centerCrop()
                    .transform(new GlideRoundTransform(context, 8));
            Glide.with(context).load(mList.get(position).getFile_path())
                    .apply(myOptions).into(viewHolder.img_yoxiu);
        }
        viewHolder.tv_yoxiu_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoXiuListActivity.class);
                intent.putExtra("position", mList.get(position).getPosition_name());
                intent.putExtra("type","attention");
                context.startActivity(intent);
            }
        });
        viewHolder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
        List<YoXiuContentBean.DataBean.ListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
        YoXiuContentItemAdapter adapter = new YoXiuContentItemAdapter(context, comment_list);
        viewHolder.recycler_comment.setAdapter(adapter);

        viewHolder.medal.setVisibility(View.VISIBLE);
        viewHolder.img_level.setVisibility(View.VISIBLE);
        String partner_type = mList.get(position).getPartner_type();
        if (partner_type .equals("0") ) {
            mList.get(position).setPartner_type("0");
            viewHolder.medal.setVisibility(View.INVISIBLE);
        } else if (partner_type .equals("1") ) {
            mList.get(position).setPartner_type("1");
            viewHolder.medal.setImageResource(R.mipmap.daren);
        } else if (partner_type .equals("2") ) {
            mList.get(position).setPartner_type("2");
            viewHolder.medal.setImageResource(R.mipmap.hongren);
        } else if (partner_type .equals("3") ) {
            mList.get(position).setPartner_type("3");
            viewHolder.medal.setImageResource(R.mipmap.kol);
        } else {
            viewHolder.medal.setVisibility(mList.get(position).getPartner_type() .equals("0")  ? View.INVISIBLE : View.VISIBLE);
        }

        int user_level = mList.get(position).getUser_level();
        if (user_level == 0) {
            viewHolder.img_level.setVisibility(View.GONE);
        } else if (user_level == 1) {
            mList.get(position).setUser_level(1);
            viewHolder.img_level.setImageResource(R.mipmap.lv1);

        } else if (user_level == 2) {
            mList.get(position).setUser_level(2);
            viewHolder.img_level.setImageResource(R.mipmap.lv2);
        } else if (user_level == 3) {
            mList.get(position).setUser_level(3);
            viewHolder.img_level.setImageResource(R.mipmap.lv3);
        } else if (user_level == 4) {
            mList.get(position).setUser_level(4);
            viewHolder.img_level.setImageResource(R.mipmap.lv4);
        } else if (user_level == 5) {
            mList.get(position).setUser_level(5);
            viewHolder.img_level.setImageResource(R.mipmap.lv5);
        } else {
            viewHolder.img_level.setVisibility(View.INVISIBLE);
        }


        /*viewHolder.recycler_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = mList.get(position).getId();
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });*/
        viewHolder.img_yoxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getId();
                int user_id = mList.get(position).getUser_id();
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id",id );
                intent.putExtra("yo_id", user_id+"");
                context.startActivity(intent);
            }
        });

        viewHolder.num_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getId();
                int user_id = mList.get(position).getUser_id();
                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("yo_id", user_id);
                context.startActivity(intent);
            }
        });


        viewHolder.comment_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getId();
                Intent intent = new Intent(context, AllCommentActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
        if (mList.get(position).getIs_my_like() == 0) {
            viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
        } else {
            viewHolder.img_like.setImageResource(R.mipmap.yixihuan_xiangqing);
        }
        viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
        yo_id = mList.get(position).getId();

        viewHolder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              String user_id = SpUtils.getString(context, "user_id", null);
//                if (user_id.equals(String.valueOf(mList.get(position).getUser_id()))){
//
//                }else {
//                    int yo_user_id = mList.get(position).getUser_id();
//                    Intent intent = new Intent(context, UserHomepageActivity.class);
//                    intent.putExtra("yo_user_id", String.valueOf(yo_user_id));
//                    context.startActivity(intent);
//                }
            }
        });



        viewHolder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = SpUtils.getString(context, "user_id", null);
                String user_token = SpUtils.getString(context, "user_token", null);

               /* Drawable like = getResources().getDrawable(
                        R.mipmap.xihuan_xiangqing);
                Drawable liked = getResources().getDrawable(
                        R.mipmap.yixihuan_xiangqing);*/
                String count_praise = mList.get(position).getCount_praise();
                int count_praises = Integer.parseInt(count_praise);
                viewHolder.img_like.setImageResource(mList.get(position).getIs_my_like() > 0 ? R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);

                Log.d("Test", "dataBeans.get(0).getIs_my_like():" + mList.get(position).getIs_my_like());
                if (mList.get(position).getIs_my_like() > 0) {
                    //由喜欢变为不喜欢，亮变暗
                    viewHolder.img_like.setImageResource(R.mipmap.datu_xihuan);
                    count_praises -= 1;
                    //设置点赞的数量
                    viewHolder.num_like.setText(count_praises + "");
                    mList.get(position).setIs_my_like(0);
                    mList.get(position).setCount_praise(String.valueOf(count_praises));

                } else {
                    //由不喜欢变为喜欢，暗变亮
                    viewHolder.img_like.setImageResource(R.mipmap.yixihuan_xiangqing);
                    count_praises += 1;
                    //设置点赞的数量
                    viewHolder.num_like.setText(count_praises + "");
                    mList.get(position).setIs_my_like(1);
                    mList.get(position).setCount_praise(String.valueOf(count_praises));

                }
                DataManager.getFromRemote().praise(user_id, user_token, mList.get(position).getId(), 0)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                            }
                        });

            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v, (Integer) v.getTag());
                }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_yoxiu_desc, num_like, user_name, comment_all,num_browse;
        ImageView img_yoxiu, img_like, img_video,user_level_img,medal,img_level;
        CircleImageView user_icon;
        RecyclerView recycler_comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_yoxiu_desc = itemView.findViewById(R.id.tv_yoxiu_desc);
            user_level_img = itemView.findViewById(R.id.user_level_img);
            num_like = itemView.findViewById(R.id.num_like);
            user_name = itemView.findViewById(R.id.tv_user_name);
            comment_all = itemView.findViewById(R.id.comment_all);
            user_icon = itemView.findViewById(R.id.user_icon);
            img_like = itemView.findViewById(R.id.img_like);
            recycler_comment = itemView.findViewById(R.id.recycler_comment);
            img_yoxiu = itemView.findViewById(R.id.img_yoxiu);
            img_video = itemView.findViewById(R.id.img_video);
            medal = itemView.findViewById(R.id.medal);
            img_level =itemView.findViewById(R.id.user_level_img);
            num_browse =itemView.findViewById(R.id.num_browse);

        }
    }
}
