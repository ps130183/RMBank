package com.hyphenate.easeui.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by pengsong on 2016-09-12.
 */
public class PermissionUtils {

    public static void permissionApply(Activity activity,String permissionName,int requestCode,PermissionApplyCallback callback){
//申请照相机的使用权限
        if (ContextCompat.checkSelfPermission(activity, permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{permissionName},
                    requestCode);//自定义的code
        } else {
            callback.permissionApplyCallback();
        }
    }

    public interface PermissionApplyCallback{
        void permissionApplyCallback();
    }

}
