package com.iyoyogo.android.bean.yoji.label;

import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class LabelListBean extends BaseBean {

    /**
     * data : {"list1":[{"label_id":9,"user_id":2,"type":1,"label":"旅游胜地1","logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/2ikQfYjpsW.jpg"},{"label_id":8,"user_id":2,"type":1,"label":"旅游胜地","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/38rAxrYSyR.png"}],"list2":[{"label_id":13,"user_id":0,"type":2,"label":"我最帅","logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/12/zEfp73CPR5.jpg"},{"label_id":12,"user_id":2,"type":2,"label":"扫码","logo":"http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/12/yNRPR84kkM.jpeg"}],"list3":[{"label_id":7,"user_id":2,"type":3,"label":"3","logo":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/aSPpa4FC47.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<List1Bean> list1;
        private List<List2Bean> list2;
        private List<List3Bean> list3;

        public List<List1Bean> getList1() {
            return list1;
        }

        public void setList1(List<List1Bean> list1) {
            this.list1 = list1;
        }

        public List<List2Bean> getList2() {
            return list2;
        }

        public void setList2(List<List2Bean> list2) {
            this.list2 = list2;
        }

        public List<List3Bean> getList3() {
            return list3;
        }

        public void setList3(List<List3Bean> list3) {
            this.list3 = list3;
        }

        public static class List1Bean {
            /**
             * label_id : 9
             * user_id : 2
             * type : 1
             * label : 旅游胜地1
             * logo : http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/8/2ikQfYjpsW.jpg
             */

            private int label_id;
            private int user_id;
            private int type;
            private String label;
            private String logo;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getLabel_id() {
                return label_id;
            }

            public void setLabel_id(int label_id) {
                this.label_id = label_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

        public static class List2Bean {
            /**
             * label_id : 13
             * user_id : 0
             * type : 2
             * label : 我最帅
             * logo : http://iyoyogo.oss-cn-beijing.aliyuncs.com/iyoyogo/2018/12/12/zEfp73CPR5.jpg
             */

            private int label_id;
            private int user_id;
            private int type;
            private String label;
            private String logo;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getLabel_id() {
                return label_id;
            }

            public void setLabel_id(int label_id) {
                this.label_id = label_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }

        public static class List3Bean {
            /**
             * label_id : 7
             * user_id : 2
             * type : 3
             * label : 3
             * logo : http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/22/aSPpa4FC47.jpg
             */

            private int label_id;
            private int user_id;
            private int type;
            private String label;
            private String logo;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getLabel_id() {
                return label_id;
            }

            public void setLabel_id(int label_id) {
                this.label_id = label_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }
        }
    }
}
