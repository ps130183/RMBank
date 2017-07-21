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
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.BaseAdapter;
import com.km.rv_libs.base.ICell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home3DynamicFragment extends BaseFragment<Home3DynamicPresenter> implements Home3DynamicContract.View {

    @BindView(R.id.rv_home_dynamic)
    RecyclerView rvHomeDynamic;

    public static Home3DynamicFragment newInstance(Bundle bundle) {

        Home3DynamicFragment fragment = new Home3DynamicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home3_dynamic;
    }

    @Override
    public Home3DynamicPresenter getmPresenter() {
        return new Home3DynamicPresenter(this);
    }

    @Override
    protected void createView() {
        initRecycler();
    }

    private void initRecycler(){
        RVUtils.setLinearLayoutManage(rvHomeDynamic, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvHomeDynamic,RVUtils.DIVIDER_COLOR_DEFAULT,3);
        final TemplateAdapter adapter = new TemplateAdapter();
        rvHomeDynamic.setAdapter(adapter);

        adapter.addLoadMore(rvHomeDynamic, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getDynamicInformationList(adapter.getNextPage());
            }
        });

        mPresenter.getDynamicInformationList(1);
    }

    @Override
    public void showDynamicInformationList(List<InformationDto> informationDtos) {
        TemplateAdapter adapter = (TemplateAdapter) rvHomeDynamic.getAdapter();
        List<ICell> mIcells = new ArrayList<>();
        for (int i = 0; i < informationDtos.size(); i++){
            InformationDto informationDto = informationDtos.get(i);
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
