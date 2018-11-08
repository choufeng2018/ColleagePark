package com.nacity.college.guide.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.guide.EnterpriseTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ParkEnterpriseItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class EnterpriseAdapter extends BaseAdapter<EnterpriseTo, ParkEnterpriseItemBinding> {


    public EnterpriseAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<ParkEnterpriseItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ParkEnterpriseItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.park_enterprise_item, parent, false);
        BindingHolder<ParkEnterpriseItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<ParkEnterpriseItemBinding> holder, int position) {
        EnterpriseTo mode = mList.get(position);
        ParkEnterpriseItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        Glide.with(MainApp.mContext).load(mode.getHomePic()).into(binding.enterpriseImage);
       if (position%2==0){
           binding.lineOne.setVisibility(View.VISIBLE);
           binding.lineTow.setVisibility(View.GONE);
       }else {
           binding.lineOne.setVisibility(View.GONE);
           binding.lineTow.setVisibility(View.VISIBLE);
       }

    }


}


