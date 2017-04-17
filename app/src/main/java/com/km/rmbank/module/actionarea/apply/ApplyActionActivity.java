package com.km.rmbank.module.actionarea.apply;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;

import butterknife.BindView;

public class ApplyActionActivity extends BaseActivity {

    String loadUrl = "http://192.168.31.220:8080/Aiyg/app/html/activityList.html";
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected int getContentView() {
        return R.layout.activity_apply_action;
    }

    @Override
    protected String getTitleName() {
        return "活动列表";
    }

    @Override
    protected void onCreate() {
        init();
    }

    private void init(){
        mWebView.loadUrl(loadUrl);
    }
}
