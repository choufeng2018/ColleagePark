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
import com.nacity.college.property.fragment.PraiseRecordFragment;
import com.nacity.college.property.fragment.PropertyPraiseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/9/4.
 **/

public class PropertySuggestActivity extends BaseActivity {
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
        setTitle(Constant.SUGGEST);
        setTpi(Constant.PROPERTY_SUGGEST_TPI);
    }

    public void setTpi(String[] titles) {
        mFragments.add(new PropertyPraiseFragment(2));
        mFragments.add(new PraiseRecordFragment(2));
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
