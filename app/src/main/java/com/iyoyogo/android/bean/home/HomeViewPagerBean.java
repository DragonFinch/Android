package com.iyoyogo.android.bean.home;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iyoyogo.android.bean.BaseBean;

import java.util.List;

public class HomeViewPagerBean extends BaseBean {


    /**
     * data : {"type":"commend","banner_list":[{"id":22,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/6/8cAKYhMFcG.jpg","target_url":"http://www.jd.com","remark":"12"},{"id":23,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/14/fTJmkyGYJp.png","target_url":"1","remark":"11"},{"id":20,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/t5zMJ6pxXd.jpg","target_url":"1","remark":"1"},{"id":19,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/CAAhEStwnN.jpg","target_url":"1","remark":"2"},{"id":18,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/X5aanfHGhw.png","target_url":"1","remark":"2"},{"id":16,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/29/58QHEFtFTn.jpg","target_url":"1111","remark":"1111"}],"yox_list":[],"yoj_list":[]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * type : commend
         * banner_list : [{"id":22,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/6/8cAKYhMFcG.jpg","target_url":"http://www.jd.com","remark":"12"},{"id":23,"path":"http://xzdtest.oss-cn-beijing.aliyuncs.com/xzdtest/2018/11/14/fTJmkyGYJp.png","target_url":"1","remark":"11"},{"id":20,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/t5zMJ6pxXd.jpg","target_url":"1","remark":"1"},{"id":19,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/CAAhEStwnN.jpg","target_url":"1","remark":"2"},{"id":18,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/31/X5aanfHGhw.png","target_url":"1","remark":"2"},{"id":16,"path":"http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/10/29/58QHEFtFTn.jpg","target_url":"1111","remark":"1111"}]
         * yox_list : []
         * yoj_list : []
         */

        private String type;
        private List<BannerListBean> banner_list;
        private List<?> yox_list;
        private List<?> yoj_list;

        public static final int VIEWPAGER = 0;
        public static final int YOUXIU = 1;
        public static final int YOUJI = 2;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public List<?> getYox_list() {
            return yox_list;
        }

        public void setYox_list(List<?> yox_list) {
            this.yox_list = yox_list;
        }

        public List<?> getYoj_list() {
            return yoj_list;
        }

        public void setYoj_list(List<?> yoj_list) {
            this.yoj_list = yoj_list;
        }

        public static class BannerListBean {
            /**
             * id : 22
             * path : http://fengchaominsu.oss-cn-beijing.aliyuncs.com/fengchaominsu/2018/11/6/8cAKYhMFcG.jpg
             * target_url : http://www.jd.com
             * remark : 12
             */

            private int id;
            private String path;
            private String target_url;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTarget_url() {
                return target_url;
            }

            public void setTarget_url(String target_url) {
                this.target_url = target_url;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
