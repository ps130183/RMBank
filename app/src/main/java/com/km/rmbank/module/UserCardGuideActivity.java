package com.km.rmbank.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.utils.retrofit.SecretConstant;

import butterknife.BindView;

public class UserCardGuideActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_card_guide;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void onCreate() {
        int guide = getIntent().getIntExtra("guide",0);

        String title = "";
        String webUrl = SecretConstant.API_HOST + SecretConstant.API_HOST_PATH;
        switch (guide){
            case 0:
                title = "人脉团队";
                webUrl += "/group";
                break;
            case 1:
                title = "名片添加";
                webUrl += "/add";
                break;
            case 2:
                title = "推荐资源";
                webUrl += "/resource";
                break;
        }
        mTitle.setText(title);
        mWebView.loadUrl(webUrl);
    }
}
