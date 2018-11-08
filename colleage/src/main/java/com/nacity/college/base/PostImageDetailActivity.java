package com.nacity.college.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.nacity.college.R;
import com.nacity.college.MainApp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/7/1.
 **/

public class PostImageDetailActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.title_name)
    TextView titleName;
    private String[] paths;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image_detail);
        ButterKnife.bind(this);
        setTitle("");
        getIntentData();
    }

    private void getIntentData() {
        String currentPath = getIntent().getStringExtra("CurrentPath");
        String pathList = getIntent().getStringExtra("PathList");
        paths = pathList.split(";");
        viewPager.setAdapter(adapter);
        for (int i = 0; i < paths.length; i++) {
            if (currentPath.equals(paths[i])) {
                viewPager.setCurrentItem(i);
                titleName.setText(i+1 + "/" + paths.length);
            }
        }
        setViewPagerListener(paths.length);
    }

    private void setViewPagerListener(int length) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                titleName.setText(position+1 + "/" + length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return paths.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView view = new PhotoView(PostImageDetailActivity.this);
            view.enable();
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            view.setTransitionName("LifeImage");
            Glide.with(appContext).load(MainApp.getImagePath(paths[position])+"?imageMogr2/thumbnail/500x").into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };
}