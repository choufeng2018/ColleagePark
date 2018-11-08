package com.nacity.college.property;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.property.fragment.PublicRepairFragment;
import com.nacity.college.property.fragment.ServiceRecordFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/9/4.
 **/

public class PublicRepairActivity extends BaseActivity {
    @BindView(R.id.tpi)
    NavigationTabStrip tpi;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_repair);
        ButterKnife.bind(this);
        setTitle(Constant.PUBLIC_REPAIR);
        setTpi(Constant.PROPERTY_SERVICE_RECORD);
    }

    public void setTpi(String[] titles) {
        mFragments.add(new PublicRepairFragment());
        mFragments.add(new ServiceRecordFragment(getIntent().getStringExtra("CategorySid")));
        tpi.setTitleSize((float) (getScreenWidth() * 0.0361));
        tpi.setTabIndex(0, true);
        viewPager.setAdapter(adapter);
        tpi.setTitles(titles);
        tpi.setViewPager(viewPager, getIntent().getIntExtra("Index", 0));


    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
}
