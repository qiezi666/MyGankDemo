package com.lin.mvp.mygankdemo.api;

import com.lin.mvp.mygankdemo.bean.GankData;
import com.lin.mvp.mygankdemo.bean.VideoData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mvp on 2016/9/8.
 */

public interface GankApi {

    //休息视频数据
    @GET("api/data/休息视频/10/{page}")
    Observable<VideoData> getVideoData(@Path("page") int page);

    //Gank Day数据http://gank.io/api/day/2016/09/08
    @GET("api/day/{day}") Observable<GankData> getGankData(
            @Path("day") String day);

}
