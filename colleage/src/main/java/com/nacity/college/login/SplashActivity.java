package com.nacity.college.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.utils.OutAnimationDrawable;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.login.model.SplashModel;
import com.nacity.college.login.presenter.SplashPresenter;
import com.nacity.college.login.view.SplashView;
import com.nacity.college.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by xzz on 2017/8/25.
 **/

public class SplashActivity extends BaseActivity implements SplashView {

    @BindView(R.id.launch_image)
    ImageView launchImage;
    private Handler handler = new Handler();
    @BindView(R.id.loading_pic)
    ImageView loadingPic;
    private boolean haveLoadAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SpUtil.put("IsSplashActivity",true);//防止推送在splash出现
        ButterKnife.bind(this);
        SplashModel model = new SplashPresenter(this);
        launchAnimation();


    }


    @Override
    public void setLoadingAd(AdvertiseTo adInfoTo, long delayTime) {
        haveLoadAd = true;
        handler.postDelayed(() -> {
            Glide.with(appContext).load(MainApp.getImagePath(adInfoTo.getAdImage())).into(loadingPic);
            loadingPic.animate()
                    .alpha(1f)
                    .setDuration(1500);
        }, 3000);

    }


    private void launchAnimation() {
//        launchImage.setImageResource(R.drawable.splash_animation);
//        AnimationDrawable animationDrawable = (AnimationDrawable) launchImage.getDrawable();
//        animationDrawable.start();
        OutAnimationDrawable.animateRawManuallyFromXML(R.drawable.splash_animation, launchImage, null,null);
        handler.postDelayed(() -> toMainOrRegisterActivity(haveLoadAd), 3000);

    }

    @OnClick(R.id.loading_pic)
    public void onViewClicked() {
    }

    /*
     *进入主页或者注册登录
     */
    private void toMainOrRegisterActivity(boolean haveLoadAd) {

        handler.postDelayed(() -> {
            Intent intent;
            if (!SpUtil.getBoolean("SecondEnter"))
                intent = new Intent(appContext, GuideActivity.class);
            else if (userInfo.isLogin()&&userInfo.getUserInfoTo()!=null) {

                if (TextUtils.isEmpty(userInfo.getUserInfoTo().getNickname()))
                    intent = new Intent(appContext, FinishDataActivity.class);
                else
                    intent = new Intent(appContext, MainActivity.class);

            } else
                intent = new Intent(appContext, LoginActivity.class);

            startActivity(intent);
            goToAnimation(1);
            finish();
        }, haveLoadAd ? 3000 : 0);
    }
}
