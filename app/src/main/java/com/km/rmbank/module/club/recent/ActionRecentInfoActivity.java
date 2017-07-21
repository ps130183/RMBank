package com.km.rmbank.module.club.recent;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.utils.UmengShareUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.animator.AnimatorViewWrapper;
import com.ps.androidlib.utils.MToast;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ActionRecentInfoActivity extends BaseActivity<ActionRecentInfoPresenter> implements ActionRecentInfoContract.View {

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.ll_action)
    LinearLayout llAction;

    @BindView(R.id.tv_action_content)
    TextView tvActionContent;
    @BindView(R.id.tv_action_member)
    TextView tvActionMember;

    private List<Fragment> mFragments;

    @BindView(R.id.ll_apply)
    LinearLayout llApply;
    private int llApplyHeight = 0;
    private AnimatorViewWrapper llApplyViewWrapper;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.btn_apply)
    Button btnApply;

    @BindView(R.id.fl_action_recent)
    FrameLayout flActionRecent;

    private String actionId;
    private boolean isMyClub;

    private ShareDto shareDto;

    @Override
    protected int getContentView() {
        return R.layout.activity_action_recent_info;
    }

    @Override
    protected String getTitleName() {
        return "";
    }

    @Override
    public ActionRecentInfoPresenter getmPresenter() {
        return new ActionRecentInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
        actionId = getIntent().getStringExtra("actionId");
        mPresenter.getActionRecentInfo(actionId);
        shareDto = new ShareDto();

        setRightBtnClick("分享", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("分享");
                openShare();
            }
        });

        if (isMyClub){
            btnApply.setVisibility(View.GONE);
        } else {
            llAction.setVisibility(View.GONE);
        }

        llApplyViewWrapper = new AnimatorViewWrapper(llApply);
        llApplyHeight = llApplyViewWrapper.getHeight();
        llApply.setVisibility(View.GONE);

    }

    private void openShare(){
        UmengShareUtils.openShare(this, shareDto, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                showToast("分享成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Logger.d(throwable.getMessage());
//                showToast("分享失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                showToast("取消分享");
            }
        });
    }

    @OnClick(R.id.tv_action_content)
    public void clickActionContent(View view){
        selectActionInfo(0);
    }

    @OnClick(R.id.tv_action_member)
    public void clickActionMember(View view){
        selectActionInfo(1);
    }

    private void selectActionInfo(int position){
        switch (position){
            case 0:
                tvActionContent.setTextColor(ContextCompat.getColor(this,R.color.color_pink));
                tvActionMember.setTextColor(ContextCompat.getColor(this,R.color.color_block));
                break;
            case 1:
                tvActionContent.setTextColor(ContextCompat.getColor(this,R.color.color_block));
                tvActionMember.setTextColor(ContextCompat.getColor(this,R.color.color_pink));
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_action_recent_content,mFragments.get(position))
                .commit();
    }

    @OnClick(R.id.btn_apply)
    public void clickApplyJoinAction(View view){
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        if (llApply.getVisibility() == View.GONE) {//打开
            openApply();
        } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            showToast("请将您的报名信息补充完整");
        } else {
//            showToast("报名，姓名：" + name + "  电话：" + phone);
            mPresenter.applyAction(actionId,name,phone);
        }
    }

    @OnClick(R.id.fl_action_recent)
    public void onClickActionRecent(View view){
        if (llApply.getVisibility() == View.VISIBLE){
            closeApply();
        }
    }

    /**
     * 打开报名输入框
     */
    private void openApply(){
        ObjectAnimator llAppluAnimator = ObjectAnimator.ofInt(llApplyViewWrapper, "height", 0, llApplyHeight);
        llAppluAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                llApply.setVisibility(View.VISIBLE);
                flActionRecent.setVisibility(View.VISIBLE);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(llAppluAnimator);
        animatorSet.setDuration(300).start();
    }

    /**
     * 关闭报名输入框
     */
    private void closeApply(){
        ObjectAnimator llAppluAnimator = ObjectAnimator.ofInt(llApplyViewWrapper, "height", llApplyHeight, 0);
        llAppluAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                llApply.setVisibility(View.GONE);
                flActionRecent.setVisibility(View.GONE);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(llAppluAnimator);
        animatorSet.setDuration(300).start();
    }

    @Override
    public void showActionRecentInfo(ActionDto actionDto) {
        mTitle.setText(actionDto.getTitle());

        shareDto.setTitle(actionDto.getTitle());
        shareDto.setContent(actionDto.getFlow());
        shareDto.setSharePicUrl(actionDto.getActivityPictureUrl());
        shareDto.setPageUrl(actionDto.getWebActivityUrl());

        mFragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putParcelable("actionDto",actionDto);
        mFragments.add(ActionRecentContentFragment.newInstance(bundle));
        if (isMyClub){
            mFragments.add(ActionJoinMemberFragment.newInstance(bundle));
        }
        selectActionInfo(0);
    }

    @Override
    public void applyActionSuccess() {
        showToast("报名成功");
    }
}
