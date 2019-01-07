package com.iyoyogo.android.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.adapter.MyAdapter;
import com.iyoyogo.android.ui.mine.fragment.GuiJiFragment;
import com.iyoyogo.android.ui.mine.fragment.GuoMeiFragment;
import com.iyoyogo.android.utils.StatusBarUtil;
import com.iyoyogo.android.utils.StatusBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityActivity extends AppCompatActivity {

    @BindView(R.id.finshi)
    ImageView finshi;
    @BindView(R.id.rb_yoji)
    RadioButton rbYoji;
    @BindView(R.id.rb_yoxiu)
    RadioButton rbYoxiu;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    /*  private ViewPager vp;
      private TabLayout tab;*/
    private MyAdapter adapter;
    private ArrayList<Fragment> list = new ArrayList<>();
    private String name;
    private GuoMeiFragment aFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengshi);
        //StatusBarUtils.setStatusBarLightMode(CityActivity.this);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        Log.e("onCreate111", "onCreate: " + name);
        finshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();
    }

    private void initView() {
      /*  vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);*/
        aFragment = new GuoMeiFragment(name);
        list.add(aFragment);
        GuiJiFragment bFragment = new GuiJiFragment(name);
        list.add(bFragment);
        switchFragment(aFragment);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_yoji:
                        switchFragment(aFragment);
                        break;
                    case R.id.rb_yoxiu:
                        switchFragment(bFragment);
                }
            }
        });

        aFragment.setSetData(new GuoMeiFragment.setData() {
            @Override
            public void getData(String name) {
                Log.e("getData", "getData: " + name);
                Intent intent = getIntent();
                intent.putExtra("city_name", name);
                setResult(6, intent);
                finish();
            }
        });

        bFragment.setSetData(new GuiJiFragment.setData() {
            @Override
            public void getData(String name) {
                Intent intent = getIntent();
                intent.putExtra("city_name",name);
                setResult(6,intent);
                finish();
            }
        });
 /*       title.add("国内");
        title.add("国际");
        adapter = new MyAdapter(getSupportFragmentManager(), list, title);
        tab.setupWithViewPager(vp);
        vp.setAdapter(adapter);*/
    }

    @OnClick(R.id.finshi)
    public void onViewClicked() {
        aFragment.setSetData(new GuoMeiFragment.setData() {
            @Override
            public void getData(String name) {
                Log.e("getData", "getData: " + name);
                Intent intent = getIntent();
                intent.putExtra("city_name", name);
                setResult(6, intent);
                finish();
            }
        });
    }
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commitAllowingStateLoss();
    }
}
