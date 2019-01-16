package com.iyoyogo.android.bean.map;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class MapRenMei extends BaseBean {

    /**
     * data : {"list":["广州","张家界","甘肃","黑龙江","太原","澳门","香港","石家庄","郑州","双邱","上海","北京"]}
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
