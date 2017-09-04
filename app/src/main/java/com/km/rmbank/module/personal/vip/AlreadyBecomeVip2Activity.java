package com.km.rmbank.module.personal.vip;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.club.ClubInfoActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AlreadyBecomeVip2Activity extends BaseActivity {
    @BindView(R.id.ll_toolbar_top)
    LinearLayout llToolbarTop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.view_line_bottom)
    View viewLineBottom;

    @Override
    protected int getContentView() {
        return R.layout.activity_already_become_vip2;
    }

    @Override
    protected String getTitleName() {
        return "已成为合伙人";
    }

    @Override
    protected void onCreate() {
        viewLineBottom.setVisibility(View.GONE);
        llToolbarTop.setBackgroundColor(ContextCompat.getColor(this,R.color.color_blue5));
        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_blue5));
    }

    @OnClick(R.id.btn_release_action)
    public void releaseAction(View view){
        toNextActivity(ClubInfoActivity.class);
    }
}
