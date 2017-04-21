package com.km.rmbank.module.actionarea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.InformationDto;

import butterknife.BindView;

public class InformationDetailActivity extends BaseActivity<InformationDetailPresenter> implements InformationDetailContract.View {

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.webView)
    WebView webView;
    private InformationDto informationDto;

    private String informationId;
    @Override
    protected int getContentView() {
        return R.layout.activity_information_detail;
    }

    @Override
    protected String getTitleName() {
        return "";
    }

    @Override
    public InformationDetailPresenter getmPresenter() {
        return new InformationDetailPresenter(this);
    }

    @Override
    protected void onCreate() {
        informationDto = getIntent().getParcelableExtra("informationDto");
        informationId = getIntent().getStringExtra("informationId");
        String title = getIntent().getStringExtra("informationTitle");
        tvTitle.setText(informationDto == null ? title : informationDto.getTitle());
    }

    @Override
    public void initWebView() {

        if (!TextUtils.isEmpty(informationId)){
            mPresenter.getInformationDetail(informationId);
        } else {
            mPresenter.getInformationDetail(informationDto.getId());
        }

    }

    @Override
    public void showInfomationDetail(String html) {
        webView.loadDataWithBaseURL(null,html,"text/html","UTF-8",null);
    }
}
