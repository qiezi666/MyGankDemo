package com.lin.mvp.mygankdemo.contract;

import com.lin.mvp.mygankdemo.base.BasePresenter;
import com.lin.mvp.mygankdemo.base.IBaseFragment;
import com.lin.mvp.mygankdemo.bean.VideoData;

/**
 * Created by mvp on 2016/9/8.
 */

public interface VideoContract {
    public interface IVideoPresenter extends BasePresenter {
        /**
         * 获取最新的日报数据
         *
         * @return
         */
        void getVideoData(int page);
    }

    public interface IVideoFragment extends IBaseFragment {

        void updateVideoData(VideoData videoData);
    }
}
