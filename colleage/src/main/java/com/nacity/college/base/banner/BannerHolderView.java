package com.nacity.college.base.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.college.common_libs.domain.apartment.AdvertiseTo;


/**
 * Created by xzz on 2017/6/25.
 **/

public class BannerHolderView implements Holder<AdvertiseTo>{
    private ImageView imageView;
    private int loadSrc;
    public BannerHolderView(int loadSrc){
        this.loadSrc=loadSrc;
    }
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, AdvertiseTo data) {
        Glide.with(context).load(data.getAdImage()).placeholder(loadSrc).into(imageView);
    }
}
