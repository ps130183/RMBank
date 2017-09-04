package com.km.rmbank.module.club.recent;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ClubIntroduceAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.event.ClubIntroduceEntity;
import com.km.rmbank.event.ImageTextInfoEvent;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.module.club.ClubInfoActivity;
import com.km.rmbank.module.club.EditClubImageTextInfoActivity;
import com.km.rmbank.module.club.past.ReleaseActionPastActivity;
import com.km.rmbank.ui.CircleProgressView;
import com.km.rmbank.utils.PickerUtils;
import com.lvfq.pickerview.TimePickerView;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.event.CompressImageEvent;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ReleaseActionRecentActivity extends BaseActivity<ReleaseActionRecentPresenter> implements ReleaseActionRecentContract.View {

    @BindView(R.id.rv_guest)
    RecyclerView rvGuest;

    private int imgUploadPosition = -1;
    private int introduceImgPosition = -1;

    @BindView(R.id.iv_upload_action_img)
    ImageView ivUploadActionImg;

    @BindView(R.id.cpv_upload_action_img)
    CircleProgressView cpvUploadActionImg;

    @BindView(R.id.et_action_name)
    EditText etActionName;
    @BindView(R.id.et_start_time)
    EditText etStartTime;
    @BindView(R.id.et_action_address)
    EditText etActionAddress;
    @BindView(R.id.et_action_flow)
    EditText etActionFlow;

    private ActionDto mActionDto;
    private String clubId;

    private int mWindowWidth = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_release_action_recent;
    }

    @Override
    protected String getTitleName() {
        return "编辑活动";
    }

    @Override
    public ReleaseActionRecentPresenter getmPresenter() {
        return new ReleaseActionRecentPresenter(this);
    }

    @Override
    protected void onCreate() {
        mWindowWidth = AppUtils.getCurWindowWidth(this);
        setRightBtnClick("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toNextActivity(ActionRecentInfoActivity.class);
                DialogUtils.showDefaultAlertDialog("是否要发布该活动？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        saveActionInfo();
                    }
                });
            }
        });
        initGuestList();
        clubId = getIntent().getStringExtra("clubId");
        mActionDto = getIntent().getParcelableExtra("actionDto");
        if (mActionDto == null){
            mActionDto = new ActionDto();
        } else {
            showActionInfo(mActionDto);
        }
    }

    /**
     * 初始化 邀请嘉宾列表
     */
    private void initGuestList() {

        RVUtils.addDivideItemForRv(rvGuest);
        RVUtils.setLinearLayoutManage(rvGuest, LinearLayoutManager.VERTICAL);
        final ClubIntroduceAdapter adapter = new ClubIntroduceAdapter(this,R.layout.item_rv_release_action_recent_guest);
        adapter.setType(2);
        rvGuest.setAdapter(adapter);
    }

    private void showActionInfo(ActionDto actionDto){
        GlideUtils.loadImage(ivUploadActionImg,actionDto.getActivityPictureUrl());
        etActionName.setText(actionDto.getTitle());
        etStartTime.setText(actionDto.getHoldDate());
        etActionAddress.setText(actionDto.getAddress());
        etActionFlow.setText(actionDto.getFlow());

        List<ClubIntroduceEntity> clubIntroduceEntities = new ArrayList<>();
        for (int i = 0; i < actionDto.getGuestList().size(); i++){
            ClubIntroduceEntity entity = new ClubIntroduceEntity();
            ActionDto.ActionGuestBean bean = actionDto.getGuestList().get(i);
            List<String> imagePaths = new ArrayList<>();
            imagePaths.add(bean.getAvatarUrl());
            entity.setIntroduceImgPaths(imagePaths);
            entity.setIntroduceContent(bean.getTitle());
            entity.setCanDelete(true);
            if (i == actionDto.getGuestList().size() - 1){
                entity.setCanDelete(false);
            }
            clubIntroduceEntities.add(entity);
        }

        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvGuest.getAdapter();
        adapter.addData(clubIntroduceEntities);
    }

    /**
     * 上传俱乐部Logo
     *
     * @param view
     */
    @OnClick({R.id.iv_upload_action_img})
    public void onClickUploadLogo(View view) {
        imgUploadPosition = 1;
        PermissionGen.with(ReleaseActionRecentActivity.this)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    @OnClick(R.id.et_start_time)
    public void selectStartTime(View view){
        PickerUtils.alertTimerPicker(this, TimePickerView.Type.ALL,etStartTime.getText().toString(), "yyyy-MM-dd HH:mm", new PickerUtils.TimerPickerCallBack() {
            @Override
            public void onTimeSelect(String date) {
                etStartTime.setText(date);
                mActionDto.setHoldDate(date);
            }
        });
    }

    @OnClick(R.id.fab_create)
    public void createNewGuest(View view){
        Bundle bundle = new Bundle();
        bundle.putBoolean("isActionRecent",true);
        toNextActivity(EditClubImageTextInfoActivity.class,bundle);
    }
//    @OnClick(R.id.iv_background)
//    public void onClickUploadCludBackground(View view) {
//        imgUploadPosition = 2;
//        PermissionGen.with(ReleaseActionRecentActivity.this)
//                .addRequestCode(1)
//                .permissions(Manifest.permission.CAMERA)
//                .request();
//    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess() {
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i) {
                    case 0:
                        if (imgUploadPosition == 1) {
                            ImageUtils.getSingleImageByCrop(ReleaseActionRecentActivity.this,false,309,125,selectImageListener);
                        } else {
                            ImageUtils.getImageFromCamera(ReleaseActionRecentActivity.this, false, selectImageListener);
                        }

                        break;
                    case 1:
                        if (imgUploadPosition == 1) {
                            ImageUtils.getSingleImageByCrop(ReleaseActionRecentActivity.this,false,309,125,selectImageListener);
                        } else {
                            ImageUtils.getImageFromPhotoAlbum(ReleaseActionRecentActivity.this,
                                    ImageUtils.ImageType.PRODUCT,
                                    ImageUtils.ImageNumber.SINGLE,
                                    null,
                                    selectImageListener);
                        }

                        break;
                }
            }
        });
    }

    @PermissionFail(requestCode = 1)
    public void requestCameraFail() {
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            String imagePath = photoList.get(0);
//            mPresenter.uploadProtrait(photoList.get(0));
            if (imgUploadPosition == 1) {
//                ivUploadActionImg.setVisibility(View.VISIBLE);
                ivUploadActionImg.getLayoutParams().width = mWindowWidth / 320 * 309;
                ivUploadActionImg.getLayoutParams().height = mWindowWidth / 320 * 125;
                GlideUtils.loadImage(ivUploadActionImg, imagePath);
            } else if (imgUploadPosition == 2) {
//                GlideUtils.loadImage(ivBackground, photoList.get(0));
            } else if (imgUploadPosition == 3) {
                if (introduceImgPosition < 0) {
                    return;
                }
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvGuest.getAdapter();
                ClubIntroduceEntity entity = adapter.getItemData(introduceImgPosition);
//                entity.setIntroduceImgPath(imagePath);
                adapter.notifyItemChanged(introduceImgPosition);
            }
//            mPresenter.uploadActionImg(imagePath, imgUploadPosition, introduceImgPosition);
            EventBusUtils.post(new UploadImageEvent(imagePath));

        }
    };

