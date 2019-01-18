package com.iyoyogo.android.ui.home.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.iyoyogo.android.R;
import com.iyoyogo.android.base.BaseActivity;
import com.iyoyogo.android.bean.search.SearchBean;
import com.iyoyogo.android.bean.search.searchInfo;
import com.iyoyogo.android.contract.SearchContract;
import com.iyoyogo.android.presenter.SearchPresenter;
import com.iyoyogo.android.utils.SpUtils;
import com.iyoyogo.android.utils.StatusBarUtils;
import com.iyoyogo.android.utils.search.SPUtils;
import com.iyoyogo.android.view.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author dengyalan
 */
public class SearchActivity extends BaseActivity<SearchContract.Presenter> implements SearchContract.View {

    public static String[] searchWord = {
            "阿德莱德山",
            "摇篮山",
            "从撒哈拉到青海湖",
            "唐人街",
            "阿德莱德大学糖",
            "冬天滑雪",
            "冷",
            "《延禧攻略》拍摄地",
            "粉色湖的不期而遇",
            "巴厘岛"
    };
    @BindView(R.id.auto_search)
    AutoCompleteTextView autoSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.clear_iv)
    TextView clearIv;
    @BindView(R.id.history_fl)
    ZFlowLayout historyFl;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.keyword_fl)
    ZFlowLayout keywordFl;
    @BindView(R.id.rl_lishi)
    RelativeLayout rlLishi;

    private List<searchInfo> mList = new ArrayList<>();
    private List<searchInfo> list1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchContract.Presenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);

        //软键盘的搜索点击时间
        autoSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软件盘
                    hideKeyboard(autoSearch);
                    if (autoSearch.getText().toString() != null){
                        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                        intent.putExtra("key", autoSearch.getText().toString());
                        startActivityForResult(intent, 0);
                        String keyWord = autoSearch.getText().toString();
                        SPUtils.getInstance(SearchActivity.this).save(autoSearch.getText().toString());
                    }else{
                        showToastShort(SearchActivity.this, "搜索内容为空！");
                    }
                }
                return false;
            }
        });



    }
    /**
     * 隐藏软键盘
     *
     * @param : Context上下文环境，一般为Activity实例
     * @param view                 :一般为EditText
     */
    public static void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //调用历史搜索  从重新获取焦点
        rlLishi.setVisibility(View.VISIBLE);
        historyFl.setVisibility(View.VISIBLE);
        initHistory();

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String user_id = SpUtils.getString(SearchActivity.this, "user_id", null);
        String user_token = SpUtils.getString(SearchActivity.this, "user_token", null);
        mPresenter.getSearch(user_id, user_token);
    }

    private void initHistory() {
        final String[] data = SPUtils.getInstance(this).getHistoryList();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        historyFl.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (isNullorEmpty(data[i])) {
                return;
            }
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            historyFl.addView(paramItemView, layoutParams);
            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    autoSearch.setText(data[j]);
                    autoSearch.setSelection(autoSearch.getText().length());
                }
            });
        }
    }

    private void initKeyword(final List<String> keyword) {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        keywordFl.removeAllViews();
        for (int i = 0; i < keyword.size(); i++) {
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(keyword.get(i));
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            keywordFl.addView(paramItemView, layoutParams);
            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    autoSearch.setText(keyword.get(j));
                    autoSearch.setSelection(autoSearch.getText().length());
                }
            });
        }
    }

    @OnClick({R.id.iv_back, R.id.clear_iv, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String searchKey = autoSearch.getText().toString();
                autoSearch.setText("");
                finish();
               /* if (!isNullorEmpty(searchKey)) {
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("key", searchKey);
                    startActivityForResult(intent, 0);
                    String keyWord = autoSearch.getText().toString();

                    if (!isNullorEmpty(keyWord)) {
                        SPUtils.getInstance(SearchActivity.this).save(autoSearch.getText().toString());
                    }
                } else {
                    showToastShort(this, "搜索内容为空！");
                }*/
                break;

            case R.id.clear_iv:
                SPUtils.getInstance(SearchActivity.this).cleanHistory();
                showToastShort(this, "已清除历史记录！");
                rlLishi.setVisibility(View.GONE);
                historyFl.setVisibility(View.GONE);
                initHistory();


                break;
            default:
                break;
        }
    }



    private boolean isNullorEmpty(String str) {
        return str == null || "".equals(str);
    }

    private void showToastShort(Context context, String data) {
        Toast toast = Toast.makeText(context, data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                initHistory();
            }
        }
    }

    @Override
    public void getRecommendTopicSuccess(searchInfo list) {
        List<String> list_hot = list.getData().getList_hot();
        initKeyword(list_hot);
        initHistory();
        String[] data = SPUtils.getInstance(this).getHistoryList();

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(this,
                R.layout.view_mw_textview, data);
        autoSearch.setAdapter(autoCompleteAdapter);

        autoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null){
                    ivBack.setVisibility(View.GONE);
                   /* if (tvSearch.getText().toString().equals("搜索")){
                        Toast.makeText(SearchActivity.this,"eqwe",Toast.LENGTH_SHORT).show();
                    }*/
                }else{

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
