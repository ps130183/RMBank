package com.km.rmbank.module.club.recent;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentGuestAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.module.chat.EaseChatActivity;
import com.km.rmbank.module.club.ClubInfoActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.UmengShareUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.animator.AnimatorViewWrapper;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.MToast;
import com.ps.androidlib.utils.StatusBarUtil;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ActionRecentInfoActivity extends BaseActivity<ActionRecentInfoPresenter> implements ActionRecentInfoContract.View {

    @BindView(R.id.ll_toolbar_top)
    LinearLayout llToolbarTop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.iv_club_background)
    ImageView ivClubBackground;

    @BindView(R.id.tv_action_title)
    TextView tvActionTitle;
    @BindView(R.id.iv_club_logo)
    ImageView ivClubLogo;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;

    @BindView(R.id.tv_action_start_time)
    TextView tvActionStartTime;
    @BindView(R.id.tv_action_address)
    TextView tvActionAddress;
    @BindView(R.id.iv_club_logo2)
    ImageView ivClubLogo2;
    @BindView(R.id.tv_club_name1)
    TextView getTvClubName1;
    @BindView(R.id.tv_club_introduce)
    TextView tvClubIntroduce;
    @BindView(R.id.btn_keep_club)
    Button btnKeepClub;

    @BindView(R.id.tv_action_flow)
    TextView tvActionFlow;

    @BindView(R.id.rv_invitation_mans)
    RecyclerView rvInvitationMans;


    private String actionId;
    private ActionDto mActionDto;
    private boolean isMyClub;

    private ShareDto shareDto;

    @Override
    protected int getContentView() {
        return R.layout.activity_action_recent_info;
    }

    @Override
    protected String getTitleName() {
        return "近期活动";
    }

    @Override
    public ActionRecentInfoPresenter getmPresenter() {
        return new ActionRecentInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        llToolbarTop.setBackgroundColor(ContextCompat.getColor(this,R.color.color_white));
        mToolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_white));
        mTitle.setTextColor(ContextCompat.getColor(this,R.color.color_block));
        //设置状态栏 字体颜色为深色
        StatusBarUtil.StatusBarLightMode(this);

        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
        actionId = getIntent().getStringExtra("actionId");
        mPresenter.getActionRecentInfo(actionId);
        shareDto = new ShareDto();

        setRightIconClick(R.mipmap.ic_action_recent_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShare();
            }
        });

        setLeftIconClick(R.mipmap.ic_left_back1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initInvitationMans();
    }


    private void initInvitationMans(){
        RVUtils.setLinearLayoutManage(rvInvitationMans, LinearLayoutManager.VERTICAL);
        ActionRecentGuestAdapter adapter = new ActionRecentGuestAdapter(this);
        rvInvitationMans.setAdapter(adapter);
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

    @Override
    public void showActionRecentInfo(ActionDto actionDto) {
//        mTitle.setText(actionDto.getTitle());
        mActionDto = actionDto;

        shareDto.setTitle(actionDto.getTitle());
        shareDto.setContent(actionDto.getFlow());
        shareDto.setSharePicUrl(actionDto.getActivityPictureUrl());
        shareDto.setPageUrl(actionDto.getWebActivityUrl());

        GlideUtils.loadImage(ivClubBackground,actionDto.getBackgroundImg());
        tvActionTitle.setText(actionDto.getTitle());
        GlideUtils.loadCircleImage(ivClubLogo,actionDto.getClubLogo());
        tvClubName.setText(actionDto.getClubName());

        tvActionStartTime.setText("   " + actionDto.getHoldDate());
        tvActionAddress.setText("   " + actionDto.getAddress());
        GlideUtils.loadCircleImage(ivClubLogo2,actionDto.getClubLogo());
        getTvClubName1.setText(actionDto.getClubName());
        tvClubIntroduce.setText(actionDto.getClubContent());
        if (actionDto.getKeepStatus() == 0){//未关注
            btnKeepClub.setText("+ 关注");
        } else {
            btnKeepClub.setText("已关注");
        }

        tvActionFlow.setText(actionDto.getFlow());

        if (Constant.user.getMobilePhone().equals(mActionDto.getMobilePhone())){
            isMyClub = true;

        }

        ActionRecentGuestAdapter adapter = (ActionRecentGuestAdapter) rvInvitationMans.getAdapter();
        adapter.addData(actionDto.getGuestList());

    }

    @Override
    public void applyActionSuccess() {
        showToast("报名成功");
    }

    @Override
    public void followClubSuccess(boolean isFollow) {
        mActionDto.setKeepStatus(isFollow ? 1 : 0);
        if (mActionDto.getKeepStatus() == 0){//未关注
            btnKeepClub.setText("+ 关注");
        } else {
            btnKeepClub.setText("已关注");
        }
    }

    /**
     * 点击 都有谁去 按钮
     * @param view
     */
    @OnClick({R.id.ll_join_members,R.id.tv_join_members})
    public void clickMembers(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("actionDto",mActionDto);
        bundle.putBoolean("isMyClub",isMyClub);
        toNextActivity(ActionJoinMemberActivity.class,bundle);
    }

    /**
     * 报名
     * @param view
     */
    @OnClick({R.id.rl_action_apply,R.id.tv_action_apply})
    public void clickApply(View view){
        if (isMyClub){
            showToast("不能报名自己的活动");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("actionId",mActionDto.getId());
        toNextActivity(ActionApplyActivity.class,bundle);
    }

    /**
     * 在线咨询
     * @param view
     */
    @OnClick({R.id.ll_relation,R.id.tv_relation})
    public void clickRelation(View view){
        if (isMyClub){
            showToast("这是您自己的活动");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("to_user_id",mActionDto.getMobilePhone());
        bundle.putString("user_nick_name",mActionDto.getClubName());
        bundle.putBoolean("isService",true);
        toNextActivity(EaseChatActivity.class,bundle);
    }

    /**
     * 关注俱乐部
     * @param view
     */
    @OnClick(R.id.btn_keep_club)
    public void keepClub(View view){
        if (isMyClub){
            showToast("不能关注自己的俱乐部");
            return;
        }
        String content;
        if (mActionDto.getKeepStatus() == 0){//未关注
            content = "是否关注该俱乐部?";
        } else {
            content = "是否取消对该俱乐部的关注？";
        }
        DialogUtils.showDefaultAlertDialog(content, new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                mPresenter.followClub(mActionDto.getClubId(),mActionDto.getKeepStatus() == 0);
            }
        });
    }

    /**
     * 查看俱乐部详情
     * @param view
     */
    @OnClick({R.id.iv_club_logo,R.id.iv_club_logo2,R.id.tv_club_name,R.id.tv_club_name1})
    public void toClubInfos(View view){
        Bundle bundle = new Bundle();
        bundle.putString("clubId",mActionDto.getClubId());
        toNextActivity(ClubInfoActivity.class,bundle);
    }
}
