package com.lin.mvp.mygankdemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lin.mvp.mygankdemo.R;
import com.lin.mvp.mygankdemo.bean.VideoDataResults;
import com.lin.mvp.mygankdemo.util.DateUtil;
import com.lin.mvp.mygankdemo.view.activity.GankActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by mvp on 2016/9/8.
 */

public class VideoAdapter extends AnimRecyclerViewAdapter<VideoAdapter.VideoHolder> {
    private List<VideoDataResults> mDatas = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    public VideoAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_video_list,null);
        VideoHolder videoHolder = new VideoHolder(view);
        return videoHolder;
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        VideoDataResults videoData = mDatas.get(position);

        if (videoData == null)
        {
            return;
        }
        setDailyDate(holder, videoData,position);

    }

    private void setDailyDate(VideoHolder holder, VideoDataResults data,int position) {
        String published = data.getPublishedAt();
        final String timeStr = DateUtil.formatDate(published) ;

        holder.mTitle.setText(data.getDesc());
        showItemAnim(holder.mTitle, position);

        holder.mLayout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //跳转到详情界面
                GankActivity.lanuch(mContext, timeStr);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  mDatas.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.card_view)
        CardView mLayout;

        @InjectView(R.id.item_title)
        TextView mTitle;

        public VideoHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

    public void updateData( List<VideoDataResults> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    public void addData(List<VideoDataResults> videos)
    {

        if (this.mDatas == null)
        {
            updateData(videos);
        } else
        {
            this.mDatas.addAll(videos);
            notifyDataSetChanged();
        }
    }

}
