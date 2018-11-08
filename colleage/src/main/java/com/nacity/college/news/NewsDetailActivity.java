package com.nacity.college.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  Created by usb on 2017/9/18.
 */

public class NewsDetailActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView        webView;
    @BindView(R.id.layout_adv)
    RelativeLayout _layoutAdv;
    @BindView(R.id.phone)
    RelativeLayout phoneLayout;
    private String id;
    private String type;
    private String phone;
    private String title;
    private String noticeType;
    private String typeOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        id= getIntent().getStringExtra("id");
        type= getIntent().getStringExtra("type");
        phone= getIntent().getStringExtra("phone");
        noticeType= getIntent().getStringExtra("noticeType");
        title= getIntent().getStringExtra("title");
        typeOther= getIntent().getStringExtra("typeOther");
        initData();

    }

    private void initTitle() {
        switch (type) {
            case "1":
                if("1".equals(noticeType)){
                    setTitle("物业公告");
                }  if("2".equals(noticeType)){
                    setTitle("管委会公告");
                }  if("3".equals(noticeType)){
                    setTitle("园区风采");
                }
                phoneLayout.setVisibility(View.GONE);
                break;
            case "2":
                setTitle("园区创新");
                phoneLayout.setVisibility(View.GONE);

                break;
             case "3":
                setTitle("法律服务");
                phoneLayout.setVisibility(View.VISIBLE);

                break;
            case "4":
                setTitle("创业服务");
                phoneLayout.setVisibility(View.VISIBLE);

                break;
            case "5":
                setTitle("政策服务");
                phoneLayout.setVisibility(View.VISIBLE);

                break;
            case "6":
                setTitle("投融资服务");
                phoneLayout.setVisibility(View.VISIBLE);
                break;
            case "7":
                setTitle(title);
                if(!"1".equals(typeOther)&&!"2".equals(typeOther)){
                phoneLayout.setVisibility(View.VISIBLE);}
                else {
                phoneLayout.setVisibility(View.GONE);
       }
                break;

        }
    }
    private void initData() {
        initTitle();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        loadingShow();
        String  url = MainApp.DefaultValue.NEWS_DETAIL_URI+ id;
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
    @OnClick({R.id.phone})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.phone:
//                Intent intent=new Intent(appContext,PostActivity.class);
//                intent.putStringArrayListExtra("TypeList",  postTypeList);
//                startActivity(intent);
                callShowDialog();
                break;
        }
    }
    private void callShowDialog() {
        final CommonDialog dialog = new CommonDialog(this,
                R.layout.dialog_call, R.style.myDialogTheme);
//        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView btnAdd = (TextView) dialog.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" +phone));
            startActivity(intent);
            dialog.dismiss();
        });
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
