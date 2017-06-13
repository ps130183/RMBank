package com.km.rmbank.module.personal.userinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserChatUtils;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.module.chat.EaseChatActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserAddressActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserCompanyActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserCompanyIntroActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserEmailActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserJobActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserNameActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserPhoneActivity;
import com.km.rmbank.module.personal.userinfo.editcart.EditUserResourceActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.PickerUtils;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.retrofit.SecretConstant;
import com.ps.androidlib.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserCardActivity extends BaseActivity<EditUserCardPresenter> implements EditUserCartContract.View {

    public static final int REQUEST_CODE_ET_NAME = 1001;
    public static final int REQUEST_CODE_ET_PHONE = 1002;
    public static final int REQUEST_CODE_ET_COMPANY = 1003;
    public static final int REQUEST_CODE_ET_JOB = 1004;
    public static final int REQUEST_CODE_ET_COMPANY_INTRO = 1005;
    public static final int REQUEST_CODE_ET_PROVIDER_RESOURCE = 1006;
    public static final int REQUEST_CODE_ET_NEED_RESOURCE = 1007;
    public static final int REQUEST_CODE_ET_LOCATION = 1008;
    public static final int REQUEST_CODE_ET_ADDRESS = 1009;
    public static final int REQUEST_CODE_ET_EMAIL = 1010;

    //二维码 路径
    private static final String QRCODE_URL = Constant.QRCODE_URL + Constant.user.getMobilePhone();

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_company)
    EditText etCompany;

    @BindView(R.id.et_job)
    EditText etJob;

    @BindView(R.id.et_company_intro)
    EditText etCompanyIntro;

    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.vMasker)
    View vMasker;

    @BindView(R.id.et_provider_resource)
    EditText etProviderResource;

    @BindView(R.id.et_need_resource)
    EditText etNeedResource;

    @BindView(R.id.et_address)
    EditText etAddress;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.iv_qrcode)
    ImageView ivQRCode;

    @BindView(R.id.btn_create_code)
    Button btnCreateCode;

    private UserCardDto userCardDto;
    private String friendPhone;
    private boolean fromQRCode = false;

    private MyTeamDto.MemberDtoListBean memberDtoListBean;//团队成员  来自我的团队

    private String shopId;// 商户的id，表明 来自商品详情页

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_card;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "编辑名片";
    }

    @Override
    public EditUserCardPresenter getmPresenter() {
        return new EditUserCardPresenter(this);
    }

    @Override
    protected void onCreate() {
        userCardDto = getIntent().getParcelableExtra("userCardDto");
        friendPhone = getIntent().getStringExtra("friendPhone");
        memberDtoListBean = getIntent().getParcelableExtra("memberDto");
        shopId = getIntent().getStringExtra("shopId");
//        PickerUtils.showOptions(this,etLocation,vMasker);
        if (userCardDto != null){ //来源 ： 扫一扫  其他人的名片
            showUserCard(userCardDto);
            if (userCardDto.getStatus() == 2){
                btnCreateCode.setText("发消息");
            } else {
                btnCreateCode.setText("加入我的人脉");
            }
//            btnCreateCode.setText("加入我的人脉");
            title.setText("好友信息");
            ivQRCode.setVisibility(View.GONE);
            fromQRCode = true;
        } else if (memberDtoListBean != null){
            title.setText("好友信息");
            btnCreateCode.setVisibility(View.GONE);
            ivQRCode.setVisibility(View.GONE);
            fromQRCode = true;
            mPresenter.getUserCardById(memberDtoListBean.getId());
        } else if (!TextUtils.isEmpty(shopId)){
            title.setText("商家信息");
            btnCreateCode.setVisibility(View.VISIBLE);
            btnCreateCode.setText("联系商家");
            ivQRCode.setVisibility(View.GONE);
            fromQRCode = true;
            mPresenter.getUserCardById(shopId);
        } else {
            PickerUtils.showOptions(this,etLocation,vMasker);
            mPresenter.getUserCard();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case REQUEST_CODE_ET_NAME:
                userCardDto.setName(data.getStringExtra("name"));
                etName.setText(userCardDto.getName());
                break;
            case REQUEST_CODE_ET_PHONE:
                userCardDto.setCardPhone(data.getStringExtra("phone"));
                etPhone.setText(userCardDto.getCardPhone());
                break;
            case REQUEST_CODE_ET_COMPANY:
                userCardDto.setCompany(data.getStringExtra("company"));
                etCompany.setText(userCardDto.getCompany());
                break;
            case REQUEST_CODE_ET_JOB:
                userCardDto.setPosition(data.getStringExtra("job"));
                etJob.setText(userCardDto.getPosition());
                break;
            case REQUEST_CODE_ET_COMPANY_INTRO:
                userCardDto.setCompanyProfile(data.getStringExtra("companyIntro"));
                etCompanyIntro.setText(userCardDto.getCompanyProfile());
                break;
            case REQUEST_CODE_ET_PROVIDER_RESOURCE:
            case REQUEST_CODE_ET_NEED_RESOURCE:
                List<IndustryDto> industryEntities = data.getParcelableArrayListExtra("checkindustry");
                setResource(industryEntities,resultCode);
                break;
            case REQUEST_CODE_ET_EMAIL:
                userCardDto.setEmailAddress(data.getStringExtra("email"));
                etEmail.setText(userCardDto.getEmailAddress());
                break;
            case REQUEST_CODE_ET_ADDRESS:
                userCardDto.setDetailedAddress(data.getStringExtra("address"));
                etAddress.setText(userCardDto.getDetailedAddress());
                break;
        }
    }

    private void setResource(List<IndustryDto> industryEntities, int resultCode){
        StringBuffer buffer = new StringBuffer();
        StringBuffer resourdeIds = new StringBuffer();
        if (industryEntities != null){
            for (IndustryDto entity : industryEntities){
                buffer.append(entity.getName()).append(",");
                resourdeIds.append(entity.getId()).append("#");
            }
            buffer.replace(buffer.length() - 1,buffer.length(),"");
            resourdeIds.replace(resourdeIds.length() - 1,resourdeIds.length(),"");
            if (resultCode == REQUEST_CODE_ET_PROVIDER_RESOURCE){
                userCardDto.setProvideResourcesMap(industryEntities);
                userCardDto.setProvideResourcesId(resourdeIds.toString());
                etProviderResource.setText(buffer.toString());
            } else {
                userCardDto.setDemandResourcesMap(industryEntities);
                userCardDto.setDemandResourcesId(resourdeIds.toString());
                etNeedResource.setText(buffer.toString());
            }
        }
    }

    /**
     * 编辑姓名
     * @param view
     */
    @OnClick(R.id.et_name)
    public void editName(View view){
        if (fromQRCode) return;
        String name = etName.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        toNextActivityForResult(REQUEST_CODE_ET_NAME,bundle);
    }

    /**
     * 编辑手机号
     * @param view
     */
    @OnClick(R.id.et_phone)
    public void editPhone(View view){
        if (fromQRCode) return;
        String phone = etPhone.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("phone",phone);
        toNextActivityForResult(REQUEST_CODE_ET_PHONE,bundle);
    }

    /**
     * 编辑公司名称
     * @param view
     */
    @OnClick(R.id.et_company)
    public void editCompany(View view){
        if (fromQRCode) return;
        String company = etCompany.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("company",company);
        toNextActivityForResult(REQUEST_CODE_ET_COMPANY,bundle);
    }

    /**
     * 编辑职位
     * @param view
     */
    @OnClick(R.id.et_job)
    public void editJob(View view){
        if (fromQRCode) return;
        String job = etJob.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("job",job);
        toNextActivityForResult(REQUEST_CODE_ET_JOB,bundle);
    }

    /**
     * 编辑职位
     * @param view
     */
    @OnClick(R.id.et_company_intro)
    public void editCompanyIntro(View view){
        if (fromQRCode) return;
        String companyIntro = etCompanyIntro.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("companyIntro",companyIntro);
        toNextActivityForResult(REQUEST_CODE_ET_COMPANY_INTRO,bundle);
    }

    /**
     * 提供资源
     * @param view
     */
    @OnClick(R.id.et_provider_resource)
    public void editProviderResource(View view){
        if (fromQRCode) return;
        Bundle bundle = new Bundle();
        bundle.putInt("requestcode",REQUEST_CODE_ET_PROVIDER_RESOURCE);
        bundle.putString("title","提供资源");
        toNextActivityForResult(REQUEST_CODE_ET_PROVIDER_RESOURCE,bundle);
    }

    /**
     * 需求资源
     * @param view
     */
    @OnClick(R.id.et_need_resource)
    public void editNeedResource(View view){
        if (fromQRCode) return;
        Bundle bundle = new Bundle();
        bundle.putInt("requestcode",REQUEST_CODE_ET_NEED_RESOURCE);
        bundle.putString("title","需求资源");
        toNextActivityForResult(REQUEST_CODE_ET_NEED_RESOURCE,bundle);
    }


    /**
     * 编辑详细地址
     * @param view
     */
    @OnClick(R.id.et_address)
    public void editAddress(View view){
        if (fromQRCode) return;
        String address = etAddress.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        toNextActivityForResult(REQUEST_CODE_ET_ADDRESS,bundle);
//        choosArea();
    }

    /**
     * 编辑email
     * @param view
     */
    @OnClick(R.id.et_email)
    public void editEmail(View view){
        if (fromQRCode) return;
        String email = etEmail.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        toNextActivityForResult(REQUEST_CODE_ET_EMAIL,bundle);
//        choosArea();
    }

    @OnClick(R.id.btn_create_code)
    public void createQRCode(View view){

        if (fromQRCode){
            if (TextUtils.isEmpty(shopId)){
                if (userCardDto.getStatus() == 2){//发消息
                    EaseUser user = new EaseUser(userCardDto.getMobilePhone());
                    user.setNickname(userCardDto.getNickName());
                    user.setAvatar(userCardDto.getPortraitUrl());
                    EaseUserChatUtils.addUser(user);
                    Bundle bundle = new Bundle();
                    bundle.putString("to_user_id",userCardDto.getMobilePhone());
                    bundle.putString("user_nick_name",userCardDto.getNickName());
                    toNextActivity(EaseChatActivity.class,bundle);
                } else {
                    DialogUtils.showDefaultAlertDialog("是否要将 " + userCardDto.getName() + " 加入我的人脉?", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
//                        showToast(friendPhone);
                            mPresenter.applyBecomeFriend(friendPhone);
                        }
                    });
                }
            } else {
//                showToast("联系商家");
                EaseUser user = new EaseUser(userCardDto.getMobilePhone());
                user.setNickname(userCardDto.getNickName());
                user.setAvatar(userCardDto.getPortraitUrl());
                EaseUserChatUtils.addUser(user);

                mPresenter.addShopToFriend(userCardDto.getMobilePhone());

                Bundle bundle = new Bundle();
                bundle.putString("to_user_id",userCardDto.getMobilePhone());
                bundle.putString("user_nick_name",userCardDto.getNickName());
                toNextActivity(EaseChatActivity.class,bundle);
//                EaseUI.getInstance().setAvatarOptions()
            }

        } else {
            String location = etLocation.getText().toString();
            userCardDto.setLocation(location);
            if (userCardDto.isEmpty()){
                showToast("请将您的信息补充完整");
                return;
            }

            mPresenter.createUserCard(userCardDto);
        }

    }



    public void toNextActivityForResult(int requestCode, Bundle bundle) {
        Class classes = null;
        switch (requestCode){
            case REQUEST_CODE_ET_NAME:
                classes = EditUserNameActivity.class;
                break;
            case REQUEST_CODE_ET_PHONE:
                classes = EditUserPhoneActivity.class;
                break;
            case REQUEST_CODE_ET_COMPANY:
                classes = EditUserCompanyActivity.class;
                break;
            case REQUEST_CODE_ET_JOB:
                classes = EditUserJobActivity.class;
                break;
            case REQUEST_CODE_ET_COMPANY_INTRO:
                classes = EditUserCompanyIntroActivity.class;
                break;
            case REQUEST_CODE_ET_PROVIDER_RESOURCE:
            case REQUEST_CODE_ET_NEED_RESOURCE:
                classes = EditUserResourceActivity.class;
                break;
            case REQUEST_CODE_ET_ADDRESS:
                classes = EditUserAddressActivity.class;
                break;
            case REQUEST_CODE_ET_EMAIL:
                classes = EditUserEmailActivity.class;
                break;
        }
        super.toNextActivityForResult(classes, requestCode, bundle);
    }

    @Override
    public void showUserCard(UserCardDto userCardDto) {
        if (memberDtoListBean != null && userCardDto.isEmpty()){
            showToast("该用户尚未编辑名片");
            finish();
            return;
        }
        if (!TextUtils.isEmpty(shopId) && userCardDto.isEmpty()){
            showToast("该商家尚未编辑名片");
            finish();
            return;
        }
        this.userCardDto = userCardDto;
        if (this.userCardDto == null || userCardDto.isEmpty()){
            this.userCardDto = new UserCardDto();
            return;
        } else if (memberDtoListBean != null){
            btnCreateCode.setVisibility(View.VISIBLE);
            friendPhone = userCardDto.getMobilePhone();
            if (userCardDto.getStatus() == 2){
                btnCreateCode.setText("发消息");
            } else {
                btnCreateCode.setText("加入我的人脉");
            }
        } else if (!TextUtils.isEmpty(shopId)){

        } else {
//            ivQRCode.setImageBitmap(QRCodeUtils.createQRCode(EditUserCardActivity.this, Constant.user.getMobilePhone()));
            ivQRCode.setImageBitmap(QRCodeUtils.createQRCode(EditUserCardActivity.this, QRCODE_URL));
            ivQRCode.setVisibility(View.VISIBLE);
            btnCreateCode.setText("更新名片");
        }

        etName.setText(userCardDto.getName());
        etPhone.setText(userCardDto.getCardPhone());
        etCompany.setText(userCardDto.getCompany());
        etJob.setText(userCardDto.getPosition());
        etCompanyIntro.setText(userCardDto.getCompanyProfile());
        setResource(userCardDto.getProvideResourcesMap(),REQUEST_CODE_ET_PROVIDER_RESOURCE);
        setResource(userCardDto.getDemandResourcesMap(),REQUEST_CODE_ET_NEED_RESOURCE);
        etLocation.setText(userCardDto.getLocation());
        etAddress.setText(userCardDto.getDetailedAddress());
        etEmail.setText(userCardDto.getEmailAddress());
    }

    ///membero/test
    @Override
    public void createUserCardSuccess() {
        ivQRCode.setImageBitmap(QRCodeUtils.createQRCode(EditUserCardActivity.this, QRCODE_URL));
        ivQRCode.setVisibility(View.VISIBLE);
        if ("更新名片".equals(btnCreateCode.getText().toString())){
            showToast("更新成功");
        }
    }

    @Override
    public void applyBecomeFriendSuccess() {
        showToast("已将对方添加到人脉中");
        userCardDto.setStatus(2);
        btnCreateCode.setText("发消息");
    }

    @Override
    public void addFriendSuccess() {

    }
}
