package com.km.rmbank.module.club;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.ClubInfoCell;
import com.km.rmbank.cell.ClubInfoDetailsCell;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.club.past.ReleaseActionPastActivity;
import com.km.rmbank.module.club.recent.ReleaseActionRecentActivity;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.km.rv_libs.TemplateAdapter;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ClubInfoActivity extends BaseActivity<ClubInfoPresenter> implements ClubInfoContract.View {

    @BindView(R.id.rv_club_info)
    RecyclerView rvClubInfo;
    private LinearLayoutManager llm;

    private ClubInfoCell mClubInfoCell;
    private ClubInfoDetailsCell mClubInfoDetailsCell;


    @BindView(R.id.ll_select_club_introduce)
    LinearLayout llSelectClubIntroduce;

    @BindView(R.id.tv_club_introduce)
    TextView tvClubIntroduce;
    @BindView(R.id.tv_action_recent)
    TextView tvActionRecent;
    @BindView(R.id.tv_action_past)
    TextView tvActionPast;

    private ClubDto mClubDto;
    private boolean isMyClub;

    private boolean isFollow = false;

    private String mClubId;

    @Override
    protected int getContentView() {
        return R.layout.activity_club_info;
    }

    @Override
    protected String getTitleName() {
        return "我的俱乐部";
    }

    @Override
    public ClubInfoPresenter getmPresenter() {
        return new ClubInfoPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.getMyClubInfo();
    }

    @Override
    protected void onCreate() {

        mClubId = getIntent().getStringExtra("clubId");
        isMyClub = TextUtils.isEmpty(mClubId);
        //getIntent().getBooleanExtra("isMyClub", false);
        if (isMyClub) {
            setRightBtnClick("编辑", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClubDto == null) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("clubDto", mClubDto);
                    toNextActivity(EditMyClubActivity.class, bundle);
                }
            });
        }

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyClubInfo(mClubId);
            }
        });

        initRecyclerView();
        mPresenter.getMyClubInfo(mClubId);
    }

    private void initRecyclerView() {
        RVUtils.setLinearLayoutManage(rvClubInfo, LinearLayoutManager.VERTICAL);
        TemplateAdapter adapter = new TemplateAdapter();
        rvClubInfo.setAdapter(adapter);
        llm = (LinearLayoutManager) rvClubInfo.getLayoutManager();

        mClubInfoCell = new ClubInfoCell(null);
        mClubInfoCell.setOnFollowClubListener(new ClubInfoCell.onFollowClubListener() {

            @Override
            public void followClub(ClubDto clubDto) {
                if(isMyClub){
                    showToast("不能关注自己的俱乐部");
                    return;
                }
                String content;
                if (isFollow) {
                    content = "是否取消对该俱乐部的关注？";
                } else {
                    content = "是否要关注该俱乐部？";
                }
//                showToast("关注这个俱乐部");
                DialogUtils.showDefaultAlertDialog(content, new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        mPresenter.followClub(mClubDto.getId());
                    }
                });
            }
        });
        adapter.add(mClubInfoCell);
        mClubInfoDetailsCell = new ClubInfoDetailsCell(this, null, getSupportFragmentManager());
        mClubInfoDetailsCell.setSelectCurFragmentListener(new ClubInfoDetailsCell.onSelectCurFragmentListener() {
            @Override
            public void setCurFragment(int position) {
                ClubInfoActivity.this.setCurFragment(position, true);
            }
        });
        adapter.add(mClubInfoDetailsCell);

        rvClubInfo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = llm.findFirstVisibleItemPosition();
                if (position == 0) {
                    llSelectClubIntroduce.setVisibility(View.GONE);
                } else {
                    llSelectClubIntroduce.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.tv_club_introduce)
    public void onClickClubIntroduce(View view) {
        setCurFragment(0, false);
    }

    @OnClick(R.id.tv_action_recent)
    public void onClickActionRencet(View view) {
        setCurFragment(1, false);
    }

    @OnClick(R.id.tv_action_past)
    public void onClickActionPast(View view) {
        setCurFragment(2, false);
    }

    private void setCurFragment(int position, boolean fromCell) {
        switch (position) {
            case 0:
                tvClubIntroduce.setTextColor(ContextCompat.getColor(this, R.color.color_pink));
                tvActionRecent.setTextColor(ContextCompat.getColor(this, R.color.color_block));
                tvActionPast.setTextColor(ContextCompat.getColor(this, R.color.color_block));
                if (isMyClub) {
                    setRightBtnClick("编辑", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mClubDto == null) {
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("clubDto", mClubDto);
                            toNextActivity(EditMyClubActivity.class, bundle);
                        }
                    });
                }
                break;
            case 1:
                tvClubIntroduce.setTextColor(ContextCompat.getColor(this, R.color.color_block));
                tvActionRecent.setTextColor(ContextCompat.getColor(this, R.color.color_pink));
                tvActionPast.setTextColor(ContextCompat.getColor(this, R.color.color_block));
                if (isMyClub) {
                    setRightBtnClick("发布", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        showToast("发布新活动");
                            Bundle bundle = new Bundle();
                            bundle.putString("clubId", mClubDto.getId());
                            toNextActivity(ReleaseActionRecentActivity.class, bundle);
                        }
                    });
                }
                break;
            case 2:
                tvClubIntroduce.setTextColor(ContextCompat.getColor(this, R.color.color_block));
                tvActionRecent.setTextColor(ContextCompat.getColor(this, R.color.color_block));
                tvActionPast.setTextColor(ContextCompat.getColor(this, R.color.color_pink));
                if (isMyClub) {
                    setRightBtnClick("发布", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("clubId", mClubDto.getId());
                            toNextActivity(ReleaseActionPastActivity.class, bundle);
                        }
                    });
                }
                break;
        }
        if (!fromCell) {
            mClubInfoDetailsCell.setCurFragment(position);
        }
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        mSwipeRefresh.setRefreshing(false);
        isFollow = clubDto.getKeepStatus();
        mClubDto = clubDto;
        mClubInfoCell.setMyClub(isMyClub);
        mClubInfoCell.setmData(clubDto);
        mClubInfoCell.setFollow(isFollow);
        mClubInfoDetailsCell.setmData(clubDto);
        mClubInfoDetailsCell.setMyClub(isMyClub);
        rvClubInfo.getAdapter().notifyDataSetChanged();
        setCurFragment(0, false);


    }

    @Override
    public void followClubSuccess() {
        isFollow = !isFollow;
        mClubInfoCell.setFollow(isFollow);
    }
}
