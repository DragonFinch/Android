package com.iyoyogo.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iyoyogo.android.R;
import com.iyoyogo.android.bean.BaseBean;
import com.iyoyogo.android.bean.attention.AttentionBean;
import com.iyoyogo.android.bean.home.HomeBean;
import com.iyoyogo.android.bean.mine.center.YoJiContentBean;
import com.iyoyogo.android.model.DataManager;
import com.iyoyogo.android.ui.home.yoji.UserHomepageActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoxiu.AllCommentActivity;
import com.iyoyogo.android.ui.home.yoxiu.YoXiuDetailActivity;
import com.iyoyogo.android.utils.DensityUtil;
import com.iyoyogo.android.utils.GlideRoundTransform;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.widget.CircleImageView;
import com.iyoyogo.android.widget.PileLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class YoJiAttentionAdapter extends RecyclerView.Adapter<YoJiAttentionAdapter.Holder> implements View.OnClickListener {
    private List<HomeBean.DataBean.YojListBean> mList;
    private Context context;
    private String user_id;
    private String user_token;
    private RecyclerView recyclerView;
    private final Activity activity;
    private int yo_id;
    private String type;
    public YoJiAttentionAdapter(Context context, List<HomeBean.DataBean.YojListBean> mList, String type) {
        this.mList = mList;
        this.context = context;
        activity = (Activity) context;
        this.type=type;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.yoji_item_attention_recycler, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
            if (type.equals("attention")){
                holder.view_like.setVisibility(View.GONE);
            }
        holder.location_end.setText(mList.get(position).getP_end());
        yo_id = mList.get(position).getYo_id();
        user_id = SpUtils.getString(context, "user_id", null);
        user_token = SpUtils.getString(context, "user_token", null);
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic)
                .override(DensityUtil.dp2px(context, ViewGroup.LayoutParams.MATCH_PARENT), DensityUtil.dp2px(context, 200))
                .transform(new GlideRoundTransform(context, 8));
        Log.d("YoJiAttentionAdapter", "yo_id:" + yo_id);
        if (yo_id == 0) {
            holder.attention_user_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUser_id()));
                    context.startActivity(intent);
                }
            });
            holder.rl_top_content.setVisibility(View.GONE);
            holder.relative_img.setVisibility(View.GONE);
            holder.ll_like_list.setVisibility(View.GONE);
            holder.layout_comment.setVisibility(View.GONE);
            holder.layout_attention.setVisibility(View.VISIBLE);
            List<HomeBean.DataBean.YojListBean.List4Bean> list_4 = mList.get(position).getList_4();
            holder.attention_user_name.setText(mList.get(position).getUser_nickname());
            holder.attention_ji_count.setText(mList.get(position).getCount_yoj() + "");
            holder.attention_xiu_count.setText(mList.get(position).getCount_yox() + "");

            holder.tv_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (retryConnection != null) {
                        retryConnection.on_retry();
                        holder.tv_attention.setText("已关注");
                    }
                    DataManager.getFromRemote().addAttention(context,user_id, user_token, mList.get(position).getUser_id()).subscribe(new Consumer<AttentionBean>() {
                        @Override
                        public void accept(AttentionBean attentionBean) throws Exception {

                            notifyDataSetChanged();
                        }
                    });

                }
            });
            Glide.with(context).load(mList.get(position).getUser_logo()).apply(myOptions).into(holder.attention_user_icon);
            if (list_4 == null) {

            } else {

                if (list_4.size() == 1) {
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_two);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_three);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_four);
                    Glide.with(context).load(list_4.get(0).getFile_path()).apply(myOptions).into(holder.img_attention_one);
                    holder.img_attention_two.setClickable(false);
                    holder.img_attention_three.setClickable(false);
                    holder.img_attention_four.setClickable(false);
                    holder.img_attention_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(0).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(0).getYo_id());

                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                } else if (list_4.size() == 2) {

                    holder.img_attention_three.setClickable(false);
                    holder.img_attention_four.setClickable(false);
                    holder.img_attention_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(0).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    holder.img_attention_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(1).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(1).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(1).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_three);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_four);
                    Glide.with(context).load(list_4.get(0).getFile_path()).apply(myOptions).into(holder.img_attention_one);
                    Glide.with(context).load(list_4.get(1).getFile_path()).apply(myOptions).into(holder.img_attention_two);
                } else if (list_4.size() == 3) {
                    holder.img_attention_four.setClickable(false);
                    holder.img_attention_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(2).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(2).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(2).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    holder.img_attention_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(0).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    holder.img_attention_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(1).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(1).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(1).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_four);
                    Glide.with(context).load(list_4.get(0).getFile_path()).apply(myOptions).into(holder.img_attention_one);
                    Glide.with(context).load(list_4.get(1).getFile_path()).apply(myOptions).into(holder.img_attention_two);
                    Glide.with(context).load(list_4.get(2).getFile_path()).apply(myOptions).into(holder.img_attention_three);
                } else if (list_4.size() == 4) {
                    holder.img_attention_one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(0).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(0).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    holder.img_attention_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(1).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(1).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(1).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    holder.img_attention_three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int yo_type = mList.get(position).getList_4().get(2).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(2).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(2).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    holder.img_attention_four.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int yo_type = mList.get(position).getList_4().get(3).getYo_type();
                            if (yo_type == 1) {
                                Intent intent = new Intent(context, YoXiuDetailActivity.class);
                                intent.putExtra("id", list_4.get(3).getYo_id());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, YoJiDetailActivity.class);
                                intent.putExtra("yo_id", list_4.get(3).getYo_id());
                                context.startActivity(intent);
                            }
                        }
                    });
                    Glide.with(context).load(list_4.get(0).getFile_path()).apply(myOptions).into(holder.img_attention_one);
                    Glide.with(context).load(list_4.get(1).getFile_path()).apply(myOptions).into(holder.img_attention_two);
                    Glide.with(context).load(list_4.get(2).getFile_path()).apply(myOptions).into(holder.img_attention_three);
                    Glide.with(context).load(list_4.get(3).getFile_path()).apply(myOptions).into(holder.img_attention_four);
                } else if (list_4.size() == 0) {
                    holder.img_attention_one.setClickable(false);
                    holder.img_attention_two.setClickable(false);
                    holder.img_attention_three.setClickable(false);
                    holder.img_attention_four.setClickable(false);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_one);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_two);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_three);
                    Glide.with(context).load(R.mipmap.default_ic).apply(myOptions).into(holder.img_attention_four);
                }
            }


        } else {

            HomeBean.DataBean.YojListBean.UserInfoBean user_info = mList.get(position).getUser_info();
            Log.d("YoJiAttentionAdapter", user_info.getUser_nickname());
            if (mList.get(position).getUser_info().getUser_nickname() != null) {

                holder.user_name.setText(mList.get(position).getUser_info().getUser_nickname());

            } else {
                holder.user_name.setText("");
            }
            holder.user_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserHomepageActivity.class);
                    intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUser_info().getUser_id()));
                    context.startActivity(intent);
                }
            });
            holder.medal.setVisibility(View.VISIBLE);
            holder.img_level.setVisibility(View.VISIBLE);
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
            int user_level = mList.get(position).getUser_level();
            if (user_level == 0) {
                holder.img_level.setVisibility(View.INVISIBLE);
            } else if (user_level == 1) {
                mList.get(position).setUser_level(1);
                holder.img_level.setImageResource(R.mipmap.lv1);

            } else if (user_level == 2) {
                mList.get(position).setUser_level(2);
                holder.img_level.setImageResource(R.mipmap.lv2);
            } else if (user_level == 3) {
                mList.get(position).setUser_level(3);
                holder.img_level.setImageResource(R.mipmap.lv3);
            } else if (user_level == 4) {
                mList.get(position).setUser_level(4);
                holder.img_level.setImageResource(R.mipmap.lv4);
            } else if (user_level == 5) {
                mList.get(position).setUser_level(5);
                holder.img_level.setImageResource(R.mipmap.lv5);
            } else {
                holder.img_level.setVisibility(View.INVISIBLE);
            }
            holder.title.setText(mList.get(position).getTitle());
            holder.tv_cost.setText("￥" + mList.get(position).getCost() + "/人");
            holder.tv_day.setText(mList.get(position).getCount_dates() + "天");
            holder.tv_num_comment.setText("全部评论(" + mList.get(position).getCount_comment() + ")");
            holder.tv_num_like.setText("等" + mList.get(position).getCount_praise() + "人喜欢过");
            List<HomeBean.DataBean.YojListBean.CommentListBean> comment_list = mList.get(position).getComment_list();
            Log.d("YoJiAttentionAdapter", "comment_list.size():" + comment_list.size());
            if (comment_list.size() == 0) {
                holder.recycler_comment.setVisibility(View.GONE);
            } else {
                holder.recycler_comment.setVisibility(View.VISIBLE);
                YoJiListItemAdapter yoJiListItemAdapter = new YoJiListItemAdapter(context, comment_list);
                holder.recycler_comment.setLayoutManager(new LinearLayoutManager(context));
                holder.recycler_comment.setAdapter(yoJiListItemAdapter);
            }
            //全部评论的接口
            holder.tv_num_comment.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllCommentActivity.class);
                    intent.putExtra("id", mList.get(position).getYo_id());
                    context.startActivity(intent);
                }
            });

            holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() > 0 ? R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);
            if (mList.get(position).getIs_my_praise() == 0) {
                holder.dt_like.setImageResource(R.mipmap.datu_xihuan);
            } else {
                holder.dt_like.setImageResource(R.mipmap.yixihuan_xiangqing);
            }
            holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() == 0 ? R.mipmap.datu_xihuan : R.mipmap.yixihuan_xiangqing);
            yo_id = mList.get(position).getYo_id();
            holder.dt_like.setOnClickListener(new View.OnClickListener() {
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
                    holder.dt_like.setImageResource(mList.get(position).getIs_my_praise() > 0 ? R.mipmap.yixihuan_xiangqing : R.mipmap.datu_xihuan);

                    Log.d("Test", "dataBeans.get(0).getIs_my_like():" + mList.get(position).getIs_my_praise());
                    if (mList.get(position).getIs_my_praise() > 0) {
                        //由喜欢变为不喜欢，亮变暗
                        holder.dt_like.setImageResource(R.mipmap.datu_xihuan);
                        count_praises -= 1;
                        //设置点赞的数量
                        holder.tv_num_like.setText("等" + count_praises + "人喜欢过");
                        mList.get(position).setIs_my_praise(0);
                        mList.get(position).setCount_praise(String.valueOf(count_praises));

                    } else {
                        //由不喜欢变为喜欢，暗变亮
                        holder.dt_like.setImageResource(R.mipmap.yixihuan_xiangqing);
                        count_praises += 1;
                        //设置点赞的数量
                        holder.tv_num_like.setText("等" + count_praises + "人喜欢过");
                        mList.get(position).setIs_my_praise(1);
                        mList.get(position).setCount_praise(String.valueOf(count_praises));

                    }
                    DataManager.getFromRemote().praise(context,user_id, user_token, mList.get(position).getYo_id(), 0)
                            .subscribe(new Consumer<BaseBean>() {
                                @Override
                                public void accept(BaseBean baseBean) throws Exception {
                                }
                            });
                }
            });
            holder.view_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initPopup(holder);
                }
            });
            holder.rl_top_content.setVisibility(View.VISIBLE);
            holder.relative_img.setVisibility(View.VISIBLE);
            holder.ll_like_list.setVisibility(View.VISIBLE);
            holder.layout_comment.setVisibility(View.VISIBLE);
            holder.layout_attention.setVisibility(View.GONE);
            int is_highquality = mList.get(position).getQuality_type();
            if (is_highquality == 1) {
                holder.typeImageView.setVisibility(View.VISIBLE);
                holder.typeImageView.setImageResource(R.mipmap.youzhi);
            } else if (is_highquality == 2) {
                holder.typeImageView.setVisibility(View.VISIBLE);
                holder.typeImageView.setImageResource(R.mipmap.jingxuan);
            } else {
                holder.typeImageView.setVisibility(View.INVISIBLE);
            }

            Log.d("YoJiAdapter", mList.get(position).getFile_path());
            Glide.with(context).load(mList.get(position).getFile_path()).apply(myOptions).into(holder.zuji_image);
            holder.location.setText(mList.get(position).getP_start());
            holder.num_look.setText(mList.get(position).getCount_view() + "");
            Log.d("YoJiAdapter", "mList.get(position).getUsers_praise():" + mList.get(position).getUsers_praise());
            LayoutInflater inflater = LayoutInflater.from(context);
            if (mList.get(position).getUsers_praise().size() == 0) {
                holder.pile_layout.setVisibility(View.GONE);
                holder.tv_num_like.setVisibility(View.GONE);
            } else {
                List<HomeBean.DataBean.YojListBean> user_icons = new ArrayList<>();
                if (mList.get(position).getUsers_praise().size() <= 10) {
                    for (int i = 0; i < mList.get(position).getUsers_praise().size(); i++) {
                        user_icons.addAll(mList);
                        com.iyoyogo.android.view.CircleImageView imageView = (com.iyoyogo.android.view.CircleImageView) inflater.inflate(R.layout.item_head_image, holder.pile_layout, false);
                        Glide.with(context).load(user_icons.get(position).getUsers_praise().get(i).getUser_logo()).into(imageView);
                        holder.pile_layout.addView(imageView);
                        int finalI = i;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                            Toast.makeText(context, "mList.get(position).getUsers_praise().get(i).getUser_id():" + mList.get(position).getUsers_praise().get(finalI).getUser_id(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, UserHomepageActivity.class);
                                intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUsers_praise().get(finalI).getUser_id()));
                                context.startActivity(intent);
                            }
                        });
                    }
                } else {
                    for (int i = 0; i < 10 && i < user_icons.size(); i++) {
                        user_icons.add(mList.get(position));
                        com.iyoyogo.android.view.CircleImageView imageView = (com.iyoyogo.android.view.CircleImageView) inflater.inflate(R.layout.item_head_image, holder.pile_layout, false);
                        Glide.with(context).load(user_icons.get(position).getUsers_praise().get(i).getUser_logo()).into(imageView);
                        holder.pile_layout.addView(imageView);
                        int finalI = i;
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                            Toast.makeText(context, "mList.get(position).getUsers_praise().get(i).getUser_id():" + mList.get(position).getUsers_praise().get(finalI).getUser_id(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, UserHomepageActivity.class);
                                intent.putExtra("yo_user_id", String.valueOf(mList.get(position).getUsers_praise().get(finalI).getUser_id()));
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.default_touxiang)
                    .error(R.mipmap.default_touxiang);

            String user_logo = mList.get(position).getUser_info().getUser_logo();

            Log.d("YoJiAdapter", user_logo);
            Glide.with(context).load(mList.get(position).getUser_info().getUser_logo())
                    .apply(requestOptions)
                    .into(holder.user_icon);

        }


        holder.itemView.setTag(position);

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
        ImageView zuji_image, typeImageView, medal, img_level, dt_like, img_attention_one, img_attention_two, img_attention_three, img_attention_four;
        CircleImageView user_icon, attention_user_icon;
        TextView num_look, location_end, user_name, title, tv_cost, location, tv_day, tv_num_like, tv_num_comment, attention_user_name, attention_xiu_count, attention_ji_count, tv_attention;
        RelativeLayout view_like, rl_top_content, relative_img;
        LinearLayout layout_attention, layout_comment, ll_like_list;
        PileLayout pile_layout;
        RecyclerView recycler_comment;

        public Holder(@NonNull View itemView) {
            super(itemView);
            medal = itemView.findViewById(R.id.medal);
            img_level = itemView.findViewById(R.id.img_level);
            location_end = itemView.findViewById(R.id.location_end);
            attention_user_name = itemView.findViewById(R.id.attention_user_name);
            attention_xiu_count = itemView.findViewById(R.id.attention_xiu_count);
            attention_ji_count = itemView.findViewById(R.id.attention_ji_count);
            tv_attention = itemView.findViewById(R.id.tv_attention);
            img_attention_one = itemView.findViewById(R.id.img_attention_one);
            img_attention_two = itemView.findViewById(R.id.img_attention_two);
            img_attention_three = itemView.findViewById(R.id.img_attention_three);
            img_attention_four = itemView.findViewById(R.id.img_attention_four);
            layout_comment = itemView.findViewById(R.id.layout_comment);
            attention_user_icon = itemView.findViewById(R.id.attention_user_icon);
            layout_attention = itemView.findViewById(R.id.layout_attention);
            ll_like_list = itemView.findViewById(R.id.ll_like_list);
            relative_img = itemView.findViewById(R.id.relative_img);
            rl_top_content = itemView.findViewById(R.id.rl_top_content);
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
        }
    }

    private void initPopup(Holder holder) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 125), DensityUtil.dp2px(context, 50), true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        TextView tv_dislike = view.findViewById(R.id.tv_dislike);
        View line = view.findViewById(R.id.line);
        line.setVisibility(View.GONE);
        TextView tv_report = view.findViewById(R.id.tv_report);
        tv_report.setVisibility(View.GONE);
        user_id = SpUtils.getString(context, "user_id", null);
        user_token = SpUtils.getString(context, "user_token", null);
        tv_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                initDislike();

            }
        });
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(holder.view_like, DensityUtil.dp2px(context, -95), DensityUtil.dp2px(context, 10));
    }

    private void initDislike() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popwindow_not_like, null);
        PopupWindow popupWindow = new PopupWindow(view, DensityUtil.dp2px(context, 300), DensityUtil.dp2px(context, 230), true);
        TextView dislike_this_kind = view.findViewById(R.id.dislike_this_kind);
        TextView dislike_this_item = view.findViewById(R.id.dislike_this_item);
        backgroundAlpha(0.6f);
        dislike_this_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote()
                        .dislike(context,user_id, user_token, yo_id, 1)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                popupWindow.dismiss();
                                if (onRetryClickListener != null) {
                                    onRetryClickListener.onretry();
                                }

                            }
                        });
            }
        });
        dislike_this_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getFromRemote()
                        .dislike(context,user_id, user_token, yo_id, 2)
                        .subscribe(new Consumer<BaseBean>() {
                            @Override
                            public void accept(BaseBean baseBean) throws Exception {
                                popupWindow.dismiss();
                                if (onRetryClickListener != null) {
                                    onRetryClickListener.onretry();
                                }
                            }
                        });

            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popupWindow.showAtLocation(activity.findViewById(R.id.activity_main), Gravity.CENTER, 0, 0);
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

    interface OnRetryConnection {
        void on_retry();
    }

    public interface OnRetryClickListener {
        void onretry();
    }

    private OnRetryClickListener onRetryClickListener;

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    OnRetryConnection retryConnection;

    public void onItemRetryOnClickListener(OnRetryConnection retryConnection) {
        this.retryConnection = retryConnection;
    }
}
