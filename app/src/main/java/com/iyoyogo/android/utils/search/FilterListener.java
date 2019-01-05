package com.iyoyogo.android.utils.search;

import com.iyoyogo.android.bean.search.KeywordUserBean;

import java.util.List;

public interface FilterListener {
    void getFilterData(List<KeywordUserBean.DataBean.ListBean> list);
}
