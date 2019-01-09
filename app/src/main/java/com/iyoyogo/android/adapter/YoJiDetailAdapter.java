package com.iyoyogo.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.iyoyogo.android.bean.yoji.detail.YoJiDetailBean;
import com.iyoyogo.android.ui.home.yoji.YoJiDetailActivity;
import com.iyoyogo.android.ui.home.yoji.YoJiPictureActivity;
import com.iyoyogo.android.utils.RoundTransform;
import com.iyoyogo.android.widget.FlowGroupView;


import java.util.ArrayList;
import java.util.List;

/**
 * yo记详情的适配器
 */
public class YoJiDetailAdapter extends RecyclerView.Adapter<YoJiDetailAdapter.Holder> implements View.OnClickListener {
    List<YoJiDetailBean.DataBean.ListBean> mList;
    private Context context;
    private boolean isShow;
    String count_praise;
    String count_comment;
    int is_my_praise; int is_my_collect;
    //改变显示删除的imageview，通过定义变量isShow去接收变量isManager
    public void changetShowDelImage(boolean isShow) {
        this.isShow = isShow;
        notifyDataSetChanged();
    }

    public YoJiDetailAdapter(Context context, List<YoJiDetailBean.DataBean.ListBean> data, String count_praise, String count_comment, int is_my_praise, int is_my_collect) {
        this.mList = data;
        this.context = context;
        this.count_praise = count_praise;
        this.count_comment = count_comment;
        this.is_my_praise = is_my_praise;
        this.is_my_collect = is_my_collect;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoji_detail, viewGroup, false);
        Holder holder = new Holder(view);
        view.setOnClickListener(this);
        return holder;
    }

    private void addTextView(FlowGroupView flowGroupView, String str, int type) {
        TextView child = new TextView(context);

        child.setCompoundDrawablePadding(4);


        ViewGroup.MarginLayoutParams params =
                new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        params.setMargins(1, 15, 15, 15);
        child.setLayoutParams(params);
        if (type == 1) {
            child.setBackgroundResource(R.drawable.label_bg_fkzn);
            child.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.make), null, null, null);
            child.setText(str);
            child.setTextColor(Color.parseColor("#5BCBF5"));

        } else if (type == 2) {
            child.setBackgroundResource(R.drawable.label_bg_deserve_to_do);
            child.setText(str);
            child.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.donot), null, null, null);
            child.setTextColor(Color.parseColor("#E0FF6100"));
        } else if (type == 3) {
            child.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.self), null, null, null);
            child.setBackgroundResource(R.drawable.label_bg_exclusive);
            child.setText(str);
            child.setTextColor(Color.parseColor("#ff8484"));
        }

