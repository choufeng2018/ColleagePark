package com.nacity.college.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nacity.college.MainApp;

import java.util.List;

/**
 * Created by xzz on 2017/6/24.
 **/

public class BaseAdapter<T,H > extends RecyclerView.Adapter<BindingHolder<H>> {

    protected List<T> mList;
    protected Context mContext;

    public BaseAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public BindingHolder<H> onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BindingHolder<H> holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void setList(List<T> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    protected void displayImage(String url, ImageView imageView){
        Glide.with(mContext).load(MainApp.getImagePath(url)).into(imageView);
    }
    protected void displayImageNoBaseUrl(String url, ImageView imageView){
        Glide.with(mContext).load(url).into(imageView);
    }
    public interface OnItemClickListener<T>{
        void itemClick(T mode);
    }

    protected OnItemClickListener<T> listener;

    public void setOnItemClickListener(OnItemClickListener<T> listener){
        this.listener=listener;

    }

    public int getScreenWidthPixels(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }


}
