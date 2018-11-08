package com.nacity.college.property.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;

import java.util.List;


/**
 *  Created by usb on 2017/10/26.
 */

public class DistributionRightAdapter extends RecyclerView.Adapter<DistributionRightAdapter.ItemViewHolder> {
    protected Context mContext;
    protected List<ServiceTypeTo> mcategoryList;
    protected List<ServiceTypeTo> mContentList;

    public DistributionRightAdapter(Context context,LRecyclerView mRecyclerView) {
        this.mContext = context;
        this.mRecyclerView = mRecyclerView;
    }
    public void setCategoryList(List<ServiceTypeTo> categoryList) {
        this.mcategoryList = categoryList;

    }

    @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.distribution_right_item, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            holder.setIsRecyclable(false);
            return holder;
//        DistributionRightItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.distribution_right_item, null, false);
//            holder.setBinding(binding);
//        return holder;
    }

    @Override
    public int getItemCount() {
        return mcategoryList==null?0:mcategoryList.size()+mContentList.size();
    }

    @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {

            ServiceTypeTo mode = mContentList.get(position);
        holder.getName().setText(mode.getName());
        holder.getExplain().setText(mode.getContent());
            if (mode.getClick()>0){
                holder.getGoodsNum().setText(mode.getClick()+"");
                holder.getGoodsNum().setVisibility(View.VISIBLE);
                holder.getReduce().setVisibility(View.VISIBLE);
            }else {
                holder.getGoodsNum().setVisibility(View.GONE);
                holder.getReduce().setVisibility(View.GONE);
            }
            if(mode.getImg()==null){
                Glide.with(MainApp.mContext).load(mode.getImg()).into(holder.getImage());
            }else{
                Glide.with(MainApp.mContext).load(R.drawable.transparent_bg).into(holder.getImage());
            }
        String price=Double.toString(mode.getUnitPrice());
        String [] priceList=price.split("\\.");
            holder.getLeftPrice().setText(priceList[0]+".");
            holder.getRightPrice().setText(priceList[1]+"0");
        Log.i("3333", "price: "+price);
        Integer.parseInt(holder.getGoodsNum().getText().toString());
            holder.getReduce().setOnClickListener(v -> {
                listenerReduce.itemReduceClick(mode,position);
                Toast.makeText(mContext,position+"position",Toast.LENGTH_LONG).show();
                int num= Integer.parseInt( holder.getGoodsNum().getText().toString())-1;
                mContentList.get(position).setClick(num);
                if(Integer.parseInt(holder.getGoodsNum().getText().toString())==1){
                    holder.getReduce().setAnimation(getHiddenAnimation());
                    holder.getReduce().setVisibility(View.GONE);
                    holder.getGoodsNum().setVisibility(View.GONE);
                }
            holder.getGoodsNum().setText(num+"");
            });

        holder.getIncrease().setOnClickListener(v -> {
                listenerIncrease.itemIncreaseClick(mode,position);
                int num=Integer.parseInt( holder.getGoodsNum().getText().toString())+1;
            mContentList.get(position).setClick(num);
                if(Integer.parseInt( holder.getGoodsNum().getText().toString())==0){
                    holder.getIncrease().setClickable(false);
                    holder.getReduce().setAnimation(getShowAnimation());
                    holder.getReduce().setVisibility(View.VISIBLE);
                    holder.getGoodsNum().postDelayed(() -> {
                        holder.getGoodsNum().setVisibility(View.VISIBLE);
                        holder.getGoodsNum().setText(num + "");
                        holder.getIncrease().setClickable(true);
                    },550);
                }else{
                    holder.getGoodsNum().setText(num+"");
                }
            });
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
    public interface OnItemIncreaseClickListener {
        void itemIncreaseClick(ServiceTypeTo mode, int position);
    }

    private OnItemIncreaseClickListener listenerIncrease;

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
    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public  View itemView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
    }

    public  class ItemViewHolder  extends RecyclerView.ViewHolder {
        public  View itemView;
        public ItemViewHolder(View itemView) {
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

    public void setList(List<ServiceTypeTo> list) {
        this.mcategoryList = list;
        for(int i = 0;i<mcategoryList.size();i++){
            if(mContentList!=null){
                mContentList.addAll(mcategoryList.get(i).getChild());
            }
        }
        notifyDataSetChanged();
    }

    //获取当前球队的类型
    public int getSortType(int position) {
        int sort = -1;
        int sum = 0;
        for (int i=0;i<mcategoryList.size();i++){
            if(position>=sum){
                sort++;
            }else {
                return sort;
            }
            sum += mcategoryList.get(i).getChild().size();
        }
        return sort;
    }


    public void setSelection(int pos) {
        if (pos < mContentList.size()) {
            moveToPosition(pos);
        }
}
    private boolean mShouldScroll = false;
    private RecyclerView mRecyclerView;
    private void moveToPosition(final int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
        int pos =n;
        mShouldScroll = false;
        mRecyclerView.setOnScrollListener(new RecyclerViewListener(n));
        //然后区分情况
        if (pos <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.scrollToPosition(pos);
        } else if (pos <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(pos - firstItem).getTop() - 50;
            mRecyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时,调用scrollToPosition只会将该项滑动到屏幕上。需要再次滑动到顶部
            mRecyclerView.scrollToPosition(pos);
            //这里这个变量是用在RecyclerView滚动监听里面的
            mShouldScroll = true;
        }
    }
    /**
     * 滚动监听
     */
    class RecyclerViewListener extends RecyclerView.OnScrollListener{
        private int n = 0;
        RecyclerViewListener(int n) {
            this.n = n;
        }
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动
            if (mShouldScroll ){
                mShouldScroll = false;
                moveToPosition(n);
            }
        }
    }
}