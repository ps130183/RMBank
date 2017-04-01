package com.km.rmbank.module.personal.account.withdraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.adapter.WithDrawAccountAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.WithDrawAccountListCell;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.ps.androidlib.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawListActivity extends BaseActivity<WithDrawPresenter> implements WithDrawContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_draw_list;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "提现管理";
    }

    @Override
    public WithDrawPresenter getmPresenter() {
        return new WithDrawPresenter(this,this);
    }

    @Override
    protected void onCreate() {
        initRecyclerView();
        mPresenter.getWithDrawList();
    }

    private void initRecyclerView(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        WithDrawAccountAdapter adapter = new WithDrawAccountAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private ICell.OnCellClickListener<WithDrawAccountDto> withdrawCellOnClick = new ICell.OnCellClickListener<WithDrawAccountDto>() {
        @Override
        public void cellClick(WithDrawAccountDto mData, int position) {
                  switch (position){
//                      case R.id.cb_isdefault://设置默认
////                          showToast(position + "   mdata = " + mData.toString() );
////                          setDefaultWithDrawAccount(mData);
//                          break;
                      case R.id.tv_edit:
                          Bundle bundle = new Bundle();
                          bundle.putParcelable("withDrawAccountEntity",mData);
                          toNextActivity(CreateWithDrawAccountActivity.class,bundle);
                          break;
                      case R.id.tv_delete:
                          deleteWithDraw(mData);
                          break;

                      default:
                          toNextActivity(WithDrawActivity.class);
                          break;
                  }
        }
    };

    private void deleteWithDraw(final WithDrawAccountDto entity){
        DialogUtils.showDefaultAlertDialog("是否要删除该提现账号？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
//                iCells.remove(entity);
//                adapter.notifyDataChanged();
            }
        });
    }

    @OnClick(R.id.btn_add_account)
    public void createAccount(View view){
        toNextActivity(CreateWithDrawAccountActivity.class);
    }

    @Override
    public void creatOrUpdateSuccess() {

    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {
        WithDrawAccountAdapter adapter = (WithDrawAccountAdapter) mRecyclerView.getAdapter();
        adapter.addData(withDrawAccountDtos);
    }
}
