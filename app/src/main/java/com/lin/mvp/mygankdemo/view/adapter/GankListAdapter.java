/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lin.mvp.mygankdemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lin.mvp.mygankdemo.R;
import com.lin.mvp.mygankdemo.bean.Gank;
import com.lin.mvp.mygankdemo.util.StringStyles;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by drakeet on 8/11/15.
 */
public class GankListAdapter extends AnimRecyclerViewAdapter<GankListAdapter.GankHolder> {

    private List<Gank> mGankList = new ArrayList<Gank>();

    private Context context;

    public GankListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public GankListAdapter.GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                               .inflate(R.layout.item_gank, parent, false);
        return new GankHolder(v);
    }


    @Override
    public void onBindViewHolder(GankListAdapter.GankHolder holder, int position) {
        Gank gank = mGankList.get(position);
        if (position == 0) {
            showCategory(holder);
        }
        else {
            boolean theCategoryOfLastEqualsToThis = mGankList.get(
                    position - 1).getType().equals(mGankList.get(position).getType());
            if (!theCategoryOfLastEqualsToThis) {
                showCategory(holder);
            }
            else {
                hideCategory(holder);
            }
        }
        holder.category.setText(gank.getType());
        SpannableStringBuilder builder = new SpannableStringBuilder(gank.getDesc()).append(
                StringStyles.format(holder.gank.getContext(), " (via. " +
                        gank.getWho() +
                        ")", R.style.ViaTextAppearance));
        CharSequence gankText = builder.subSequence(0, builder.length());

        holder.gank.setText(gankText);
        showItemAnim(holder.gank, position);
    }


    @Override
    public int getItemCount() {
        return mGankList.size();
    }


    private void showCategory(GankListAdapter.GankHolder holder) {
        if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
    }


    private void hideCategory(GankListAdapter.GankHolder holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }


    /**
     * view.isShown() is a kidding...
     */
    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }


    class GankHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_category)
        TextView category;
        @InjectView(R.id.tv_title)
        TextView gank;


        public GankHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }


//        @OnClick(R.id.ll_gank_parent) void onGank(View v) {
//            Gank gank = mGankList.get(getLayoutPosition());
//            Intent intent = WebActivity.newIntent(v.getContext(), gank.getUrl(), gank.getDesc());
//            v.getContext().startActivity(intent);
//        }
    }


    public void updateData(List<Gank> list)
    {
        this.mGankList = list;
        this.notifyDataSetChanged();
    }

}
