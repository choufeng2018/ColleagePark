package com.nacity.college.property.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.FeedbackTo;
import com.nacity.college.R;
import com.nacity.college.databinding.PropertyPraiseRecordItemBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.Constant;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.base.utils.DateUtil;


/**
 * Created by xzz on 2017/6/24.
 **/

public class PropertyPraiseRecordAdapter extends BaseAdapter<FeedbackTo, PropertyPraiseRecordItemBinding> {

    private int type;

    public PropertyPraiseRecordAdapter(Context context, int type) {
        super(context);
        this.mContext = context;
        this.type = type;
    }

    @Override
    public BindingHolder<PropertyPraiseRecordItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        PropertyPraiseRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.property_praise_record_item, parent, false);
        BindingHolder<PropertyPraiseRecordItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<PropertyPraiseRecordItemBinding> holder, int position) {
        FeedbackTo mode = mList.get(position);
        PropertyPraiseRecordItemBinding binding = holder.getBinding();
        binding.setMode(mode);
        Glide.with(MainApp.mContext).load(type == 1 ? R.drawable.property_praise_icon : R.drawable.property_suggest_icon).into(binding.serviceIcon);
        binding.messageInfo.setText(type == 1 ? Constant.PRAISE_MESSAGE : Constant.SUGGEST_MESSAGE);
        binding.serviceTime.setText(DateUtil.getDateTimeFormat(DateUtil.mDateFormatString, mode.getCreateTime()));

    }


}


