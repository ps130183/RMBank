package com.km.rmbank.module.actionarea.apply;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.utils.retrofit.SecretConstant;
import com.ps.androidlib.utils.StatusBarUtil;

import butterknife.BindView;

public class ActionDetailActivity extends BaseActivity {

    private ActionDto actionDto;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_action_detail;
    }

    @Override
    protected String getTitleName() {
        return "活动详情";
    }

    @Override
    protected void onCreate() {
//        StatusBarUtil.setFullScreen(this);
        actionDto = getIntent().getParcelableExtra("actionDto");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();
    }

    private void init() {
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);//开启JavaScript支持
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }
        });

        webView.loadUrl(SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/app/html/activityDetail?id=" + actionDto.getId());
    }
}

