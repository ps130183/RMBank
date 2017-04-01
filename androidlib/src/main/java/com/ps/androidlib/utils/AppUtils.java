package com.ps.androidlib.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.ps.androidlib.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by pengsong on 2016-10-20.
 */

public class AppUtils {

    public static DisplayMetrics metrics = null;
    public static Handler mHandler;

    static {
        metrics = new DisplayMetrics();
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    public static String getCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;

    }

    /**
     * 跳转页面
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public static void skipToPage(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 获取当前屏幕宽度
     *
     * @param mContext
     * @return
     */
    public static int getCurWindowWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取当前屏幕高度
     *
     * @param mContext
     * @return
     */
    public static int getCurWindowHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**-----------------------------------我是分割线----------------------------------------------*/
    /**
     * 去主线程运行
     *
     * @param mills
     * @param uiThread
     */
    public static void executeOnUIThread(int mills, final UIThread uiThread) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                uiThread.onUIThread();
            }
        }, mills);
    }

    public interface UIThread {
        void onUIThread();
    }


    final public static int REQUEST_CODE_ASK_CALL_PHONE = 1111;

    /**
     * 动态申请权限
     * 要在申请权限的页面中 实现{link @onRequestPermissionsResult}
     *
     * @param mContext
     * @param permission
     * @param applyPermissionListener
     */
    public static void applyPermission(Context mContext, String[] permission, onApplyPermissionListener applyPermissionListener) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext,
                        permission,
                        REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                applyPermissionListener.applyPermissionSuccess();
            }
        } else {
            applyPermissionListener.applyPermissionSuccess();
        }
    }

    public interface onApplyPermissionListener {
        void applyPermissionSuccess();
    }


    /**
     * 获取版本号
     * @param mContext
     * @return 当前应用的版本号
     */
    public static String getAppVersion(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
