package com.lin.mvp.mygankdemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.lin.mvp.mygankdemo.R;
import com.lin.mvp.mygankdemo.base.BaseSwipeBackActivity;
import com.lin.mvp.mygankdemo.bean.Gank;
import com.lin.mvp.mygankdemo.bean.GankData;
import com.lin.mvp.mygankdemo.bean.GankDataResults;
import com.lin.mvp.mygankdemo.contract.GankContract;
import com.lin.mvp.mygankdemo.presenter.GankPresenter;
import com.lin.mvp.mygankdemo.util.PreferenceUtils;
import com.lin.mvp.mygankdemo.view.adapter.GankListAdapter;
import com.lin.mvp.mygankdemo.view.widget.LoveVideoView;
import com.lin.mvp.mygankdemo.view.widget.VideoImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class GankActivity extends BaseSwipeBackActivity implements GankContract.IGankFragment {


    private static final String EXTRA_ID = "extra_id";
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.rv_gank)
    RecyclerView mRecyclerView;
    @InjectView(R.id.iv_video)
    VideoImageView mIvVideo;
    @InjectView(R.id.coll_toolbar_layout)
    CollapsingToolbarLayout mCollToolbarLayout;
    @InjectView(R.id.prograss)
    AVLoadingIndicatorView mPrograss;
    @InjectView(R.id.stub_empty_view)
    ViewStub mEmptyViewStub;
    @InjectView(R.id.stub_video_view)
    ViewStub mVideoViewStub;

    LoveVideoView mVideoView;
    private ActionBar mActionBar;

    private String time;

    GankPresenter mPresenter;
    private boolean isShowSnack = false;

    GankListAdapter mAdapter;
    ArrayList<Gank> mGankList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            time = intent.getStringExtra(EXTRA_ID);
        }

//         初始化ToolBar
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollToolbarLayout.setTitleEnabled(true);
        mActionBar.setTitle(time);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

        mAdapter = new GankListAdapter(GankActivity.this);

        //第一次进入提示用户可以左滑返回
        showSnackBarHint();

        initRecyclerView();

        setVideoViewPosition(getResources().getConfiguration());

        loadData();
    }

    boolean mIsVideoViewInflated = false;
    private void setVideoViewPosition(Configuration newConfig) {
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE: {
                if (mIsVideoViewInflated) {
                    mVideoViewStub.setVisibility(View.VISIBLE);
                } else {
                    mVideoView = (LoveVideoView) mVideoViewStub.inflate();
                    mIsVideoViewInflated = true;
                }
                if (mGankList.size() > 0 && mGankList.get(0).getType().equals("休息视频")) {
                    mVideoView.loadUrl(mGankList.get(0).getUrl());
                }
                break;
            }
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_UNDEFINED:
            default: {
                mVideoViewStub.setVisibility(View.GONE);
                break;
            }
        }
    }

    void closePlayer() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toast.makeText(this,"今天编辑们休息哦，没有干货提供！",Toast.LENGTH_SHORT).show();
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        setVideoViewPosition(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override public void onResume() {
        super.onResume();
        resumeVideoView();
    }

    @Override public void onPause() {
        super.onPause();
        pauseVideoView();
        clearVideoView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showSnackBarHint() {

        boolean isShow = PreferenceUtils.getBoolean("isShowSnack", false);
        if (!isShow) {
            Snackbar.make(mToolbar, "左滑可以返回主页哦~", Snackbar.LENGTH_LONG).show();
            this.isShowSnack = true;
            PreferenceUtils.putBoolean("isShowSnack", this.isShowSnack);
        }
    }

    private void loadData() {
        System.out.println("time:" + time);
        mPresenter.getGankData(time);
    }

    @Override
    public void initDatas() {
        mPresenter = new GankPresenter(getApplicationContext(), GankActivity.this);
    }


    public static void lanuch(Context context, String time) {

        Intent mIntent = new Intent(context, GankActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(EXTRA_ID, time);
        context.startActivity(mIntent);
    }

    @Override
    public void updateGankData(GankData gankData) {
        GankDataResults results = gankData.getResults();
        if (results.getAndroid() != null) {
            mGankList.addAll(results.getAndroid());
        }
        if (results.getIOS() != null) mGankList.addAll(results.getIOS());
        if (results.getApp() != null) mGankList.addAll(results.getApp());
        if (results.get休息视频() != null) mGankList.addAll(0, results.get休息视频());
        if (results.get前端() != null) mGankList.addAll(results.get前端());

        mAdapter.updateData(mGankList);

    }

    @OnClick(R.id.header_appbar) void onPlayVideo() {
        resumeVideoView();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (mGankList.size() > 0 && mGankList.get(0).getType().equals("休息视频")) {
            Toast.makeText(this,"加载中。。",Toast.LENGTH_SHORT).show();
        } else {
            closePlayer();
        }
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        clearVideoView();
    }

    @Override
    public void showProgressDialog() {
        if (mPrograss != null) {
            mPrograss.show();
        }
    }

    @Override
    public void hidProgressDialog() {
        if (mPrograss != null) {
            mPrograss.hide();
        }
    }

    @Override
    public void showError(String error) {
        mEmptyViewStub.inflate();
        Snackbar.make(mToolbar, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        mPresenter.unsubcrible();
        resumeVideoView();
    }

    private void pauseVideoView() {
        if (mVideoView != null) {
            mVideoView.onPause();
            mVideoView.pauseTimers();
        }
    }

    private void resumeVideoView() {
        if (mVideoView != null) {
            mVideoView.resumeTimers();
            mVideoView.onResume();
        }
    }
    private void clearVideoView() {
        if (mVideoView != null) {
            mVideoView.clearHistory();
            mVideoView.clearCache(true);
            mVideoView.loadUrl("about:blank");
            mVideoView.pauseTimers();
        }
    }
}
