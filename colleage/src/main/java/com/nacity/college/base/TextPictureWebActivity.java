package com.nacity.college.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nacity.college.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzz on 2017/7/6.
 **/

public class TextPictureWebActivity extends BaseActivity {
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
        String title = getIntent().getStringExtra("Title");
        if (title != null && title.indexOf("#") == 0 && title.lastIndexOf("#") == title.length() - 2)
            title = title.substring(1, title.length() - 2);

        setTitle(title);
        String content = getIntent().getStringExtra("Content");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String data = String.format("<html><head><style>img{max-width:100%%;height:auto " +
                        "!important;width:auto !important;};</style>" +
                        "</head><body style='margin:0; padding:0;'>" +

                        "<div style='padding:0px'>%s</div></body></html>",
                content);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.loadDataWithBaseURL("", data, "text/html", "utf-8", "");

    }
}
