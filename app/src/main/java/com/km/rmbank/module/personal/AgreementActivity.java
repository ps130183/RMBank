package com.km.rmbank.module.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.utils.retrofit.SecretConstant;

import butterknife.BindView;

public class AgreementActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView tvTitle;
    private String titleName = "";

    @BindView(R.id.webView)
    WebView webView;
    @Override
    protected int getContentView() {
        return R.layout.activity_agreement;
    }

    @Override
    protected String getTitleName() {
        return "";
    }

    @Override
    protected void onCreate() {
        titleName = getIntent().getStringExtra("titleName");
        tvTitle.setText(titleName);

        String agreementUrl = getIntent().getStringExtra("agreementUrl");
        webView.loadUrl(SecretConstant.API_HOST + SecretConstant.API_HOST_PATH +agreementUrl);
    }
}
