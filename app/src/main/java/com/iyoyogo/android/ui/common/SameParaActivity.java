package com.iyoyogo.android.ui.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.iyoyogo.android.Bean;
import com.iyoyogo.android.ImageBean;
import com.iyoyogo.android.ItemBean;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.base.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SameParaActivity extends BaseActivity {


    @BindView(R.id.back_same_para)
    ImageView backSamePara;
    @BindView(R.id.recycler_same_para)
    RecyclerView recyclerSamePara;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_same_para;
    }

    @Override
    protected void initView() {
        ArrayList<Bean> mList = new ArrayList<>();
        super.initView();
        Bean bean = new Bean();
        bean.setImgs("http://t2.hddhhn.com/uploads/tu/201608/280/st1.png");
        ImageBean imageBean = new ImageBean("http://t2.hddhhn.com/uploads/tu/201702/532/st1.png");
        ImageBean imageBean1 = new ImageBean("http://t2.hddhhn.com/uploads/tu/201702/132/st110.png");
        ItemBean itemBean = new ItemBean();
        List<ItemBean> itemBeanList=new ArrayList<>();
        imageBean.setImgList(itemBeanList);
        mList.add(bean);
        List<ImageBean> image_list=new ArrayList<>();
        image_list.add(imageBean);
        image_list.add(imageBean1);
        bean.setImageList(image_list);


        ItemBean itemBean1=new ItemBean();
        ItemBean itemBean2=new ItemBean();
        ItemBean itemBean3=new ItemBean();
        ItemBean itemBean4=new ItemBean();
        ItemBean itemBean5=new ItemBean();
        ItemBean itemBean6=new ItemBean();
        itemBean.setUri("http://t2.hddhhn.com/uploads/tu/201702/132/st5.png");
        itemBean1.setUri("http://t2.hddhhn.com/uploads/tu/201701/323/st1.png");
        itemBean2.setUri("http://t2.hddhhn.com/uploads/tu/201701/91/st3.png");
        itemBean3.setUri("http://t2.hddhhn.com/uploads/tu/201612/429/st1.png");
        itemBean4.setUri("http://t2.hddhhn.com/uploads/tu/201610/141/st6.jpg");
        itemBean5.setUri("http://t2.hddhhn.com/uploads/tu/201708/760/18a21a92591.jpg");
        itemBean6.setUri("http://t2.hddhhn.com/uploads/tu/201707/84/c3.jpg");
        itemBeanList.add(itemBean);
        itemBeanList.add(itemBean1);
        itemBeanList.add(itemBean2);
        itemBeanList.add(itemBean3);
        itemBeanList.add(itemBean4);
        itemBeanList.add(itemBean5);
        itemBeanList.add(itemBean6);
        imageBean.setImgList(itemBeanList);
        Log.d("SameParaActivity", "itemBeanList.size():" + itemBeanList.size());

        Log.d("SameParaActivity", mList.get(0).getImgs());
        Log.d("SameParaActivity", "mList.get(0).getImageList().size():" + mList.get(0).getImageList().size());
        String url = mList.get(0).getImageList().get(0).getUrl();
        String urls = mList.get(0).getImageList().get(1).getUrl();
        Log.d("SameParaActivity", mList.get(0).getImageList().get(0).getImgList().get(0).getUri());
        Log.d("SameParaActivity", url);
        Log.d("SameParaActivity", urls);
        SameParaAdapter sameParaAdapter = new SameParaAdapter(SameParaActivity.this,mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerSamePara.setLayoutManager(linearLayoutManager);
        recyclerSamePara.setAdapter(sameParaAdapter);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }



    @OnClick(R.id.back_same_para)
    public void onViewClicked() {
        finish();
    }
}
