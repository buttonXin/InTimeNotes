package com.example.oldhigh.ddtest.bean;

import java.util.List;

/**
 * Created by oldhigh on 2017/11/21.
 */

public class GuideItemInfoBean {


    /**
     * total_numb : 10
     * page : 1
     * pagesize : 1000
     * numb : 10
     * objs : */

    private List<ObjsBean> objs;

    public List<ObjsBean> getObjs() {
        return objs;
    }

    public void setObjs(List<ObjsBean> objs) {
        this.objs = objs;
    }

    public static class ObjsBean {
        /**
         * mProgramDetailData : {"actor":[],"desc":"    《原来如此》栏目是一档以实验体验为特征的科普栏目。在人们习以为常，熟视无睹的日常生活中发现问题，通过质疑假设、实验求证的调查手段，给予科学探究。\n","director":[],"guest":[],"host":["刘楠"],"name":"原来如此","pic":"http://img2.tvmao.cn/thumb/tvcolumn/0/409/540x303.jpg","vpic":"http://imghw.deepepg.com/thumb/cIIrlwR1CPHRWFx8D-oJmgzGhNzu8=.jpg"}
         * edate : 2017-11-24
         * etime : 15:38
         * flcate : 6,
         * name : 原来如此-2017-40
         * resId : OFIf
         * time : 15:07
         * typeId : 12
         * mChannelInfo : {"channelId":33,"llogo":"http://img.tvmao.com/images/logo/channel/CCTV10/CCTV10_140x140.png","logo":"http://static.tvmao.cn/channel/logo/CCTV10.jpg","name":"CCTV-10科教","pulse":"8963,4487,578,530,579,531,579,1660,579,1659,579,530,579,531,579,1660,579,1660,579,1659,579,531,579,1660,579,1660,579,1659,579,530,579,530,578,532,578,1659,579,530,579,530,579,531,579,1659,579,530,579,530,579,530,579,531,579,1660,579,1660,579,1659,579,531,579,1660,579,1660,579,1659,579,40376,8963,2230,579,96057^8957,4495,571,538,571,539,571,1668,571,1667,571,538,571,539,571,1669,571,1668,571,1667,571,539,571,1669,571,1669,571,1668,571,538,571,538,570,538,570,538,570,538,570,539,570,540,570,1668,570,538,571,538,571,539,571,1669,571,1668,571,1668,572,1666,572,538,572,1667,573,1667,573,1665,574,40386,8967,2227,583,96041","type":0}
         */

        private MProgramDetailDataBean mProgramDetailData;
        private String edate;
        private String etime;
        private String flcate;
        private String name;
        private String time;
        private String resId;
        private MChannelInfoBean mChannelInfo;

        public MProgramDetailDataBean getMProgramDetailData() {
            return mProgramDetailData;
        }

        public void setMProgramDetailData(MProgramDetailDataBean mProgramDetailData) {
            this.mProgramDetailData = mProgramDetailData;
        }

        public String getResId() {
            return resId;
        }

        public void setResId(String resId) {
            this.resId = resId;
        }

        public String getEdate() {
            return edate;
        }

        public void setEdate(String edate) {
            this.edate = edate;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getFlcate() {
            return flcate;
        }

        public void setFlcate(String flcate) {
            this.flcate = flcate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public MChannelInfoBean getMChannelInfo() {
            return mChannelInfo;
        }

        public void setMChannelInfo(MChannelInfoBean mChannelInfo) {
            this.mChannelInfo = mChannelInfo;
        }

        public static class MProgramDetailDataBean {
            /**
             * actor : []
             * desc :     《原来如此》栏目是一档以实验体验为特征的科普栏目。在人们习以为常，熟视无睹的日常生活中发现问题，通过质疑假设、实验求证的调查手段，给予科学探究。

             * director : []
             * guest : []
             * host : ["刘楠"]
             * name : 原来如此
             * pic : http://img2.tvmao.cn/thumb/tvcolumn/0/409/540x303.jpg
             * vpic : http://imghw.deepepg.com/thumb/cIIrlwR1CPHRWFx8D-oJmgzGhNzu8=.jpg
             */

            private String desc;
            private String name;
            private String pic;
            private List<?> actor;
            private List<?> director;
            private List<?> guest;
            private List<String> host;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public List<?> getActor() {
                return actor;
            }

            public void setActor(List<?> actor) {
                this.actor = actor;
            }

            public List<?> getDirector() {
                return director;
            }

            public void setDirector(List<?> director) {
                this.director = director;
            }

            public List<?> getGuest() {
                return guest;
            }

            public void setGuest(List<?> guest) {
                this.guest = guest;
            }

            public List<String> getHost() {
                return host;
            }

            public void setHost(List<String> host) {
                this.host = host;
            }
        }

        public static class MChannelInfoBean {
            /**
             * channelId : 33
             * llogo : http://img.tvmao.com/images/logo/channel/CCTV10/CCTV10_140x140.png
             * logo : http://static.tvmao.cn/channel/logo/CCTV10.jpg
             * name : CCTV-10科教
             * pulse : 8963,4487,578,530,579,531,579,1660,579,1659,579,530,579,531,579,1660,579,1660,579,1659,579,531,579,1660,579,1660,579,1659,579,530,579,530,578,532,578,1659,579,530,579,530,579,531,579,1659,579,530,579,530,579,530,579,531,579,1660,579,1660,579,1659,579,531,579,1660,579,1660,579,1659,579,40376,8963,2230,579,96057^8957,4495,571,538,571,539,571,1668,571,1667,571,538,571,539,571,1669,571,1668,571,1667,571,539,571,1669,571,1669,571,1668,571,538,571,538,570,538,570,538,570,538,570,539,570,540,570,1668,570,538,571,538,571,539,571,1669,571,1668,571,1668,572,1666,572,538,572,1667,573,1667,573,1665,574,40386,8967,2227,583,96041
             * type : 0
             */

            private String name;
            private String pulse;
            private String channelId ;

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPulse() {
                return pulse;
            }

            public void setPulse(String pulse) {
                this.pulse = pulse;
            }
        }
    }
}
