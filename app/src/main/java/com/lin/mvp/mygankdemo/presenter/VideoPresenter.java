package com.lin.mvp.mygankdemo.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.lin.mvp.mygankdemo.api.ApiManager;
import com.lin.mvp.mygankdemo.base.BasePresenterImpl;
import com.lin.mvp.mygankdemo.base.Config;
import com.lin.mvp.mygankdemo.bean.VideoData;
import com.lin.mvp.mygankdemo.contract.VideoContract;
import com.lin.mvp.mygankdemo.util.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mvp on 2016/9/8.
 */

public class VideoPresenter extends BasePresenterImpl implements VideoContract.IVideoPresenter{
    private VideoContract.IVideoFragment mVideoFragment;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();
    private Context context;

    public VideoPresenter(Context context, VideoContract.IVideoFragment mVideoFragment) {
        this.mVideoFragment = mVideoFragment;
        mCacheUtil = CacheUtil.get(context);
        this.context = context;
    }


    @Override
    public void getVideoData(int page) {
        mVideoFragment.showProgressDialog();
        Subscription subscribe = ApiManager.getInstence().getGankService().getVideoData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoData>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        mVideoFragment.hidProgressDialog();
                        mVideoFragment.showError(e.getMessage());
                    }
                    @Override
                    public void onNext(VideoData videoData) {
                        mVideoFragment.hidProgressDialog();
                        mCacheUtil.put(Config.VIDEO, gson.toJson(videoData));
                        mVideoFragment.updateVideoData(videoData);
                    }
                });
        addSubscription(subscribe);
    }

}
