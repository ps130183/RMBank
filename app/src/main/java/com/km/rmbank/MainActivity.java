package com.km.rmbank;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BasePresenter;
import com.km.rmbank.module.HomeActivity;
import com.km.rmbank.utils.PcaDataUtils;

import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

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
        PcaDataUtils.initData(context);
        toNextActivity(HomeActivity.class);
    }

}
