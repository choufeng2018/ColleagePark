package com.nacity.college.base.banner;

import android.content.Intent;
import android.text.TextUtils;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.AdWebActivity;
import com.nacity.college.base.TextPictureWebActivity;

import java.util.List;

/**
 * Created by xzz on 2017/6/25.
 **/

public class BannerUtil {
    public static void setBanner(ConvenientBanner banner,List<AdvertiseTo> adList,int loadSrc){

        banner.setPages(
                new CBViewHolderCreator<BannerHolderView>() {
                    @Override
                    public BannerHolderView createHolder() {
                        return new BannerHolderView(loadSrc);
                    }
                }, adList);
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
     banner.setOnItemClickListener(position -> {
         if (!TextUtils.isEmpty(adList.get(position).getAdUrl())){
             Intent intent=new Intent(MainApp.mContext, AdWebActivity.class);
             intent.putExtra("Url",adList.get(position).getAdUrl());
             intent.putExtra("Title",adList.get(position).getAdSubject());
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             MainApp.mContext.startActivity(intent);
         }else if (!TextUtils.isEmpty(adList.get(position).getAdContent())){
             Intent intent=new Intent(MainApp.mContext, TextPictureWebActivity.class);
             intent.putExtra("Content",adList.get(position).getAdContent());
             intent.putExtra("Title",adList.get(position).getAdSubject());
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             MainApp.mContext.startActivity(intent);
         }
     });
    }
}
