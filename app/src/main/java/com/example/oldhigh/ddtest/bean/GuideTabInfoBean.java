package com.example.oldhigh.ddtest.bean;

import java.util.List;

/**
 * Created by oldhigh on 2017/11/20.
 */

public class GuideTabInfoBean {


    /**
     * total_numb : 1
     * page : 1
     * pagesize : 1000
     * numb : 1
     * objs : [{"name":"生活","tags":"interactive","flcate":"6,","owner":"8849bdd8-d76b-4904-96e2-53ae1431386c","id":"07ca635e-7b28-4771-ae4f-9bca33cb5ea1","create_time":"2017-11-24T15:14:35","update_time":"2017-11-24T15:14:35"}]
     */

    private List<ObjsBean> objs;

    public List<ObjsBean> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjsBean> objs) {
        this.objs = objs;
    }

    public static class ObjsBean {
        /**
         * name : 生活
         * tags : interactive
         * flcate : 6,
         * owner : 8849bdd8-d76b-4904-96e2-53ae1431386c
         * id : 07ca635e-7b28-4771-ae4f-9bca33cb5ea1
         * create_time : 2017-11-24T15:14:35
         * update_time : 2017-11-24T15:14:35
         */

        private String name;
        private String tags;
        private String flcate;
        private String create_time;
        private String update_time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getFlcate() {
            return flcate;
        }

        public void setFlcate(String flcate) {
            this.flcate = flcate;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
