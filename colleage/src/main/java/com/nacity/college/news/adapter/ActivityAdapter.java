package com.nacity.college.news.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ActivityParkItemBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

/**
 * Created by usb on 2017/12/11.
 */

public class ActivityAdapter  extends BaseAdapter<NewsTo,ActivityParkItemBinding> {

    public ActivityAdapter(Context context) {
        super(context);
        this.mContext=context;
    }


    @Override
    public BindingHolder<ActivityParkItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityParkItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_park_item, parent, false);
        BindingHolder<ActivityParkItemBinding> holder= new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        holder.setIsRecyclable(false);
        return holder;
    }
    @Override
    public void onBindViewHolder(BindingHolder<ActivityParkItemBinding> holder, int position) {
        NewsTo mode=mList.get(position);
        ActivityParkItemBinding binding = holder.getBinding();

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
        //状态：0 预热中，1 未开始，2 进行中， 3 已结束，4 停用
        if(0==mode.getStatus()){
            binding.state.setVisibility(View.VISIBLE);
             binding.state.setBackgroundColor(Color.parseColor("#6c76a6"));
            binding.state.setText("预热中");
        }
        else if(2==mode.getStatus()){
            binding.state.setVisibility(View.VISIBLE);
            binding.state.setBackgroundColor(Color.parseColor("#fd665e"));
            binding.state.setText("进行中");
        }
        else if(3==mode.getStatus()){
            binding.state.setVisibility(View.VISIBLE);
            binding.state.setBackgroundColor(Color.parseColor("#b4b4b4"));
            binding.state.setText("已结束");
        }else{
            binding.state.setVisibility(View.GONE);
        }
        binding.setMode(mode);

    }
}