//        initEvents(child);//监听
        flowGroupView.addView(child);

    }

    private void initEvents(TextView child) {
        child.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                //话题内容

                Toast.makeText(context, child.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundTransform(context, 8));

//        Glide.with(context).load(mList.get(i).getThumbnail_pic_s()).apply(requestOptions).into(holder.zuji_image);
        holder.tv_position_name.setText(mList.get(position).getPosition_name());
        String start_date = mList.get(position).getStart_date();
        String end_date = mList.get(position).getEnd_date();
        int start_time = Integer.parseInt(start_date.replaceAll("-", ""));
        Log.d("YoJiDetailAdapter", "start_time:" + start_time);
        int end_time = Integer.parseInt(end_date.replaceAll("-", ""));
        Log.d("YoJiDetailAdapter", "end_time:" + end_time);
        int time_date = end_time - start_time;
        holder.tv_time.setText(mList.get(position).getCount_dates() + "天");
        holder.create_time.setText(start_date);


        List<String> logos = mList.get(position).getLogos();
        int size = logos.size();

        if (position == 0) {
            holder.flow.setVisibility(View.VISIBLE);
            loadData(holder, logos, size, position);

        } else {
            holder.img_water_mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isShow) {
                        changetShowDelImage(true);

                    } else {
                        changetShowDelImage(false);
                    }
                }
            });
            if (isShow) {
                List<YoJiDetailBean.DataBean.ListBean.LabelsBean> labels = mList.get(position).getLabels();
                holder.flow.removeAllViews();
                for (int i = 0; i < labels.size(); i++) {
                    addTextView(holder.flow, labels.get(i).getLabel(), labels.get(i).getType());
                }
                holder.flow.setVisibility(View.VISIBLE);
                loadData(holder, logos, size, position);
                if (position == mList.size() - 1) {
                    holder.plane.setVisibility(View.GONE);
                }
            } else {
                holder.flow.setVisibility(View.GONE);
                holder.picture_count_one.setVisibility(View.GONE);
                holder.picture_count_two.setVisibility(View.GONE);
                holder.picture_count_three.setVisibility(View.GONE);
                holder.picture_count_four.setVisibility(View.GONE);
                holder.picture_count_five.setVisibility(View.GONE);
                if (position == mList.size() - 1) {
                    holder.plane.setVisibility(View.GONE);
                }
            }
        }

        if (OnPlayListener != null) {
            OnPlayListener.getData(holder, position);
        }
        if (position == mList.size() - 1) {
            holder.plane.setVisibility(View.GONE);
        }
        holder.itemView.setTag(position);

    }

    private void loadData(@NonNull Holder holder, List<String> logos, int size, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.default_ic)
                .error(R.mipmap.default_ic);
        List<YoJiDetailBean.DataBean.ListBean.LabelsBean> labels = mList.get(position).getLabels();
        holder.flow.removeAllViews();
        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i).getLabel();
            Log.d("YoJiDetailAdapter", label);
            addTextView(holder.flow, labels.get(i).getLabel(), labels.get(i).getType());
        }
        if (size == 1) {
            holder.flow.setVisibility(View.VISIBLE);
            holder.picture_count_one.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).apply(requestOptions).into(holder.img_count_one_one);
            holder.img_count_one_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 0);
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    context.startActivity(intent);
                }
            });
        } else if (size == 2) {
            holder.flow.setVisibility(View.VISIBLE);
            holder.picture_count_two.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).apply(requestOptions).into(holder.img_count_two_one);
            Glide.with(context).load(logos.get(1)).apply(requestOptions).into(holder.img_count_two_two);
            holder.img_count_two_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 0);
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    context.startActivity(intent);
                }
            });
            holder.img_count_two_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 1);
                    context.startActivity(intent);
                }
            });
        } else if (size == 3) {
            holder.flow.setVisibility(View.VISIBLE);

            holder.picture_count_three.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).apply(requestOptions).into(holder.img_count_three_one);
            Glide.with(context).load(logos.get(1)).apply(requestOptions).into(holder.img_count_three_two);
            Glide.with(context).load(logos.get(2)).apply(requestOptions).into(holder.img_count_three_three);
            holder.img_count_three_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 0);
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    context.startActivity(intent);
                }
            });
            holder.img_count_three_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 1);
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    context.startActivity(intent);
                }
            });
            holder.img_count_three_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 2);
                    context.startActivity(intent);
                }
            });
        } else if (size == 4) {
            holder.flow.setVisibility(View.VISIBLE);
            holder.flow.setVisibility(View.VISIBLE);
            holder.picture_count_four.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).apply(requestOptions).into(holder.img_count_four_one);
            Glide.with(context).load(logos.get(1)).apply(requestOptions).into(holder.img_count_four_two);
            Glide.with(context).load(logos.get(2)).apply(requestOptions).into(holder.img_count_four_three);
            Glide.with(context).load(logos.get(3)).apply(requestOptions).into(holder.img_count_four_four);
            holder.img_count_four_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 0);
                    context.startActivity(intent);
                }
            });
            holder.img_count_four_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 1);
                    context.startActivity(intent);
                }
            });
            holder.img_count_four_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);

                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 2);
                    context.startActivity(intent);
                }
            });
            holder.img_count_four_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 3);
                    context.startActivity(intent);
                }
            });
        } else if (size == 5) {
            holder.flow.setVisibility(View.VISIBLE);
            holder.picture_count_five.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).apply(requestOptions).into(holder.img_count_five_one);
            Glide.with(context).load(logos.get(1)).apply(requestOptions).into(holder.img_count_five_two);
            Glide.with(context).load(logos.get(2)).apply(requestOptions).into(holder.img_count_five_three);
            Glide.with(context).load(logos.get(3)).apply(requestOptions).into(holder.img_count_five_four);
            Glide.with(context).load(logos.get(4)).apply(requestOptions).into(holder.img_count_five_five);
            holder.img_count_five_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 0);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 1);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 2);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 3);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 4);
                    context.startActivity(intent);
                }
            });
        } else if (size > 5) {
            holder.flow.setVisibility(View.VISIBLE);
            holder.picture_count_five.setVisibility(View.VISIBLE);
            Glide.with(context).load(logos.get(0)).apply(requestOptions).into(holder.img_count_five_one);
            Glide.with(context).load(logos.get(1)).apply(requestOptions).into(holder.img_count_five_two);
            Glide.with(context).load(logos.get(2)).apply(requestOptions).into(holder.img_count_five_three);
            Glide.with(context).load(logos.get(3)).apply(requestOptions).into(holder.img_count_five_four);
            Glide.with(context).load(logos.get(4)).apply(requestOptions).into(holder.img_count_five_five);
            holder.tv_pic_count.setVisibility(View.VISIBLE);
            holder.tv_pic_count.setText(size + "");
            holder.img_count_five_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 0);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 1);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 2);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 3);
                    context.startActivity(intent);
                }
            });
            holder.img_count_five_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                   intent.putExtra("yo_id", mList.get(position).getYo_id()+"");
                    intent.putExtra("is_my_collect", is_my_collect);
                    intent.putExtra("count_praise", is_my_praise);
                    intent.putExtra("count_praise", count_praise);
                    intent.putExtra("count_collect", count_comment);
                    intent.setClass(context, YoJiPictureActivity.class);
                    intent.putStringArrayListExtra("logos", (ArrayList<String>) (ArrayList<String>) mList.get(position).getLogos_big());
                    intent.putExtra("position", 4);
                    context.startActivity(intent);
                }
            });
        }
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

    public interface OnPlayListener {
        void getData(Holder holder, int position);
    }

    private OnPlayListener OnPlayListener;

    public void setOnItemDataListener(OnPlayListener onPlayListener) {
        this.OnPlayListener = onPlayListener;
    }

    public class Holder extends RecyclerView.ViewHolder {
        FlowGroupView flow;

        ImageView zuji_image, plane, img_water_mark,
                img_count_one_one, img_count_two_one, img_count_three_one, img_count_four_one, img_count_five_one,
                img_count_two_two, img_count_three_two, img_count_four_two, img_count_five_two,
                img_count_three_three, img_count_four_three, img_count_five_three,
                img_count_four_four, img_count_five_four, img_count_five_five;
        TextView tv_position_name, tv_time, create_time, tv_pic_count;
        RelativeLayout picture_count_one, picture_count_two, picture_count_three, picture_count_four, picture_count_five;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_water_mark = itemView.findViewById(R.id.img_water_mark);
            flow = itemView.findViewById(R.id.flow);
            tv_pic_count = itemView.findViewById(R.id.tv_pic_count);
            tv_time = itemView.findViewById(R.id.tv_time);
            create_time = itemView.findViewById(R.id.create_time);
            img_count_one_one = itemView.findViewById(R.id.img_count_one_one);
            img_count_two_one = itemView.findViewById(R.id.img_count_two_one);
            img_count_three_one = itemView.findViewById(R.id.img_count_three_one);
            img_count_four_one = itemView.findViewById(R.id.img_count_four_one);
            img_count_five_one = itemView.findViewById(R.id.img_count_five_one);
            img_count_two_two = itemView.findViewById(R.id.img_count_two_two);
            img_count_three_two = itemView.findViewById(R.id.img_count_three_two);
            img_count_four_two = itemView.findViewById(R.id.img_count_four_two);
            img_count_five_two = itemView.findViewById(R.id.img_count_five_two);
            img_count_three_three = itemView.findViewById(R.id.img_count_three_three);
            img_count_four_three = itemView.findViewById(R.id.img_count_four_three);
            img_count_five_three = itemView.findViewById(R.id.img_count_five_three);
            img_count_four_four = itemView.findViewById(R.id.img_count_four_four);
            img_count_five_four = itemView.findViewById(R.id.img_count_five_four);
            img_count_five_five = itemView.findViewById(R.id.img_count_five_five);
            picture_count_one = itemView.findViewById(R.id.picture_count_one);
            picture_count_two = itemView.findViewById(R.id.picture_count_two);
            picture_count_three = itemView.findViewById(R.id.picture_count_three);
            picture_count_four = itemView.findViewById(R.id.picture_count_four);
            picture_count_five = itemView.findViewById(R.id.picture_count_five);
            tv_position_name = itemView.findViewById(R.id.tv_position_name);
            zuji_image = itemView.findViewById(R.id.zuji_image);
            plane = itemView.findViewById(R.id.plane);
        }
    }

}
