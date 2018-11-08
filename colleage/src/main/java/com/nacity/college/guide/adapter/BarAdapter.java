package com.nacity.college.guide.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nacity.college.R;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.LocateHorizontalView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 龙衣 on 17-2-20.
 **/

public class BarAdapter extends RecyclerView.Adapter implements LocateHorizontalView.IAutoLocateHorizontalView {
    private List<String> ages;
    private Context context;
    private View itemView;
    private List<TextView>textViewList=new ArrayList<>();

    public BarAdapter(Context context, List<String> ages){
        this.ages = ages;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_bar,parent,false);
        this.itemView = itemView;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        textViewList.add(((ViewHolder)holder).tvAge);
        ((ViewHolder)holder).tvAge.setText(String.valueOf(ages.get(position)));
        ((ViewHolder)holder).tvAge.setOnClickListener(v -> EventBus.getDefault().post(new Event<>("SelectBarPosition", position)));
    }

    @Override
    public int getItemCount() {
        return ages.size();
    }

    @Override
    public View getItemView() {
        return itemView;
    }

    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        ViewHolder holder1 = (ViewHolder) holder;

        if(isSelected){
            holder1.tvAge.setTextColor(Color.parseColor("#6d75a4"));
        }else{
            holder1.tvAge.setTextColor(Color.parseColor("#b4b4b4"));
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAge;

        private ViewHolder(View itemView) {
            super(itemView);
            tvAge = (TextView) itemView.findViewById(R.id.tv_age);
        }
    }
}
