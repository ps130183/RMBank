package com.km.rmbank.module.personal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.PersonalFunctionCell;
import com.km.rmbank.cell.PersonalUserInfoCell;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.PersonalFunctionEntity;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.module.personal.account.UserAccountActivity;
import com.km.rmbank.module.personal.attention.AttentionGoodsActivity;
import com.km.rmbank.module.personal.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.personal.integral.MyIntegralActivity;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.km.rmbank.module.personal.receiveraddress.ReceiverAddressActivity;
import com.km.rmbank.module.personal.setting.SettingActivity;
import com.km.rmbank.module.personal.shopcart.ShoppingCartActivity;
import com.km.rmbank.module.personal.team.MyTeamActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.personal.userinfo.UserInfoActivity;
import com.km.rmbank.module.personal.vip.BecomeVIPActivity;
import com.km.rmbank.utils.Constant;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.MToast;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class PersonalFragment extends BaseFragment<PersonalPresenter> implements PersonalContract.View {

    public final static int REQUEST_PERMISSION_CAMERA = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    //页面内容
//    @BindView(R.id.swiper_refresh)
//    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private UserInfoDto mUserInfo;
    PersonalUserInfoCell userInfoCell;

    public static PersonalFragment newInstance(Bundle bundle) {
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_personal;
    }

    @Override
    public PersonalPresenter getmPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    protected void createView() {
        title.setText("个人中心");
        initToolbar();
        initRvContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//扫描二维码 成功  接收结果
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            addFriend(result);
        }
    }


    @PermissionSuccess(requestCode = REQUEST_PERMISSION_CAMERA)
    public void success(){
        sweep();
    }
    @PermissionFail(requestCode = REQUEST_PERMISSION_CAMERA)
    public void failed(){
        showToast("照相机权限申请失败");
    }


    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar.inflateMenu(R.menu.toolbar_personal_right);
//        toolbar.setOverflowIcon(baseut);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.rich_scan) {
                    PermissionGen.needPermission(PersonalFragment.this,
                            REQUEST_PERMISSION_CAMERA, Manifest.permission.CAMERA);
                } else if (item.getItemId() == R.id.share) {
                    MToast.showToast(getContext(), "分享");
                }
                return false;
            }
        });

    }

    private void initRvContent() {

        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        final TemplateAdapter adapter = new TemplateAdapter();
        mRecyclerView.setAdapter(adapter);

        final List<ICell> iCells = new ArrayList<>();
        userInfoCell = new PersonalUserInfoCell(null, userCellClickListener);
        PersonalFunctionCell fucntionCell = new PersonalFunctionCell(Constant.user, functionCellClickListener);
        iCells.add(userInfoCell);
        iCells.add(fucntionCell);

        adapter.addData(iCells);

    }

    private ICell.OnCellClickListener<UserInfoDto> userCellClickListener = new ICell.OnCellClickListener<UserInfoDto>() {
        @Override
        public void cellClick(UserInfoDto mData, int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case R.id.iv_user_portrait:
                    bundle.putParcelable("userInfo",mUserInfo);
                    toNextActivity(UserInfoActivity.class,bundle);
                    break;
                case R.id.tv_user_account:
                    toNextActivity(UserAccountActivity.class);
                    break;
                case R.id.tv_edit_card:
                    toNextActivity(EditUserCardActivity.class);
                    break;
                case R.id.tv_vip:
                    toNextActivity(BecomeVIPActivity.class);
                    break;
                case R.id.tv_setting:
                    toNextActivity(SettingActivity.class);
                    break;
                case R.id.iv_shop_cart:
//                    showToast("购物车");
                    toNextActivity(ShoppingCartActivity.class);
                    break;
            }
        }
    };

    private ICell.OnCellClickListener<UserDto> functionCellClickListener = new ICell.OnCellClickListener<UserDto>() {
        @Override
        public void cellClick(UserDto mData, int position) {
            switch (position) {
                case R.id.tv_my_team:
//                    MToast.showToast(getContext(), "我的团队");
                    toNextActivity(MyTeamActivity.class);
                    break;
                case R.id.tv_my_contact:
                    MToast.showToast(getContext(), "我的人脉");
                    break;
                case R.id.tv_my_integral:
//                    MToast.showToast(getContext(), "我的积分");
                    toNextActivity(MyIntegralActivity.class);
                    break;
                case R.id.tv_goods_manager:
//                    MToast.showToast(getContext(), "商品管理");
                    toNextActivity(GoodsManagerActivity.class);
                    break;
                case R.id.tv_my_order:
//                    MToast.showToast(getContext(), "我的订单");
                    toNextActivity(MyOrderActivity.class);
                    break;
                case R.id.tv_address:
//                    MToast.showToast(getContext(), "收货地址");
                    toNextActivity(ReceiverAddressActivity.class);
                    break;
                case R.id.tv_service:
                    MToast.showToast(getContext(), "在线客服");
                    break;
                case R.id.tv_attention:
//                    MToast.showToast(getContext(), "我的关注");
                    toNextActivity(AttentionGoodsActivity.class);
                    break;
            }
        }
    };


    private void sweep() {
        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
    }

    private void addFriend(final String phone){
        DialogUtils.showDefaultAlertDialog("是否要和 " + phone + " 成为好友？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                showToast("与" + phone + "成为好友");
            }
        });
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        mUserInfo = userInfoDto;
        userInfoCell.setmData(userInfoDto);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
