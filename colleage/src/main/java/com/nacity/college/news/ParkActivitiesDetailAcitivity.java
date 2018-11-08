package com.nacity.college.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.news.model.ActivityInfoModel;
import com.nacity.college.news.presenter.ActivityInfoPresenter;
import com.nacity.college.news.view.ActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  Created by usb on 2017/12/11.
 */

public class ParkActivitiesDetailAcitivity extends BaseActivity implements ActivityView {
    @BindView(R.id.webView)
    WebView        webView;
    @BindView(R.id.layout_adv)
    RelativeLayout _layoutAdv;
    @BindView(R.id.enroll)
    TextView enroll;
    private boolean showButton;
    private boolean isSignUp;
    private String id;
    private ActivityInfoModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        ButterKnife.bind(this);
        id= getIntent().getStringExtra("id");
        Log.i("222", "idid: "+id);
        model = new ActivityInfoPresenter(this);
        loadingShow();
        model.getActivityInfo(id);
        setTitle(Constant.ACTIVITY_PARK);

    }

    private void initData(NewsTo newsTo) {
        if(showButton) {
            enroll.setVisibility(View.VISIBLE);
            if (newsTo.getStatus()==3) {
                enroll.setBackgroundColor(getResources().getColor(R.color.gray_b4));
                enroll.setClickable(false);
                enroll.setText("活动结束");
            }else{
            if ("0".equals(newsTo.getIsFull())) {
                if (isSignUp) {
                    //            binding.state.setBackgroundColor(mContext.getResources().getColor(R.color.action_text_color));
                    enroll.setBackgroundColor(getResources().getColor(R.color.gray_b4));
                    enroll.setText("已报名");
                    enroll.setClickable(false);
                } else {
                    enroll.setBackgroundColor(getResources().getColor(R.color.app_color));
                    enroll.setText("我要报名");
                }
            } else {
                if (isSignUp) {
                    //            binding.state.setBackgroundColor(mContext.getResources().getColor(R.color.action_text_color));
                    enroll.setBackgroundColor(getResources().getColor(R.color.gray_b4));
                    enroll.setText("已报名");
                    enroll.setClickable(false);
                } else {
                    enroll.setBackgroundColor(getResources().getColor(R.color.gray_b4));
                    enroll.setClickable(false);
                    enroll.setText("报名已满");
                }
            }
        }
        }
        else
            enroll.setVisibility(View.GONE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String  url = MainApp.DefaultValue.ACTIVITY_DETAIL_URI+ id;
        runOnUiThread(() -> {
//            if ( !TextUtils.isEmpty(mNews.getContent())) {
//                webView.setWebViewClient(new WebViewClient() {
//
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        webView.loadUrl(url);
//                        return true;
//                    }
//                });
//                webView.loadUrl(mNews.getContent());
//            } else {
//                String  html= String.format("<html><head><style>img{max-width:100%%;height:auto !important;width:auto !important;};</style></head><body style='margin:0; padding:0;'>%s</body></html>",
//                        mNews.getContent());

//                webView.loadDataWithBaseURL(null, mNews, "text/html", "utf-8", null);
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    loadingDismiss();

                }
            });
//        }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _layoutAdv.removeView(webView);
        webView.removeAllViews();
        webView.destroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
    @OnClick({R.id.enroll})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.enroll:
                Intent intent=new Intent(appContext,EnrollActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void getActivityInfo(NewsTo newsTo) {
        if((newsTo.getStatus()==2||newsTo.getStatus()==3)&&newsTo.getShowButton()==1){
            showButton=true;
        }else{
            showButton=false;
        }
        if (newsTo.getIsSignUp()==1)
            isSignUp=true;
        else
            isSignUp=false;
        initData(newsTo);
        Log.i("22223", "getShowButton: "+newsTo.getShowButton());
        Log.i("22223", "getShowButton: "+newsTo.getIsSignUp());
        Log.i("22223", "getStatus: "+newsTo.getStatus());

    }
}
