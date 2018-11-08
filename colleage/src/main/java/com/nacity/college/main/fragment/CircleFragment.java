package com.nacity.college.main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.utils.EasySlidingTabRecord;
import com.nacity.college.circle.CirclePersonalCenterActivity;
import com.nacity.college.circle.PostActivity;
import com.nacity.college.circle.fragment.LifeFragment;
import com.nacity.college.main.model.CircleFragmentModel;
import com.nacity.college.main.presenter.CircleFragmentPresenter;
import com.nacity.college.main.view.CircleFragmentView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xzz on 2017/9/7.
 **/

@SuppressLint("ValidFragment")
public class CircleFragment extends BaseFragment implements CircleFragmentView {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.sub_textView)
    TextView subTextView;
    @BindView(R.id.tpi)
    EasySlidingTabRecord tpi;
    @BindView(R.id.v_icon)
    View vIcon;
    @BindView(R.id.post)
    ImageView post;
    private boolean isFirst = true;


    private List<LifeFragment> mFragments = new ArrayList<>();
    Unbinder unbinder;
    private FragmentPagerAdapter adapter;
    private String title[] = new String[3];
    private List<NeighborPostTypeTo> typeList = new ArrayList<>();
    private int currentPosition;

    @Override
    public void onStart() {
        super.onStart();
        if (!isFirst)

            return;


        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size() == 0 ? 0 : mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        };
        setTpi();
        isFirst = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_circle, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        if (!isFirst)
            setTpi();
        setView();
        return mRootView;
    }

    private void setView() {
        if (vIcon!=null) {
            if (userInfo.getUserInfoTo().getUserType() == 6 || userInfo.getUserInfoTo().getUserType() == 7)
                vIcon.setVisibility(View.VISIBLE);
            else
                vIcon.setVisibility(View.GONE);
        }
    }

    public void setTpi() {
        if (isFirst) {
            CircleFragmentModel model = new CircleFragmentPresenter(this);
            model.getCircleType();
        }
        viewPager.setAdapter(adapter);
        tpi.setViewPager(viewPager);

        tpi.setTextSize(getScreenWidth() * 26 / 750);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                if (mFragments.size() > 0)
                    mFragments.get(position).init();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.post, R.id.person_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.post:


                Intent intent = new Intent(appContext, PostActivity.class);
                intent.putExtra("TypeList", (Serializable) typeList);
                intent.putExtra("Index", currentPosition == 0 ? 1 : currentPosition);
                startActivity(intent);
                break;
            case R.id.person_layout:
                intent = new Intent(appContext, CirclePersonalCenterActivity.class);
                NeighborPostTo postTo = new NeighborPostTo();

                postTo.setUserPic(userInfo.getUserInfoTo().getUserPic());
                postTo.setNickname(userInfo.getUserInfoTo().getNickname());
                postTo.setCreateUserId(userInfo.getSid());
                postTo.setUserType(userInfo.getUserInfoTo().getUserType());
                intent.putExtra("PoseTo", postTo);
                startActivity(intent);
                break;
        }

    }


    @Override
    public void getTypeSuccess(List<NeighborPostTypeTo> data) {

        mFragments.clear();
        typeList.clear();
        typeList.addAll(data);
        title = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {

            title[i] = data.get(i).getName();
            mFragments.add(new LifeFragment(data.get(i).getTypeIndex(), subTextView, getActivity()));

        }

        adapter.notifyDataSetChanged();
        tpi.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(mFragments.size());
        mFragments.get(0).init();
    }

    public void setUserInfo(){
        setView();
    }
}
