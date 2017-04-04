package com.ps.androidlib.animator;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;

/**
 * Created by kamangkeji on 17/4/2.
 */

public class RecyclerViewAnimator {

    /**
     * 给recyclerview添加 隐藏或显示的动画
     * return boolean true:显示  false:隐藏
     */
    private int height;
    private AnimatorViewWrapper viewWrapper;
    public boolean recyclerViewSetVisiable(final RecyclerView recyclerView){
        recyclerView.setVisibility(View.VISIBLE);
        ObjectAnimator objectAnimator;
        if (viewWrapper == null){
            viewWrapper = new AnimatorViewWrapper(recyclerView);
        }
        Logger.d("rvMember height = " + viewWrapper.getHeight() + "   " + height);
        if (viewWrapper.getHeight() != 0){//目前是显示状态   去隐藏
            height = viewWrapper.getHeight();
            objectAnimator = ObjectAnimator.ofInt(viewWrapper,"height",0);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            return false;
        } else {//目前是隐藏状态  去显示
            objectAnimator = ObjectAnimator.ofInt(viewWrapper,"height",height);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            return true;
        }
    }

}
