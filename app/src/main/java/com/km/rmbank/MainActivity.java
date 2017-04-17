package com.km.rmbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.HomeActivity;
import com.km.rmbank.module.guide.GuideActivity;
import com.km.rmbank.module.guide.LaunchActivity;
import com.km.rmbank.utils.selectcity.CityPickData;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "首页";
    }

    @Override
    protected void onCreate() {
        initPcaData(this);
    }

    @OnClick(R.id.to_home)
    public void toHome(View view){
        toNextActivity(HomeActivity.class);
    }


    private void initPcaData(final Context context){
        toNextActivity(LaunchActivity.class);
        finish();
    }

}
