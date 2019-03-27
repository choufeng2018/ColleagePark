package com.nacity.college.myself;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nacity.college.R;
import com.mob.MobSDK;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by xzz on 2017/7/6.
 **/

public class ShareActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_apk);
        ButterKnife.bind(this);
        setTitle(Constant.SHARE);
        MobSDK.init(this);


    }

    @OnClick({R.id.wChat, R.id.QQ, R.id.message, R.id.moment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wChat:
                wChatShare();
                break;
            case R.id.QQ:
                qqShare();
                break;
            case R.id.message:
                messageShare();
                break;
            case R.id.moment:
                momentShare();
                break;
        }
    }

    public void wChatShare(){
        Wechat.ShareParams shareParams = new Wechat.ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setText(Constant.APP_SHARE_CONTENT);
        Platform weChatApp = ShareSDK.getPlatform(Wechat.NAME);
        weChatApp.share(shareParams);
    }
    public void qqShare(){
//        QQ.ShareParams qq = new QQ.ShareParams();
//        qq.setTitle("欢迎加入紫金众创社区");
//        //点击标题跳转的分享网址
//        qq.setTitleUrl("http://joyhomenet.com/cdownload.html");
//        qq.setText(Constant.APP_SHARE_CONTENT);
//        qq.setImageData(BitmapFactory.decodeResource(getResources(), R.drawable.share_ic));
//        Platform share_qq = ShareSDK.getPlatform( QQ.NAME);
//
//
//        share_qq.share(qq);

        QQ.ShareParams shareParams = new QQ.ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setTitleUrl("http://joyhomenet.com/cdownload.html");
        shareParams.setTitle("欢迎加入紫金众创社区");
        shareParams.setText(Constant.APP_SHARE_CONTENT);
        shareParams.setImageUrl("http://7xk6y7.com2.z0.glb.qiniucdn.com/colleage_share_ic.png");

        Platform mQQApp = ShareSDK.getPlatform(QQ.NAME);
        mQQApp.share(shareParams);


    }
    public void messageShare(){
        ShortMessage.ShareParams shareParams = new ShortMessage.ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        String mShareContent = Constant.APP_SHARE_CONTENT;
        shareParams.setText(mShareContent);
        Platform mShortMsg = ShareSDK.getPlatform(ShortMessage.NAME);
        mShortMsg.share(shareParams);
    }
    public void momentShare(){
        WechatMoments.ShareParams shareParams = new WechatMoments.ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setText(Constant.APP_SHARE_CONTENT);
        shareParams.setTitle("JoyPark");
        Platform moment = ShareSDK.getPlatform(WechatMoments.NAME);
        moment.share(shareParams);
    }
}
