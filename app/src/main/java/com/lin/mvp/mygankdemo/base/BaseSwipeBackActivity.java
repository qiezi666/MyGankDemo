package com.lin.mvp.mygankdemo.base;

import android.os.Bundle;

import com.lin.mvp.mygankdemo.view.widget.swipebacklayout.SwipeBackActivity;
import com.lin.mvp.mygankdemo.view.widget.swipebacklayout.SwipeBackLayout;

import butterknife.ButterKnife;


/**
 * Created by 11 on 2016/4/1.
 * <p/>
 * 侧滑返回Activity基类
 */
public abstract class BaseSwipeBackActivity extends SwipeBackActivity
{

    public SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        ButterKnife.inject(this);
        //初始化侧滑返回layout
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        initDatas();
        //初始化控件
        initViews(savedInstanceState);


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);
    public abstract void initDatas();
}
