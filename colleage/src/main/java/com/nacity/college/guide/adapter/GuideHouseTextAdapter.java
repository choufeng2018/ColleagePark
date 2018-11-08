package com.nacity.college.guide.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.college.common_libs.domain.apartment.GuideHouseTypeTo;
import com.nacity.college.R;
import com.nacity.college.databinding.GuideHouseTextViewBinding;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


/**
 * Created by xzz on 2017/6/24.
 **/

public class GuideHouseTextAdapter extends BaseAdapter<GuideHouseTypeTo, GuideHouseTextViewBinding> {

    private int mHighlight = -1;
    private boolean isClick;
    private List<TextView>textViewList=new ArrayList<>();

    public GuideHouseTextAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public BindingHolder<GuideHouseTextViewBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        GuideHouseTextViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.guide_house_text_view, parent, false);
        BindingHolder<GuideHouseTextViewBinding> holder = new BindingHolder<>(binding.getRoot());
        ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
        //  params.width =(int)getItemWidth();

        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<GuideHouseTextViewBinding> holder, int position) {
        GuideHouseTypeTo mode = mList.get(position);
        GuideHouseTextViewBinding binding = holder.getBinding();
        binding.setMode(mode);
         textViewList.add(position,binding.houseName);


            if (isSelect(position)) {
                //  EventBus.getDefault().post(new Event<>("SelectBarPosition", position));
                if (listener != null)
                    listener.houseSelect(position);
                binding.houseName.setTextColor(Color.parseColor("#6d75a4"));
            } else {

                binding.houseName.setTextColor(Color.parseColor("#b4b4b4"));

        }

        isClick=false;
        binding.houseName.setOnClickListener(v -> {
            isClick=true;
            Observable.from(textViewList).subscribe(textView -> textView.setTextColor(Color.parseColor("#b4b4b4")));
            if (clkicListener != null)
                clkicListener.houseClick(position);
            ((TextView) v).setTextColor(Color.parseColor("#6d75a4"));
        });

    }


    public float getItemWidth() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int ITEM_NUM = 3;
        return displayMetrics.widthPixels / ITEM_NUM;
    }

    public void highlightItem(int position) {
        mHighlight = position;
        int offset = 3 / 2;
        for (int i = position - offset; i <= position + offset; ++i)
            notifyItemChanged(i);
    }

    public boolean isSelect(int position) {
        return mHighlight == position;
    }

    public interface HouseSelectListener {
        void houseSelect(int position);
    }

    private HouseSelectListener listener;

    public void setHouseSelect(HouseSelectListener listener) {
        this.listener = listener;
    }

    public interface HouseClickListener {
        void houseClick(int position);
    }

    private HouseClickListener clkicListener;

    public void setHouseClick(HouseClickListener listener) {
        this.clkicListener = listener;
    }
}


