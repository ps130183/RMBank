package com.ps.androidlib.animator;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ps.androidlib.utils.AppUtils;

/**
 * Created by kamangkeji on 17/4/2.
 */

public class AnimatorViewWrapper {
    View target;

    public AnimatorViewWrapper(View target) {
        this.target = target;
    }

    public void setHeight(int height){
        target.getLayoutParams().height = height;
        target.requestLayout();
    }

    public int getHeight(){
        if (target instanceof ViewGroup){
            int width = View.MeasureSpec.makeMeasureSpec(AppUtils.getCurWindowWidth(target.getContext()), View.MeasureSpec.EXACTLY);
            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            target.measure(width,height);
            return target.getMeasuredHeight();
        } else {
            return target.getLayoutParams().height;
        }
    }
}
