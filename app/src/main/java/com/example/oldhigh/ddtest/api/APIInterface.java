package com.example.oldhigh.ddtest.api;

import com.dopool.proutil.BaseApi.BaseInterface;
import com.example.oldhigh.ddtest.Constant;
import com.example.oldhigh.ddtest.bean.GuideItemInfoBean;
import com.example.oldhigh.ddtest.bean.GuideTabInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public interface APIInterface  {

    //获取验证码接口
    @FormUrlEncoded
    @POST
    Call<Object> getIdentCode(@Url String url, @Field("mobile") String mobile);

    //验证验证码接口
    @FormUrlEncoded
    @POST
    Call<Object> sendIdentCode(@Url String url, @Field("mobile") String mobile,
                               @Field("captchaCode") String capCode);

    //导航 接口
    // http://dev-apimid.dopool.com/ca2b6cc7-23da-437f-8574-3168f0bee775?tags=interactive  / live
    @GET("ca2b6cc7-23da-437f-8574-3168f0bee775?tags=interactive")
    Observable<GuideTabInfoBean> getGuideTabInfoBean();


    //分类下的信息集合
    //http://dev-apimid.dopool.com/59e24493-0386-4b12-b1f3-8de31ff16064?
    // category_id=e5187bea-89a6-4f3f-96d3-3e77c83a1d6d
    @GET("59e24493-0386-4b12-b1f3-8de31ff16064?tags=interactive")
    Observable<GuideItemInfoBean>  getGuideItemInfoBean(@Query("flcate") String flcate);

}
