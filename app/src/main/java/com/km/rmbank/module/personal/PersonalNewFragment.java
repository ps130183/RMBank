package com.km.rmbank.module.personal;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseFragment;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.cell.PersonalFunctionCell;
import com.km.rmbank.cell.PersonalUserInfoCell;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.event.UpdateEaseUserUnreadNumberEvent;
import com.km.rmbank.module.club.ClubInfoActivity;
import com.km.rmbank.module.club.EditMyClubActivity;
import com.km.rmbank.module.personal.account.UserAccountActivity;
import com.km.rmbank.module.personal.attention.AttentionGoodsActivity;
import com.km.rmbank.module.personal.goodsmanager.GoodsManagerActivity;
import com.km.rmbank.module.personal.integral.MyIntegralActivity;
import com.km.rmbank.module.personal.mycontact.MyContactActivity;
import com.km.rmbank.module.personal.order.MyOrderActivity;
import com.km.rmbank.module.personal.receiveraddress.ReceiverAddressActivity;
import com.km.rmbank.module.personal.setting.SettingActivity;
import com.km.rmbank.module.personal.shopcart.ShoppingCartActivity;
import com.km.rmbank.module.personal.team.MyTeamActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;
import com.km.rmbank.module.personal.userinfo.UserCardInfoActivity;
import com.km.rmbank.module.personal.userinfo.UserInfoActivity;
import com.km.rmbank.module.personal.vip.SelectMemberTypeActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rv_libs.TemplateAdapter;
import com.km.rv_libs.base.ICell;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.MToast;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalNewFragment extends BaseFragment<PersonalPresenter> implements PersonalContract.View {


    public final static int REQUEST_PERMISSION_CAMERA = 1;
    public final static int REQUEST_PERMISSION_SHARE = 2;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//
//    @BindView(R.id.title)
//    TextView title;

    private ShareDto shareDto;

    //页面内容
//    @BindView(R.id.swiper_refresh)
//    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private UserInfoDto mUserInfo;
    PersonalUserInfoCell userInfoCell;
    PersonalFunctionCell fucntionCell;

    public static PersonalFragment newInstance(Bundle bundle) {
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public PersonalPresenter getmPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_personal_new;
    }

    @Override
    protected void createView() {
//        title.setText("个人中心");
        initRvContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadUserInfo();
//        mPresenter.getShareContent();

        fucntionCell.setMyContactHintVisible(EaseUserChatUtils.isUnreadMsg());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//扫描二维码 成功  接收结果
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            mPresenter.getUserInfoByQRCode(result);
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


    @PermissionSuccess(requestCode = REQUEST_PERMISSION_SHARE)
    public void requesShare(){

    }
    @PermissionFail(requestCode = REQUEST_PERMISSION_SHARE)
    public void failShare(){
        showToast("获取权限失败");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUnreadNumber(UpdateEaseUserUnreadNumberEvent event){
        fucntionCell.setMyContactHintVisible(true);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }



    private void initRvContent() {

        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        final TemplateAdapter adapter = new TemplateAdapter();
        mRecyclerView.setAdapter(adapter);

        final List<ICell> iCells = new ArrayList<>();
        userInfoCell = new PersonalUserInfoCell(null, userCellClickListener);
        fucntionCell = new PersonalFunctionCell(Constant.user, functionCellClickListener);
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
                    if (Constant.userInfo.getIsNotEditCard() == 0){
                        toNextActivity(EditUserCardActivity.class);
                    } else {
                        toNextActivity(UserCardInfoActivity.class);
                    }
                    break;
                case R.id.tv_vip:
                    if ("2".equals(Constant.user.getRoleId())){
                        showToast("您已经是合伙人会员");
                        return;
                    }
                    toNextActivity(SelectMemberTypeActivity.class);
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
                case R.id.tv_my_club:
                    if (Constant.userInfo.getClubStatus() == 0){
                        toNextActivity(EditMyClubActivity.class);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isMyClub",true);
                        toNextActivity(ClubInfoActivity.class,bundle);
                    }
//                    toNextActivity(mycl);
                    break;
                case R.id.tv_my_team:
//                    MToast.showToast(getContext(), "我的团队");
                    toNextActivity(MyTeamActivity.class);
                    break;
                case R.id.tv_my_contact:
//                    MToast.showToast(getContext(), "暂未开通");
                    toNextActivity(MyContactActivity.class);
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
                case R.id.tv_service_phone:
                case R.id.rl_service:
//                    MToast.showToast(getContext(), "在线客服");
                    call("010-87655945");
                    break;
                case R.id.tv_attention:
//                    MToast.showToast(getContext(), "我的关注");
                    toNextActivity(AttentionGoodsActivity.class);
                    break;
            }
        }
    };


    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sweep() {
        startActivityForResult(new Intent(getActivity(), CaptureActivity.class), 0);
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        mUserInfo = userInfoDto;
        Constant.userInfo = userInfoDto;
        Constant.isAllowUserCard = (TextUtils.isEmpty(mUserInfo.getAllowStutas()) || "0".equals(mUserInfo.getAllowStutas()));
        userInfoCell.setmData(userInfoDto);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void getUserInfoByQRCodeSuccess(UserCardDto userCardDto, String friendPhone) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCardDto",userCardDto);
        bundle.putString("friendPhone",friendPhone);
        toNextActivity(EditUserCardActivity.class,bundle);
    }

    @Override
    public void showShareContent(ShareDto shareDto) {
        this.shareDto = shareDto;
    }

}
