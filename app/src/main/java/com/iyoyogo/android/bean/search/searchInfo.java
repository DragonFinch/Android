package com.iyoyogo.android.bean.search;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class searchInfo extends BaseBean {

    /**
     * data : {"list_hot":["香港","巴黎","大理","三亚"],"list_history":["","%E9%9B%AA%E8%B0%B7%E7%A9%BF%E8%B6%8A","地","门头沟沿河城","龙","房山大滩","龙脊梯田","北京","雪谷穿越","一树山","雪乡国家森林公园","七","古","穿衣潮搭","新西兰","古城"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> list_hot;
        private List<String> list_history;

        public List<String> getList_hot() {
            return list_hot;
        }

        public void setList_hot(List<String> list_hot) {
            this.list_hot = list_hot;
        }

        public List<String> getList_history() {
            return list_history;
        }

        public void setList_history(List<String> list_history) {
            this.list_history = list_history;
        }
    }
}
