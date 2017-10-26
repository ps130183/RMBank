package com.km.rmbank.module.master;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MasterWorkAdapter;
import com.km.rmbank.adapter.OrderWorkAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.dto.MasterWorkDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.module.payment.PaymentActivity;
import com.ps.androidlib.animator.ShowViewAnimator;
import com.ps.androidlib.utils.AnimationUtil;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MasterInfoActivity extends BaseActivity<MasterInfoPresenter> implements MasterInfoContract.View {

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.iv_master_photo)
    ImageView ivMasterPhoto;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rv_master_work)
    RecyclerView rvMasterWork;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.btn_must_order)
    Button btnMustOrder;

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_work_price)
    TextView tvWorkPrice;
    @BindView(R.id.rl_order_main)
    RelativeLayout rlOrderMain;

    @BindView(R.id.tv_project_name)
    TextView tvProjectName;

    private MasterDto mMasterInfo;
    private String id;

    private ShowViewAnimator showOrderAnimator;

    private MasterWorkDto curMasterWork;

    @Override
    protected int getContentView() {
        return R.layout.activity_master_info;
    }

    @Override
    public MasterInfoPresenter getmPresenter() {
        return new MasterInfoPresenter(this);
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected void onCreate() {
        mMasterInfo = getIntent().getParcelableExtra("masterInfo");
        id = getIntent().getStringExtra("id");
        if (mMasterInfo != null) {
            mTitle.setText(mMasterInfo.getName());
            GlideUtils.loadImage(ivMasterPhoto, mMasterInfo.getRepresentativeImag());
            tvAddress.setText(mMasterInfo.getAddress());
            tvIntroduce.setText(mMasterInfo.getIntroduce());
            tvName.setText(mMasterInfo.getName());
            initMastWork();

            showOrderAnimator = new ShowViewAnimator();
//            int windowWidth = AppUtils.getCurWindowWidth(this);
//            rlOrder.getLayoutParams().height = windowWidth / 3 * 2;

        }
    }

    /**
     * 加载大咖的项目 列表
     */
    private void initMastWork() {
        //项目
        RVUtils.setLinearLayoutManage(rvMasterWork, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvMasterWork);
        final MasterWorkAdapter adapter = new MasterWorkAdapter(this);
        rvMasterWork.setAdapter(adapter);

//        RVUtils.setLinearLayoutManage(rvOrderWork, LinearLayoutManager.VERTICAL);
//        final OrderWorkAdapter orderWorkAdapter = new OrderWorkAdapter(this);
//        rvOrderWork.setAdapter(orderWorkAdapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<MasterWorkDto>() {
            @Override
            public void onItemClick(MasterWorkDto itemData, int position) {
                adapter.checkedWork(itemData);
            }

        });


        adapter.addLoadMore(rvMasterWork, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getMasterWork(id, adapter.getNextPage());
            }
        });

        mPresenter.getMasterWork(id, 1);
    }


    @Override
    public void showMasterWorkList(List<MasterWorkDto> masterWorkDtos, int pageNo) {
        MasterWorkAdapter adapter = (MasterWorkAdapter) rvMasterWork.getAdapter();
        adapter.addData(masterWorkDtos, pageNo);
        adapter.checkedWork(masterWorkDtos.get(0));
    }

    @Override
    public void createOrderSuccess(PayOrderDto payOrderDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("payOrderDto",payOrderDto);
        bundle.putInt("paymentForObj",5);
        toNextActivity(PaymentActivity.class,bundle);
    }

    /**
     * 立即预约
     * @param view
     */
    @OnClick(R.id.btn_must_order)
    public void showOrderWork(View view) {
        showProjectOrder();
        MasterWorkAdapter adapter = (MasterWorkAdapter) rvMasterWork.getAdapter();
        curMasterWork = adapter.getCheckedWork();
        tvWorkPrice.setText("¥" + curMasterWork.getMoney());
        tvProjectName.setText(curMasterWork.getHeadings());
    }

    @OnClick(R.id.iv_close)
    public void hideOrderWork(View view){
        showProjectOrder();
    }

    @OnClick(R.id.rl_order_main)
    public void clickOrderMain(View view){

    }

    @OnClick(R.id.tv_to_pay)
    public void clickToPay(View view){
        if (curMasterWork == null){
            showToast("请选择预约的项目");
            return;
        }
        mPresenter.createMasterOrder(id,curMasterWork.getId(),curMasterWork.getMoney()+"");
    }

    private void showProjectOrder(){
        if (rlOrderMain.getVisibility() == View.GONE){
            rlOrderMain.setVisibility(View.VISIBLE);
        }
        showOrderAnimator.showViewByAnimator(rlOrder, new ShowViewAnimator.onHideListener() {
            @Override
            public void hide() {
                rlOrderMain.setVisibility(View.GONE);
            }
        });
    }
}
