package com.km.rmbank.utils.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.km.rmbank.R;

/**
 * Created by kamangkeji on 17/5/18.
 */

public class CusBehavior1 extends CoordinatorLayout.Behavior {

    public CusBehavior1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.rv_gt2;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        child.setY(dependency.getY() + dependency.getHeight());
        return true;
    }
}
