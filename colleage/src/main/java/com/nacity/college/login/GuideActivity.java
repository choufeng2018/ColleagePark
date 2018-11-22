package com.nacity.college.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.utils.SpUtil;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/9/19.
 **/

public class GuideActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.point_layout)
    AutoLinearLayout pointLayout;
    @BindView(R.id.parent_bg)
    AutoRelativeLayout parentBg;
    @BindView(R.id.guide_bg2)
    ImageView guideBg2;
    @BindView(R.id.guide_bg4)
    ImageView guideBg4;
    @BindView(R.id.point1)
    View point1;
    @BindView(R.id.point2)
    View point2;
    @BindView(R.id.point3)
    View point3;
    @BindView(R.id.point4)
    View point4;
    private Integer[] imageList = {R.drawable.guide_text_bg1, R.drawable.guide_text_bg2, R.drawable.guide_text_bg3, R.drawable.guide_text_bg4};
    private int currentPage;
    private List<View> pointList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        setViewPager();
        setStart();

    }


    private void setStart() {
        btnStart.setOnClickListener(v -> {
            if (currentPage == 2 || currentPage == 1) {
                SpUtil.put("SecondEnter", true);
                Intent intent = new Intent(appContext, LoginActivity.class);
                startActivity(intent);
                goToAnimation(1);

            } else if (currentPage == 3) {
                SpUtil.put("SecondEnter", true);
                Intent intent = new Intent(appContext, LoginActivity.class);
                startActivity(intent);
                goToAnimation(1);
                finish();
            }
        });
    }

    private void setViewPager() {
        viewPager.setAdapter(new MyAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPoint(position);
                currentPage = position;
                switch (position) {
                    case 0:
                        parentBg.setBackgroundResource(R.drawable.guide_bg1);
                        Glide.with(appContext).load(R.drawable.guide_bg2).into(guideBg2);
                        guideBg2.animate().alpha(0f).setDuration(800);
                        break;
                    case 1:
                        Glide.with(appContext).load(R.drawable.guide_bg2).into(guideBg2);
                        guideBg2.animate().alpha(1f).setDuration(800);
                        break;
                    case 2:
                        Glide.with(appContext).load(R.drawable.guide_bg14).into(guideBg4);
                        guideBg4.animate().alpha(0f).setDuration(800);
                        parentBg.setBackgroundResource(R.drawable.guide_bg2);
                        break;
                    case 3:

                        Glide.with(appContext).load(R.drawable.guide_bg14).into(guideBg4);
                        guideBg4.animate().alpha(1f).setDuration(800);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View mView = View.inflate(appContext, R.layout.image_layout, null);
            ImageView imageView = (ImageView) mView.findViewById(R.id.image_view);
            imageView.setBackgroundResource(imageList[position]);
            container.addView(mView);
            return mView;
        }
    }



    private void setPoint(int position) {
         point1.setBackgroundResource(position==0?R.drawable.start_point_un_select:R.drawable.start_point_select);
        point2.setBackgroundResource(position==1?R.drawable.start_point_un_select:R.drawable.start_point_select);
        point3.setBackgroundResource(position==2?R.drawable.start_point_un_select:R.drawable.start_point_select);
        point4.setBackgroundResource(position==3?R.drawable.start_point_un_select:R.drawable.start_point_select);
    }
}
