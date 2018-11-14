package com.nacity.college.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.utils.PingFangTextView;
import com.nacity.college.news.fragment.NewsFragment;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by usb on 2017/8/30.园区公告
 */

public class NewsActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.property_notice)
    PingFangTextView propertyNotice;
    @BindView(R.id.manager_notice)
    PingFangTextView managerNotice;
    @BindView(R.id.park_notice)
    PingFangTextView parkNotice;
    @BindView(R.id.move_line)
    AutoRelativeLayout moveLine;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        viewPager.setAdapter(adapter);
        setTitle(getIntent().getStringExtra("Title"));
        mFragments.add(new NewsFragment("1"));
        mFragments.add(new NewsFragment("2"));
        mFragments.add(new NewsFragment("3"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                moveLine.setX(position*getScreenWidth()/3+positionOffsetPixels/3);
            }

            @Override
            public void onPageSelected(int position) {
                 propertyNotice.setTextColor(position==0? Color.parseColor("#4363b6"):Color.parseColor("#333333"));
                 managerNotice.setTextColor(position==1? Color.parseColor("#4363b6"):Color.parseColor("#333333"));
                 parkNotice.setTextColor(position==2? Color.parseColor("#4363b6"):Color.parseColor("#333333"));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    };


    @OnClick({R.id.property_notice, R.id.manager_notice, R.id.park_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.property_notice:
                viewPager.setCurrentItem(0);
                break;
            case R.id.manager_notice:
                viewPager.setCurrentItem(1);
                break;
            case R.id.park_notice:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
