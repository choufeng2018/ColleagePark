package com.nacity.college.property.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *  Created by usb on 2017/11/1.
 */

public class DistributionRightOtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private List<ServiceTypeTo> categoryList;
    private List<ServiceTypeTo> teamList = new ArrayList<>();
    private Context mContext;

    public DistributionRightOtherAdapter(Context context, List<ServiceTypeTo> categoryList) {
        setCategoryList(categoryList);
        mContext = context;
    }

    public void setCategoryList(List<ServiceTypeTo> categoryList) {
        this.categoryList = categoryList;
        for(int i = 0;i<categoryList.size();i++){
            if(teamList!=null){
                teamList.addAll(categoryList.get(i).getChild());
            }
        }
        notifyDataSetChanged();
    }

    public List<ServiceTypeTo> getCategoryList() {
        return categoryList;
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    /**
     * 返回值相同会被默认为同一项
     * @param position
     * @return
     */
    @Override
    public long getHeaderId(int position) {
        return getSortType(position);
    }

    //获取当前球队的类型
    public int getSortType(int position) {
        int sort = -1;
        int sum = 0;
        for (int i=0;i<categoryList.size();i++){
            if(position>=sum){
                sort++;
            }else {
                return sort;
            }
            sum += categoryList.get(i).getChild().size();
        }
        return sort;
    }
    /**
     * ===================================================================================================
     * header的ViewHolder
     * ===================================================================================================
     */
    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.header_type_item, viewGroup, false);
        HeaderViewHolder holder=new HeaderViewHolder(view);
        holder.setIsRecyclable(false);

        return holder;

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        HeaderViewHolder viewHolde = (HeaderViewHolder) viewHolder;
        viewHolde.setIsRecyclable(false);

        viewHolde.getTitle().setText(categoryList.get(getSortType(position)).getName());
//        textView.setText(categoryList.get(getSortType(position)).getName());
//        textView.setBackgroundColor(getRandomColor());
    }
    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }

    /**
     * ==================================================================================================
     * 以下为contentViewHolder
     * ==================================================================================================
     */
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.distribution_right_item, parent, false);
        ContentViewHolder holder=new ContentViewHolder(view);
        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        ContentViewHolder viewHolder = (ContentViewHolder) holder;
        ServiceTypeTo mode = teamList.get(position);
        viewHolder.getName().setText(mode.getName());
        viewHolder.getExplain().setText(mode.getContent());
        if (mode.getClick()>0){
            viewHolder.getGoodsNum().setText(mode.getClick()+"");
            viewHolder.getGoodsNum().setVisibility(View.VISIBLE);
            viewHolder.getReduce().setVisibility(View.VISIBLE);
        }else {
            viewHolder.getGoodsNum().setVisibility(View.GONE);
            viewHolder.getReduce().setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(mode.getImg()))
            Glide.with(MainApp.mContext).load(mode.getImg()).into(viewHolder.getImage());
        else{
            Glide.with(MainApp.mContext).load(R.drawable.distribution_load).into(viewHolder.getImage());
        }
//        else
//            Glide.with(MainApp.mContext).load(R.drawable.bg_withe).into(viewHolder.getImage());
        String price=Double.toString(mode.getUnitPrice());
        String [] priceList=price.split("\\.");
        viewHolder.getLeftPrice().setText(priceList[0]+".");
        viewHolder.getRightPrice().setText(priceList[1]+"0");
        Log.i("3333", "price: "+price);
        Integer.parseInt(viewHolder.getGoodsNum().getText().toString());
        viewHolder.getReduce().setOnClickListener(v -> {
            listenerReduce.itemReduceClick(mode,position);
            int num= Integer.parseInt( viewHolder.getGoodsNum().getText().toString())-1;
            teamList.get(position).setClick(num);
            if(Integer.parseInt(viewHolder.getGoodsNum().getText().toString())==1){
                viewHolder.getReduce().setClickable(false);
                viewHolder.getReduce().setAnimation(getHiddenAnimation());
                viewHolder.getReduce().setVisibility(View.GONE);
                viewHolder.getGoodsNum().setVisibility(View.GONE);
            }
            viewHolder.getGoodsNum().setText(num+"");
        });

        viewHolder.getIncrease().setOnClickListener(v -> {
            listenerIncrease.itemIncreaseClick(mode,position);
            int num=Integer.parseInt( viewHolder.getGoodsNum().getText().toString())+1;
            teamList.get(position).setClick(num);
            if(Integer.parseInt( viewHolder.getGoodsNum().getText().toString())==0){
                viewHolder.getIncrease().setClickable(false);
                viewHolder.getReduce().setClickable(true);
                viewHolder.getReduce().setAnimation(getShowAnimation());
                viewHolder.getReduce().setVisibility(View.VISIBLE);
                viewHolder.getGoodsNum().postDelayed(() -> {
                    viewHolder.getGoodsNum().setVisibility(View.VISIBLE);
                    viewHolder.getGoodsNum().setText(num + "");
                    viewHolder.getIncrease().setClickable(true);
                },550);
            }else{
                viewHolder.getGoodsNum().setText(num+"");
            }
        });
    }
    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public  View itemView;

        public HeaderViewHolder(View itemView) {

            super(itemView);
            this.itemView=itemView;

        }
        public TextView getTitle(){
            return (TextView)itemView.findViewById(R.id.tvGoodsItemTitle);
        }
    }
    private class ContentViewHolder extends RecyclerView.ViewHolder {
        public  View itemView;
        public ContentViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
        public TextView getGoodsNum(){
            return (TextView)itemView.findViewById(R.id.goods_number);
        }
        public TextView getExplain(){
            return (TextView)itemView.findViewById(R.id.explain);
        }
        public TextView getName(){
            return (TextView)itemView.findViewById(R.id.name);
        }  public TextView getRightPrice(){
            return (TextView)itemView.findViewById(R.id.price_right);
        }
        public TextView getLeftPrice(){
            return (TextView)itemView.findViewById(R.id.price_left);
        }
        public ImageView getIncrease(){
            return (ImageView)itemView.findViewById(R.id.increase);
        }
        public ImageView getReduce(){
            return (ImageView)itemView.findViewById(R.id.reduce);
        }
        public ImageView getImage(){
            return (ImageView)itemView.findViewById(R.id.water_image);
        }
        }
    private OnItemIncreaseClickListener listenerIncrease;

    public interface OnItemIncreaseClickListener {
        void itemIncreaseClick(ServiceTypeTo mode, int position);
    }


    public void setOnItemIncreaseClickListener(OnItemIncreaseClickListener listener) {
        this.listenerIncrease = listener;

    }
    public interface OnItemReduceClickListener {
        void itemReduceClick(ServiceTypeTo mode, int position);
    }

    private OnItemReduceClickListener listenerReduce;

    public void setOnItemReduceClickListener(OnItemReduceClickListener listener) {
        this.listenerReduce = listener;

    }
    //显示减号的动画
    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720, RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    //隐藏减号的动画
    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
    }


