package com.km.rmbank.module.personal.userinfo.editcart;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.IndustryParentAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.IndustryDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EditUserResourceActivity extends BaseActivity<ResourcePresenter> implements ResourceContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    @BindView(R.id.title)
    TextView mTitle;

    private int requestCode;

    private List<IndustryDto> industryEntities;
//
    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_provider_resource;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "提供资源";
    }

    @Override
    public ResourcePresenter getmPresenter() {
        return new ResourcePresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndustryParentAdapter adapter = (IndustryParentAdapter) mRecyclerview.getAdapter();
                ArrayList<IndustryDto> industryCheck = (ArrayList<IndustryDto>) adapter.getAllIndustryChecked();
                if (industryCheck == null || industryCheck.size() == 0){
                    showToast("请选择你需要的行业");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("checkindustry",industryCheck);
                bundle.putInt("requestcode",requestCode);
                setResult(requestCode,bundle);
            }
        });
        init();
    }

    private void init(){
        requestCode = getIntent().getIntExtra("requestcode",0);
        String title = getIntent().getStringExtra("title");
        mTitle.setText(title);
        RVUtils.setLinearLayoutManage(mRecyclerview,LinearLayoutManager.VERTICAL);
        IndustryParentAdapter adapter = new IndustryParentAdapter(this);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void showIndustry(List<IndustryDto> industryEntities) {
        IndustryParentAdapter adapter = (IndustryParentAdapter) mRecyclerview.getAdapter();
        adapter.addData(industryEntities);
    }
}
