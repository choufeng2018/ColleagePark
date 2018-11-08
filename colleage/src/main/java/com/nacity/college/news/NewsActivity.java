package com.nacity.college.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.news.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  Created by usb on 2017/8/30.园区公告
 */

public class NewsActivity extends BaseActivity {
    @BindView(R.id.tpi)
    NavigationTabStrip tpi;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private     List<Fragment> mFragments=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        String[] titles=new String[]{"物业公告","管委会公告","园区风采"};
        tpi.setTabIndex(0, true);
        viewPager.setAdapter(adapter);
        tpi.setTitles(titles);
        tpi.setViewPager(viewPager, 0);
        setTitle(getIntent().getStringExtra("Title"));
        mFragments.add(new NewsFragment("1"));
        mFragments.add(new NewsFragment("2"));
        mFragments.add(new NewsFragment("3"));
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




}
