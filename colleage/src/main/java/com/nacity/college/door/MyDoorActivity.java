package com.nacity.college.door;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.door.fragment.ApplyDoorRecordFragment;
import com.nacity.college.door.fragment.MyDoorFragment;
import com.nacity.college.door.fragment.RemoteOpenDoorFragment;
import com.nacity.college.door.presenter.MyDoorPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2018/1/27.
 **/

public class MyDoorActivity extends BaseActivity<Boolean> {
    @BindView(R.id.tpi)
    NavigationTabStrip tpi;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private MyDoorPresenter presenter=new MyDoorPresenter(this);
    private boolean remoteDoorOpen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_door);
        ButterKnife.bind(this);
        setTitle(Constant.MY_DOOR);

        presenter.displayRemoteDoor(apartmentInfo.getSid(),userInfo.getSid());
        loadingShow();
    }

    public void setTpi(String[] titles) {
        mFragments.add(new MyDoorFragment());
        mFragments.add(new ApplyDoorRecordFragment());
        if (remoteDoorOpen)
            mFragments.add(new RemoteOpenDoorFragment());
        tpi.setTitleSize((float) (getScreenWidth() * 0.0391));
        tpi.setTabIndex(0, true);
        viewPager.setAdapter(adapter);
        tpi.setTitles(titles);
        tpi.setViewPager(viewPager, getIntent().getIntExtra("Index", 0));
        adapter.notifyDataSetChanged();

    }

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return remoteDoorOpen?3:2;
        }
    };

    @Override
    public void getDataSuccess(Boolean data) {
        super.getDataSuccess(data);
        remoteDoorOpen=data;
        setTpi(remoteDoorOpen? Constant.MY_DOOR_TPI_CONTAIN_REMOTE: Constant.MY_DOOR_TPI);
    }
}
