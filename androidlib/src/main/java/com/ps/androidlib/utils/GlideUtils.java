package com.ps.androidlib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.R;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class GlideUtils {
    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */


    public static void loadImage(ImageView imageView,int imageRes){
        Glide.with(imageView.getContext())
                .load(imageRes)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView,String imagePath){
        if (imageView == null){
            Logger.e("imageview is null");
            return;
        }

        Glide.with(imageView.getContext())
                .load(imagePath)
                .placeholder(R.drawable.glide_placeholder)
                .error(R.drawable.glide_placeholder)
                .crossFade(1000)
                .into(imageView);
    }


    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}
