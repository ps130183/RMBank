
package com.km.rmbank.module.personal.active;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.utils.retrofit.SecretConstant;

import butterknife.BindView;

public class ActiveValueIntroduceActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected int getContentView() {
        return R.layout.activity_active_value_introduce;
    }

    @Override
    protected String getTitleName() {
        return "活跃值规则";
    }

    @Override
    protected void onCreate() {
        mWebView.loadUrl(SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/active/value/introduce");
    }
}
