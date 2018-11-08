package com.nacity.college.circle.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.circle.CareListTo;
import com.nacity.college.R;
import com.nacity.college.databinding.CareActivityItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.Event;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

import de.greenrobot.event.EventBus;


/**
 * Created by xzz on 2017/6/24.
 **/

public class CareAdapter extends BaseAdapter<CareListTo,CareActivityItemBinding> {


    public CareAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @Override
    public BindingHolder<CareActivityItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        CareActivityItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.care_activity_item,parent,false);
        BindingHolder<CareActivityItemBinding> holder= new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<CareActivityItemBinding> holder, int position) {
        CareListTo mode=mList.get(position);
        CareActivityItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        Glide.with(mContext).load(MainApp.getImagePath(mode.getFollowUserPic())).into(binding.headImage);
        binding.nickName.setText(mode.getFollowNickname());
        binding.cancelCare.setOnClickListener(v -> EventBus.getDefault().post(new Event<>("CancelCare",mode,position)));
        binding.vIcon.setVisibility(mode.getFollowUserType()==6||mode.getFollowUserType()==7? View.VISIBLE:View.GONE);
    }


}


