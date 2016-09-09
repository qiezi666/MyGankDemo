package com.lin.mvp.mygankdemo.contract;

import com.lin.mvp.mygankdemo.base.BasePresenter;
import com.lin.mvp.mygankdemo.base.IBaseFragment;
import com.lin.mvp.mygankdemo.bean.GankData;

/**
 * Created by mvp on 2016/9/8.
 */

public interface GankContract {
    public interface IGankPresenter extends BasePresenter {
        /**
         * 获取最新的日报数据
         *
         * @return
         */
        void getGankData(String day);
    }

    public interface IGankFragment extends IBaseFragment {

        void updateGankData(GankData gankData);
    }
}
