package com.km.rmbank.module.personal.account.withdraw;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baoyachi.stepview.VerticalStepView;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WithDrawDetailsActivity extends BaseActivity {

    @BindView(R.id.vertical_stepview)
    VerticalStepView mVerticalStepView;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_draw_details;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "提现详情";
    }

    @Override
    protected void onCreate() {
        initVerticalStepView();
    }

    private void initVerticalStepView(){
        List<String> contentList = new ArrayList<>();
        contentList.add("提现申请已经提交，等待平台处理");
        contentList.add("工商银行   (5986)\n1.00元");
        contentList.add("预计24小时内(次日17:15)到账");
        mVerticalStepView.setStepsViewIndicatorComplectingPosition(contentList.size() - 2)//设置完成的步数
                .reverseDraw(false)//default is true
                .setStepViewTexts(contentList)//总步骤
                .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.color_blue))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.color_block))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.color_blue))//设置StepsView text完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.color_block))//设置StepsView text未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon
    }
}
