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
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

import de.greenrobot.event.EventBus;


/**
 * Created by xzz on 2017/6/24.
 **/

public class FansAdapter extends BaseAdapter<CareListTo, CareActivityItemBinding> {


    public FansAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<CareActivityItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        CareActivityItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.care_activity_item, parent, false);
        BindingHolder<CareActivityItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<CareActivityItemBinding> holder, int position) {
        CareListTo mode = mList.get(position);
        CareActivityItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        Glide.with(mContext).load(MainApp.getImagePath(mode.getUserPic())).into(binding.headImage);
        binding.careMeIcon.setVisibility(!mode.isFollowed() ? View.GONE : View.VISIBLE);
        binding.cancelCare.setText(!mode.isFollowed() ? Constant.CARE : Constant.ALREADY_CARE);
        binding.nickName.setText(mode.getNickname());
        binding.vIcon.setVisibility(mode.getUserType()==6||mode.getUserType()==7? View.VISIBLE:View.GONE);
        if (!mode.isFollowed())
            binding.cancelCare.setOnClickListener(v -> EventBus.getDefault().post(new Event<>("Care", position)));

    }


}


