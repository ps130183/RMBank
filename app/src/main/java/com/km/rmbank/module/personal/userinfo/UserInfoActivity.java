package com.km.rmbank.module.personal.userinfo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.utils.PickerUtils;
import com.km.rmbank.utils.retrofit.SecretConstant;
import com.lvfq.pickerview.TimePickerView;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.tv_birthday)
    TextView tvBirthday;

    @BindView(R.id.iv_protrait)
    ImageView ivProtrait;

    @BindView(R.id.et_user_nick_name)
    EditText etUserNickName;

    private UserInfoDto userInfoDto;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_info;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "个人信息";
    }

    @Override
    public UserInfoPresenter getmPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfoDto.setNickName(etUserNickName.getText().toString());
                mPresenter.saveUserInfo(userInfoDto);
            }
        });

        init();
    }

    private void init(){
        userInfoDto = getIntent().getParcelableExtra("userInfo");
        if (userInfoDto == null){
            userInfoDto = new UserInfoDto();
        } else {
            etUserNickName.setText(userInfoDto.getNickName());
            GlideUtils.loadImage(ivProtrait,userInfoDto.getPortraitUrl());
            tvBirthday.setText(userInfoDto.getBirthday());
        }
    }

    @OnClick(R.id.tv_birthday)
    public void chooseBirthday(View view){
        PickerUtils.alertTimerPicker(this, TimePickerView.Type.YEAR_MONTH_DAY,tvBirthday.getText().toString(), "yyyy-MM-dd", new PickerUtils.TimerPickerCallBack() {
            @Override
            public void onTimeSelect(String date) {
                tvBirthday.setText(date);
                userInfoDto.setBirthday(date);
            }
        });
    }

    @OnClick({R.id.rl_protrait,R.id.iv_portrait_left,R.id.iv_protrait})
    public void selectProtrait(View view){
        PermissionGen.with(UserInfoActivity.this)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();

    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess(){
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i){
                    case 0:
                        ImageUtils.getImageFromCamera(UserInfoActivity.this,true,selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(UserInfoActivity.this,
                                ImageUtils.ImageType.PROTRAIT,
                                ImageUtils.ImageNumber.SINGLE,
                                null,
                                selectImageListener);
                        break;
                }
            }
        });
    }
    @PermissionFail(requestCode = 1)
    public void requestCameraFail(){
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener =  new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            mPresenter.uploadProtrait(photoList.get(0));
        }
    };

    @Override
    public void uploadProtraitSuccess(String imageUri) {
        Logger.d("上传头像 ==  " + imageUri);
        GlideUtils.loadCircleImage(ivProtrait, imageUri);
        userInfoDto.setPortraitUrl(imageUri);
    }

    @Override
    public void saveUserInfoSuccess() {
        showToast("保存成功");
        finish();
    }
}
