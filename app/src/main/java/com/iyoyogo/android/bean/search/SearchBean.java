package com.iyoyogo.android.bean.search;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class SearchBean extends BaseBean {


    /**
     * data : {"list":["上海"]}
     */

    private DataBean data;
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> list;

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
