package com.nacity.college.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nacity.college.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/7/6.
 **/

public class AdWebActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setView() {
        String title=getIntent().getStringExtra("Title");
        if (title!=null&&title.indexOf("#")==0&&title.lastIndexOf("#")==title.length()-2)
            title=title.substring(1,title.length()-2);

        setTitle(title);
        String  url = getIntent().getStringExtra("Url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);

            }


        });

        webView.loadUrl(url);

    }
}
