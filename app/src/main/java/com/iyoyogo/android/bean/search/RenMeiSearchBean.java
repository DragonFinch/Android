package com.iyoyogo.android.bean.search;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class RenMeiSearchBean extends BaseBean {

    /**
     * data : {"list":["圣母玛利亚"]}
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
