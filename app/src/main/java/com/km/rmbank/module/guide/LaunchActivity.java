package com.km.rmbank.module.guide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.module.HomeActivity;
import com.km.rmbank.module.HomeNewActivity;
import com.km.rmbank.utils.selectcity.CityPickData;
import com.ps.androidlib.utils.SPUtils;
import com.ps.androidlib.utils.StatusBarUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        StatusBarUtil.setFullScreen(this);
        init();
    }

    private void init(){
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        CityPickData.initData(LaunchActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        boolean isFirst = SPUtils.getInstance().getBoolean("isFirst",true);
                        if (isFirst){
                            startActivity(new Intent(LaunchActivity.this,GuideActivity.class));
                        } else {
                            startActivity(new Intent(LaunchActivity.this,HomeNewActivity.class));
//                            startActivity(new Intent(LaunchActivity.this,HomeActivity.class));
                        }
                        finish();
                    }
                });
    }
}
