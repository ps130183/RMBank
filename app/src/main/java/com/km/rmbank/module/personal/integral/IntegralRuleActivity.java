package com.km.rmbank.module.personal.integral;

import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.utils.retrofit.SecretConstant;

import butterknife.BindView;

public class IntegralRuleActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_rule;
    }

    @Override
    protected String getTitleName() {
        return "积分规则";
    }

    @Override
    protected void onCreate() {
        webView.loadUrl(SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/member/total/view");
    }
}
