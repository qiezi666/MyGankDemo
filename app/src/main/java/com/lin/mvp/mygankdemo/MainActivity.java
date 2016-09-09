package com.lin.mvp.mygankdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lin.mvp.mygankdemo.bean.VideoData;
import com.lin.mvp.mygankdemo.bean.VideoDataResults;
import com.lin.mvp.mygankdemo.contract.VideoContract;
import com.lin.mvp.mygankdemo.presenter.VideoPresenter;
import com.lin.mvp.mygankdemo.view.adapter.VideoAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.lin.mvp.mygankdemo.MyApplication.getContext;

public class MainActivity extends AppCompatActivity implements VideoContract.IVideoFragment{

    @InjectView(R.id.video_recycle)
    RecyclerView mVideoRecycle;
    @InjectView(R.id.prograss)
    AVLoadingIndicatorView mPrograss;
    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    VideoPresenter mPresenter;

    private LinearLayoutManager mLinearLayoutManager;
    private boolean isrefreshing;
    private boolean isLoadingMore;
    private boolean hasMoreData = true;
    private int index = 1;
    private VideoAdapter mAdapter;
    private List<VideoDataResults> mVideoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initDatas();
        initViews();
    }

    private void initDatas() {
        mPresenter = new VideoPresenter(getApplicationContext(),this);
    }

    private void initViews() {
        // 1、初始化RecycleView 的 Adapter
        mAdapter = new VideoAdapter(MainActivity.this);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mVideoRecycle.setLayoutManager(mLinearLayoutManager);
        mVideoRecycle.setHasFixedSize(true);
        mVideoRecycle.setItemAnimator(new DefaultItemAnimator());
        mVideoRecycle.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isrefreshing) {
                    Log.d(TAG, "ignore manually update!");
                } else {
                    System.out.println("上啦刷新数据");
                    onRefreshData();
                    isrefreshing = true;
                }
            }
        });

        mVideoRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!hasMoreData) {
                    Toast.makeText(getContext(), "没有更多数据了！", Toast.LENGTH_SHORT).show();
                    return;
                }

                int lastVisibleItem = ((LinearLayoutManager) mLinearLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLinearLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (isLoadingMore) {
                        Log.d(TAG, "ignore manually update!");
                    } else {
                        System.out.println("加载更多数据");
                        index += 1;
                        onLoadMoreData();//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = true;
                    }
                }
            }
        });

        loadDate();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoRecycle.scrollToPosition(0);
            }
        });
    }

    private void loadDate() {
        mPresenter.getVideoData(1);
    }

    private void onLoadMoreData() {
        mPresenter.getVideoData(index);
    }

    private void onRefreshData() {
        mPresenter.getVideoData(1);
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
        Toast.makeText(MainActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateVideoData(VideoData videoData) {
        if (isrefreshing) {
            mSwipeRefreshLayout.setRefreshing(false);
            isrefreshing = false;

            if(videoData!=null){
                mVideoList = videoData.getResults();
                if(mVideoList!=null){
                    mAdapter.updateData(mVideoList);
                }
            }
        }else if(isLoadingMore){
            isLoadingMore = false;
            if(videoData!=null){
                mAdapter.addData(videoData.getResults());
            }else {
                hasMoreData = false;
            }
        }else {
            if(videoData!=null){
                mVideoList = videoData.getResults();
                if(mVideoList!=null){
                    mAdapter.updateData(mVideoList);
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        mPresenter.unsubcrible();
    }
}
