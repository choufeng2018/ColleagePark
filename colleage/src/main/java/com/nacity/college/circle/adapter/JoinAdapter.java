package com.nacity.college.circle.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.circle.CircleJoinTo;
import com.nacity.college.R;
import com.nacity.college.databinding.JoinActivityItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class JoinAdapter extends BaseAdapter<CircleJoinTo,JoinActivityItemBinding> {



    public JoinAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    public BindingHolder<JoinActivityItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        JoinActivityItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.join_activity_item,parent,false);
        BindingHolder<JoinActivityItemBinding> holder= new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<JoinActivityItemBinding> holder, int position) {
        CircleJoinTo mode=mList.get(position);
        JoinActivityItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        Glide.with(mContext).load(MainApp.getImagePath(mode.getPostUserPic())).into(binding.headImage);
        binding.vIcon.setVisibility(mode.getPostUserType()==6||mode.getPostUserType()==7? View.VISIBLE:View.GONE);
    }


}


