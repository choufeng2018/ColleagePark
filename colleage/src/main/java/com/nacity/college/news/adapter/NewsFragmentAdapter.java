package com.nacity.college.news.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.R;
import com.nacity.college.databinding.NewsFragmentListBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

/**
 *  Created by usb on 2017/9/4.
 */

public class NewsFragmentAdapter extends BaseAdapter<NewsTo,NewsFragmentListBinding> {

    public NewsFragmentAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    public BindingHolder<NewsFragmentListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsFragmentListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_fragment_list, parent, false);
        BindingHolder<NewsFragmentListBinding> holder= new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        holder.setIsRecyclable(false);
        return holder;
    }
    @Override
    public void onBindViewHolder(BindingHolder<NewsFragmentListBinding> holder, int position) {
        NewsTo mode=mList.get(position);
        NewsFragmentListBinding binding = holder.getBinding();

        if(!TextUtils.isEmpty(mode.getPicUrl())){
            binding.layout.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(mode.getPicUrl()).into(binding.imagePic);}
        else{
            binding.layout.setVisibility(View.GONE);
              }

        if(!TextUtils.isEmpty(mode.getSummary())){
            Log.i("888", "onBindViewHolder:1 "+mode.getSummary());

        binding.noticeContent.setText(mode.getSummary().replaceAll("&nbsp;"," "));}
        else{
        Log.i("888", "onBindViewHolder:null ");
        binding.noticeContent.setVisibility(View.GONE);}
        if(1==mode.getIsTop())
        binding.isTop.setVisibility(View.VISIBLE);
        else
            binding.isTop.setVisibility(View.GONE);

        binding.setMode(mode);

    }
}