//    private Dialog compressImageDialog;
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void compressImageing(CompressImageEvent event){
//        if (compressImageDialog == null){
//            compressImageDialog = DialogUtils.showLoadingDialog("正在上传图片，请稍后。。。");
//        } else {
//            compressImageDialog.show();
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(UploadImageEvent event){
        com.ps.androidlib.utils.ImageUtils.compressImage(event.getImagePath())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
//                        compressImageDialog.dismiss();
                        mPresenter.uploadActionImg(s, imgUploadPosition, introduceImgPosition);
                    }
                });

    }

    /**
     * 编辑完 图文详情 返回的结果
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditImageText(ImageTextInfoEvent event){
        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvGuest.getAdapter();
        if (event.getPosition() < 0){ //新增
            ClubIntroduceEntity entity = new ClubIntroduceEntity();
            entity.setIntroduceContent(event.getTextContent());
            entity.setIntroduceImgPaths(event.getImageList());
            int position = adapter.addData(entity);
            adapter.notifyDataChanged();
            Logger.d("add data position ====  " + position);
        } else {//修改 编辑
            ClubIntroduceEntity entity = adapter.getItemData(event.getPosition());
            entity.setIntroduceContent(event.getTextContent());
            entity.setIntroduceImgPaths(event.getImageList());
            adapter.notifyItemChanged(event.getPosition());
            Logger.d("refresh data position ====  " + event.getPosition());
        }


    }

    @Override
    public void uploadActionImgSuccess(String imageUrl, int imageType,int position) {
        switch (imageType){
            case 1:
                mActionDto.setActivityPictureUrl(imageUrl);
                break;
            case 2:
                break;
            case 3:
                if (position < 0) {
                    return;
                }
                Logger.d("adapter item position = " + position);
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvGuest.getAdapter();
                ClubIntroduceEntity entity = adapter.getItemData(position);
//                entity.setIntroduceImgPath(imageUrl);
                break;
        }
    }

    @Override
    public void showUploadImgProgress(int imageType, int position, int progress) {
        switch (imageType){
            case 1:
                cpvUploadActionImg.setProgress(progress);
                break;
            case 2:
                break;
            case 3:
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvGuest.getAdapter();
                adapter.setProgress(position,progress);
                break;
        }
    }

    @Override
    public void releaseActionSuccess() {
        toNextActivity(ClubInfoActivity.class);
        finish();
    }

    /**
     * 保存 并 发布活动
     */
    private void saveActionInfo(){
        mActionDto.setTitle(etActionName.getText().toString());
        mActionDto.setHoldDate(etStartTime.getText().toString());
        mActionDto.setAddress(etActionAddress.getText().toString());
        mActionDto.setFlow(etActionFlow.getText().toString());

        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvGuest.getAdapter();
        List<ClubIntroduceEntity> entityList = adapter.getAllData();
        List<ActionDto.ActionGuestBean> guestBeanList = new ArrayList<>();

        for (ClubIntroduceEntity entity : entityList){
            ActionDto.ActionGuestBean bean = new ActionDto.ActionGuestBean();
            bean.setTitle(entity.getIntroduceContent());
            bean.setAvatarUrl(entity.getIntroduceImgPaths().get(0));
            guestBeanList.add(bean);
        }
        mActionDto.setGuestList(guestBeanList);

        if (mActionDto.isEmpty()){
            Logger.d(mActionDto.toString());
            showToast("请将活动的信息补充完整");
            return;
        }
        mPresenter.releaseActionRecent(mActionDto,clubId);
    }
}
