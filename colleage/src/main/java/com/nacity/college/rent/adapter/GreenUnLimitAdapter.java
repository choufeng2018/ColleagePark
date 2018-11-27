package com.nacity.college.rent.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.rent.HouseRentTo;
import com.nacity.college.R;
import com.nacity.college.databinding.RentHouseItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;


/**
 * Created by xzz on 2017/6/24.
 **/

public class GreenUnLimitAdapter extends BaseAdapter<HouseRentTo, RentHouseItemBinding> {


    public GreenUnLimitAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<RentHouseItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        RentHouseItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rent_house_item, parent, false);
        BindingHolder<RentHouseItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<RentHouseItemBinding> holder, int position) {
        HouseRentTo mode = mList.get(position);
        RentHouseItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        Glide.with(MainApp.mContext).load(mode.getHouseImages().split(";")[0]).error(R.drawable.park_rent_house_load).into(binding.houseImage);
        binding.housePrice.setText(mode.getHousePrice());

        binding.houseType.setVisibility(mode.getHouseChosen() ==1 ? View.GONE : View.VISIBLE);
        binding.point.setVisibility(TextUtils.isEmpty(mode.getHouseDecoration()) ? View.GONE : View.VISIBLE);

        binding.houseImage.setOnClickListener(v -> {
            if (listener != null) {
                listener.itemClick(mode, binding.houseImage);

            }
        });

    }

    public interface OnItemClickListener {
        void itemClick(HouseRentTo mode, ImageView jumpView);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}


