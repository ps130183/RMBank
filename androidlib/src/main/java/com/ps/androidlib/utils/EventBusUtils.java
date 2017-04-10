package com.ps.androidlib.utils;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by pengsong on 2016-08-03.
 */
public class EventBusUtils {

    /**
     * 注册eventBus
     * @param context
     */
    public static void register(Context context){
        EventBus.getDefault().register(context);
    }
    /**
     * 注销eventBus
     * @param context
     */
    public static void unregister(Context context){
        EventBus.getDefault().unregister(context);
    }

    /**
     * 发送请求
     * @param message
     */
    public static void post(Object message){
        EventBus.getDefault().post(message);
    }
}
