package com.lin.mvp.mygankdemo.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.lin.mvp.mygankdemo.api.ApiManager;
import com.lin.mvp.mygankdemo.base.BasePresenterImpl;
import com.lin.mvp.mygankdemo.base.Config;
import com.lin.mvp.mygankdemo.bean.GankData;
import com.lin.mvp.mygankdemo.contract.GankContract;
import com.lin.mvp.mygankdemo.util.CacheUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mvp on 2016/9/8.
 */

public class GankPresenter extends BasePresenterImpl implements GankContract.IGankPresenter{

    private GankContract.IGankFragment mGankFragment;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();
    private Context context;

    public GankPresenter(Context context, GankContract.IGankFragment mGankFragment) {
        this.mGankFragment = mGankFragment;
        mCacheUtil = CacheUtil.get(context);
        this.context = context;
    }


    @Override
    public void getGankData(String day) {
        mGankFragment.showProgressDialog();
        Subscription subscribe = ApiManager.getInstence().getGankService().getGankData(day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankData>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        mGankFragment.hidProgressDialog();
                        mGankFragment.showError(e.getMessage());
                        System.out.println(e.getMessage());
                    }
                    @Override
                    public void onNext(GankData gankData) {
                        mGankFragment.hidProgressDialog();
                        mCacheUtil.put(Config.VIDEO, gson.toJson(gankData));
                        mGankFragment.updateGankData(gankData);
                        System.out.println("updateGankData");
                    }
                });
        addSubscription(subscribe);
    }
}
