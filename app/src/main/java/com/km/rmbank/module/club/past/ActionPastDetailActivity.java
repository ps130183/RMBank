package com.km.rmbank.module.club.past;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionPastDetailsAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.module.club.ClubInfoActivity;
import com.km.rmbank.utils.UmengShareUtils;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ActionPastDetailActivity extends BaseActivity<ActionPastDetailPresenter> implements ActionPastDetailContract.View {

    @BindView(R.id.title)
    TextView tvTitle;

    @BindView(R.id.tv_action_name)
    TextView tvActionName;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.iv_club_logo)
    ImageView ivClubLogo;
    @BindView(R.id.tv_release_time)
    TextView tvReleaseTime;
    @BindView(R.id.rv_action_past_details)
    RecyclerView rvActionPastDetails;

    @BindView(R.id.jzv_player)
    JZVideoPlayerStandard jzvPlayer;


    private String actionPastId;

    private String clubId;
    private boolean isMyClub;

    private ShareDto mShareDto;

    @Override
    protected int getContentView() {
        return R.layout.activity_action_past_detail;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    public ActionPastDetailPresenter getmPresenter() {
        return new ActionPastDetailPresenter(this);
    }

    @Override
    protected void onCreate() {
        actionPastId = getIntent().getStringExtra("actionPastId");
        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
        initActionPastDetails();
        mPresenter.getActionPastDetails(actionPastId);

        if (!isMyClub){
            setRightIconClick(R.mipmap.ic_home_dynamic_share, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mShareDto == null){
                        showToast("获取分享内容失败");
                        return;
                    }
                    UmengShareUtils.openShare(ActionPastDetailActivity.this, mShareDto, new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            showToast("分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            showToast("分享失败");
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {

                        }
                    });
                }
            });
        }
    }

    private void initActionPastDetails(){
        RVUtils.setLinearLayoutManage(rvActionPastDetails, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionPastDetails,RVUtils.DIVIDER_COLOR_WHITE);
        ActionPastDetailsAdapter adapter = new ActionPastDetailsAdapter(this);
        rvActionPastDetails.setAdapter(adapter);
    }

    @Override
    public void showActionPastDetails(final ActionPastDto actionPastDto) {
        clubId = actionPastDto.getClubId();
        tvTitle.setText(actionPastDto.getTitle());
        tvActionName.setText(actionPastDto.getTitle());
        tvClubName.setText(TextUtils.isEmpty(actionPastDto.getClubName()) ? "玩转地球商旅学苑" : actionPastDto.getClubName());
        GlideUtils.loadCircleImage(ivClubLogo,actionPastDto.getClubLogo());
        tvReleaseTime.setText("发布时间：" + DateUtils.getInstance().getDate(actionPastDto.getCreateDate()));


        ActionPastDetailsAdapter adapter = (ActionPastDetailsAdapter) rvActionPastDetails.getAdapter();
        adapter.addData(actionPastDto.getDetailList());

        if (isMyClub && actionPastDto.getStatus() != 1){
            setRightBtnClick("编辑", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("actionPastDto",actionPastDto);
                    toNextActivity(ReleaseActionPastActivity.class,bundle);
                }
            });
        }
        //视频
        if (!TextUtils.isEmpty(actionPastDto.getVideoUrl())){
            jzvPlayer.setVisibility(View.VISIBLE);
            jzvPlayer.setUp(actionPastDto.getVideoUrl()
                    , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, actionPastDto.getVideoName());
            GlideUtils.loadImage(jzvPlayer.thumbImageView,actionPastDto.getAvatarUrl());
        }

        mShareDto = new ShareDto();
        mShareDto.setTitle(TextUtils.isEmpty(actionPastDto.getClubName()) ? "玩转地球商旅学苑" : actionPastDto.getClubName());
        mShareDto.setContent(actionPastDto.getTitle());
        mShareDto.setPageUrl(actionPastDto.getWebDynamicUrl());
        mShareDto.setSharePicUrl(actionPastDto.getAvatarUrl());
    }

    @OnClick({R.id.iv_club_logo,R.id.tv_club_name})
    public void onClickClubName(View view){
        if ("manager".equals(clubId)){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("clubId",clubId);
        toNextActivity(ClubInfoActivity.class,bundle);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
