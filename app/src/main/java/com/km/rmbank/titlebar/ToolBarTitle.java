package com.km.rmbank.titlebar;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kamangkeji on 17/1/20.
 */

public class ToolBarTitle implements BaseActivity.TitleBarInterface {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fl_center_view)
    FrameLayout flCenterView;

    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;

    private boolean isSetTitle = false;

    public ToolBarTitle(boolean isSetTitle) {
        this.isSetTitle = isSetTitle;
    }

    @Override
    public void bindViewByRes(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void setLeftButtton(int iconRes, View.OnClickListener leftClick) {
        if (iconRes > 0) {
            toolbar.setNavigationIcon(iconRes);
            if (leftClick != null) {
                toolbar.setNavigationOnClickListener(leftClick);
            }
        } else {
            toolbar.setNavigationIcon(null);
        }
    }

    @Override
    public void setTitleName(String titleName) {
        toolbar.setTitle(titleName);
    }

    @Override
    public void setCenterView(BaseActivity.CenterViewInterface centerView) {
        View center = centerView.getView();
        centerView.setViewWidget(center);
        flCenterView.addView(center);
    }

    @Override
    public void setRightButton(int iconRes, View.OnClickListener rightClick) {
        tvTitleRight.setVisibility(View.GONE);
        if (iconRes > 0){
            ivTitleRight.setVisibility(View.VISIBLE);
            ivTitleRight.setImageResource(iconRes);
            ivTitleRight.setOnClickListener(rightClick);
        } else {
            ivTitleRight.setVisibility(View.GONE);
        }
    }

    @Override
    public void setRightButton(String rightBtnText, View.OnClickListener rightClick) {
            ivTitleRight.setVisibility(View.GONE);
        if (rightClick != null){
            tvTitleRight.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(rightBtnText)){
                tvTitleRight.setText(rightBtnText);
            }
            tvTitleRight.setOnClickListener(rightClick);
        } else {
            tvTitleRight.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean isSetTitle() {
        return isSetTitle;
    }

}
