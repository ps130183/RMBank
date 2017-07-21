package com.km.rmbank.module.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.Home3ClubAction1Cell;
import com.km.rmbank.cell.Home3ClubAction2Cell;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.module.club.past.ActionPastDetailActivity;
import com.km.rmbank.module.club.recent.ActionRecentInfoActivity;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.BaseAdapter;
import com.km.rv_libs.base.ICell;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.BannerUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3ClubFragment extends BaseFragment<Home3ClubPresenter> implements Home3ClubContract.View {

    private int mWindowWidth = 0;
    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.rv_club_action)
    RecyclerView rvClubAction;

    public static Home3ClubFragment newInstance(Bundle bundle) {

        Home3ClubFragment fragment = new Home3ClubFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_club;
    }

    @Override
    public Home3ClubPresenter getmPresenter() {
        return new Home3ClubPresenter(this);
    }

    @Override
    protected void createView() {
        mWindowWidth = AppUtils.getCurWindowWidth(getContext());
        mPresenter.getBannerList();
        initRecyclerview();
    }

    private void initBannerData(final List<InformationDto> informationDtos) {
        mBanner.getLayoutParams().height = mWindowWidth / 320 * 141;
        List<String> bannerDataList = new ArrayList<>();
        for (InformationDto informationDto : informationDtos){
            String[] avatarUrls = informationDto.getAvatarUrl().split("#");
            bannerDataList.add(avatarUrls[0]);
        }
        BannerUtils.initBannerFromUrl(mBanner, bannerDataList, new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                InformationDto informationDto = informationDtos.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",informationDto.getId());
                toNextActivity(ActionPastDetailActivity.class,bundle);
            }
        });
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(rvClubAction, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvClubAction,RVUtils.DIVIDER_COLOR_DEFAULT,3);
        final TemplateAdapter adapter = new TemplateAdapter();

        rvClubAction.setAdapter(adapter);

        adapter.addLoadMore(rvClubAction, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getActionList(adapter.getNextPage());
            }
        });

        mPresenter.getActionList(1);

    }

    @Override
    public void showBannerList(List<InformationDto> informationDtos) {
        initBannerData(informationDtos);
    }

    @Override
    public void getActionListSuccess(List<InformationDto> actionDtos, int pageNo) {
        TemplateAdapter adapter = (TemplateAdapter) rvClubAction.getAdapter();
        List<ICell> mIcells = new ArrayList<>();
        for (int i = 0; i < actionDtos.size(); i++){
            InformationDto informationDto = actionDtos.get(i);
            if (i % 2 == 0){
                Home3ClubAction1Cell cell1 = new Home3ClubAction1Cell(getContext(),informationDto);
                cell1.setOnCellClickListener(clickActionListener);
                mIcells.add(cell1);
            } else {
                Home3ClubAction2Cell cell2 = new Home3ClubAction2Cell(getContext(),informationDto);
                cell2.setOnCellClickListener(clickActionListener);
                mIcells.add(cell2);
            }
        }
        adapter.addData(mIcells);
    }

    ICell.OnCellClickListener clickActionListener = new ICell.OnCellClickListener<InformationDto>() {
        @Override
        public void cellClick(InformationDto mData, int position) {
            Bundle bundle = new Bundle();
            bundle.putString("actionPastId",mData.getId());
            toNextActivity(ActionPastDetailActivity.class,bundle);
        }
    };
}
