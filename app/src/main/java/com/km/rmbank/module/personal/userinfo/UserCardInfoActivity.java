package com.km.rmbank.module.personal.userinfo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCardInfoActivity extends BaseActivity<UserCardInfoPresenter> implements UserCardInfoContract.View {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.iv_qrcode)
    ImageView ivQRCode;

    @BindView(R.id.tv_provide_resource)
    TextView tvProvideResource;
    @BindView(R.id.tv_demand_resource)
    TextView tvDemandResource;

    private UserCardDto mUserCardDto;

    //二维码 路径
    private static final String QRCODE_URL = Constant.QRCODE_URL + Constant.user.getMobilePhone();


    @Override
    protected int getContentView() {
        return R.layout.activity_user_card_info;
    }

    @Override
    protected String getTitleName() {
        return "我的名片";
    }

    @Override
    public UserCardInfoPresenter getmPresenter() {
        return new UserCardInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("分享", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDto shareDto = new ShareDto();
                shareDto.setTitle(mUserCardDto.getName() + "-名片");
                StringBuffer bufProvide = new StringBuffer("提供资源：");

                for (String resource : mUserCardDto.getProvideResourcesMap()){
                    bufProvide.append(resource).append(",");
                }
                shareDto.setContent(bufProvide.toString());
                shareDto.setSharePicUrl(Constant.userInfo.getPortraitUrl());
                shareDto.setPageUrl("http://www.baidu.com");
                UmengShareUtils.openShare(UserCardInfoActivity.this, shareDto, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        showToast("成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        showToast("分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        showToast("取消分享");
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserCardInfo();
    }

    @Override
    public void showUserCardInfo(UserCardDto userCardDto) {
        if (userCardDto != null){
            mUserCardDto = userCardDto;
            tvName.setText(userCardDto.getName());
            tvPosition.setText(userCardDto.getPosition());
            tvPhone.setText(userCardDto.getCardPhone());
            tvEmail.setText(userCardDto.getEmailAddress());
            tvAddress.setText(userCardDto.getDetailedAddress());

            ivQRCode.setImageBitmap(QRCodeUtils.createQRCode(this,QRCODE_URL));

            StringBuffer bufProvide = new StringBuffer();
            StringBuffer bufDemand = new StringBuffer();

            for (String resource : userCardDto.getProvideResourcesMap()){
                bufProvide.append(resource).append("\n");
            }

            for (String resource : userCardDto.getDemandResourcesMap()){
                bufDemand.append(resource).append("\n");
            }

            tvProvideResource.setText(bufProvide.toString());
            tvDemandResource.setText(bufDemand.toString());
        }
    }

    @OnClick(R.id.btn_edit_user_card)
    public void clickEditUserCard(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCardDto",mUserCardDto);
        toNextActivity(EditUserCardActivity.class,bundle);
    }
}
