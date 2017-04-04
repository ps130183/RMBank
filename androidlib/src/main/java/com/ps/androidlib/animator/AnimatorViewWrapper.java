package com.ps.androidlib.animator;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        return target.getHeight();
    }
}
