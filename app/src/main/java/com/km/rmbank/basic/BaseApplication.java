package com.km.rmbank.basic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.Utils;
import com.threshold.rxbus2.RxBus;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by kamangkeji on 17/2/11.
 */

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initLogUtils();
        registCallback();
        initUtils();
        UmengShareUtils.initUmengShare(this);
        initEaseUI();
//        RxBus.config(AndroidSchedulers.mainThread());
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    /**
     * 初始化日志打印
     */
    private void initLogUtils() {
        Logger.init("RMBANK")               // default tag : PRETTYLOGGER or use just init()
                .setMethodCount(2)            // default 2
//                .hideThreadInfo()           // default it is shown
                .setLogLevel(LogLevel.FULL);  // default : LogLevel.FULL
        Logger.d("initLogUtils");
    }

    /**
     * 弹出提示框 初始化
     */
    private void registCallback() {
        StyledDialog.init(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActyManager.getInstance().setCurrentActivity(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initUtils() {
        Utils.init(this);
//        SPUtils spUtils = new SPUtils("RMbank");
    }

    private void initEaseUI() {
        if (EaseUI.getInstance().init(this,null)){
            EMClient.getInstance().setDebugMode(true);
            EaseUserChatUtils.init();
        }
    }

}
