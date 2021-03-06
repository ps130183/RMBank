package com.km.rmbank.module.club.past;

import android.Manifest;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ClubIntroduceAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.event.ClubIntroduceEntity;
import com.km.rmbank.event.ImageTextInfoEvent;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.module.club.ClubInfoActivity;
import com.km.rmbank.module.club.EditClubImageTextInfoActivity;
import com.km.rmbank.ui.CircleProgressView;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.event.CompressImageEvent;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogLoading;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ReleaseActionPastActivity extends BaseActivity<ReleaseActionPastPresenter> implements ReleaseActionPastContract.View {

    @BindView(R.id.rv_action_past_details)
    RecyclerView rvActionPastDetails;

    private int imgUploadPosition = -1;
    private int introduceImgPosition = -1;

    @BindView(R.id.iv_upload_action_img)
    ImageView ivUploadActionPastImg1;

    @BindView(R.id.cpv_upload_action_img)
    CircleProgressView cpvUploadPastacitonImg;

    @BindView(R.id.et_action_past_title)
    EditText etActionPastTitle;

    private ActionPastDto mActionPastDto;

    private int mWindowWidth = 0;


    @Override
    protected int getContentView() {
        return R.layout.activity_release_action_past;
    }

    @Override
    protected String getTitleName() {
        return "编辑往期";
    }

    @Override
    public ReleaseActionPastPresenter getmPresenter() {
        return new ReleaseActionPastPresenter(this);
    }

    @Override
    protected void onCreate() {
        mWindowWidth = AppUtils.getCurWindowWidth(this);
        setRightBtnClick("发布", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDefaultAlertDialog("是否要发布该活动？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        saveActionInfo();
                    }
                });
            }
        });
        initRecyclerView();
        String clubId = getIntent().getStringExtra("clubId");
        mActionPastDto = getIntent().getParcelableExtra("actionPastDto");
        if (mActionPastDto != null){
            showActionPastInfo();
        } else {
            mActionPastDto = new ActionPastDto();
            mActionPastDto.setClubId(clubId);
        }
    }

    private void initRecyclerView() {
        RVUtils.addDivideItemForRv(rvActionPastDetails);
        RVUtils.setLinearLayoutManage(rvActionPastDetails, LinearLayoutManager.VERTICAL);
        final ClubIntroduceAdapter adapter = new ClubIntroduceAdapter(this, R.layout.item_rv_edit_club_introduce);
        rvActionPastDetails.setAdapter(adapter);
    }

    private void showActionPastInfo(){
        GlideUtils.loadImage(ivUploadActionPastImg1,mActionPastDto.getAvatarUrl());
        etActionPastTitle.setText(mActionPastDto.getTitle());

        List<ClubIntroduceEntity> clubIntroduceEntities = new ArrayList<>();
        for (int i = 0; i < mActionPastDto.getDetailList().size(); i++){
            ClubIntroduceEntity entity = new ClubIntroduceEntity();
            ActionPastDto.DynamicBean bean = mActionPastDto.getDetailList().get(i);
            entity.setIntroduceImgPaths(bean.getDynamicImageList());
            entity.setIntroduceContent(bean.getDynamicImageContent());
            entity.setCanDelete(true);
            if (i == mActionPastDto.getDetailList().size() - 1){
                entity.setCanDelete(false);
            }
            clubIntroduceEntities.add(entity);
        }

        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvActionPastDetails.getAdapter();
        adapter.addData(clubIntroduceEntities);
    }

    /**
     * 上传往期活动图片1
     *
     * @param view
     */
    @OnClick({R.id.iv_upload_action_img})
    public void onClickUploadLogo1(View view) {
        imgUploadPosition = 1;
        PermissionGen.with(ReleaseActionPastActivity.this)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }


    @OnClick(R.id.fab_create)
    public void createImageText(View view){
        toNextActivity(EditClubImageTextInfoActivity.class);
    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess() {
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i) {
                    case 0:
                        boolean isCrop;
                        if (imgUploadPosition == 1) {
                            ImageUtils.getSingleImageByCrop(ReleaseActionPastActivity.this, true, 309, 125, selectImageListener);
                        } else if (imgUploadPosition == 2 || imgUploadPosition == 3) {
                            ImageUtils.getSingleImageByCrop(ReleaseActionPastActivity.this, true, 148, 98, selectImageListener);
                        } else {
                            ImageUtils.getImageFromCamera(ReleaseActionPastActivity.this, false, selectImageListener);
                        }

                        break;
                    case 1:
                        if (imgUploadPosition == 1) {
                            ImageUtils.getSingleImageByCrop(ReleaseActionPastActivity.this, false, 309, 125, selectImageListener);
                        } else if (imgUploadPosition == 2 || imgUploadPosition == 3) {
                            ImageUtils.getSingleImageByCrop(ReleaseActionPastActivity.this, false, 148, 98, selectImageListener);
                        } else {
                            ImageUtils.getImageFromPhotoAlbum(ReleaseActionPastActivity.this,
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
            if (imgUploadPosition == 1) {
                ivUploadActionPastImg1.getLayoutParams().width = mWindowWidth / 320 * 309;
                ivUploadActionPastImg1.getLayoutParams().height = mWindowWidth / 320 * 125;
                GlideUtils.loadImage(ivUploadActionPastImg1, imagePath);
            } else if (imgUploadPosition == 4) {
                if (introduceImgPosition < 0) {
                    return;
                }
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvActionPastDetails.getAdapter();
                ClubIntroduceEntity entity = adapter.getItemData(introduceImgPosition);
//                entity.setIntroduceImgPath(imagePath);
                adapter.notifyItemDataChanged(introduceImgPosition);
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
        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvActionPastDetails.getAdapter();
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
    public void uploadActionImgSuccess(String imageUrl, int imageType, int position) {
        switch (imageType) {
            case 1:
                mActionPastDto.setAvatarUrl(imageUrl);
                break;
            case 4:
                if (position < 0) {
                    return;
                }
                Logger.d("adapter item position = " + position);
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvActionPastDetails.getAdapter();
                ClubIntroduceEntity entity = adapter.getItemData(position);
//                entity.setIntroduceImgPath(imageUrl);
                break;
        }
    }

    @Override
    public void showUploadImgProgress(int imageType, int position, int progress) {
        switch (imageType) {
            case 1:
                cpvUploadPastacitonImg.setProgress(progress);
                break;
            case 4:
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvActionPastDetails.getAdapter();
                adapter.setProgress(position, progress);
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
    private void saveActionInfo() {
        mActionPastDto.setTitle(etActionPastTitle.getText().toString());

        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvActionPastDetails.getAdapter();
        List<ClubIntroduceEntity> entityList = adapter.getAllData();
        List<ActionPastDto.DynamicBean> dynamicBeanList = new ArrayList<>();

        for (ClubIntroduceEntity entity : entityList) {
            ActionPastDto.DynamicBean bean = new ActionPastDto.DynamicBean();
            bean.setDynamicImageContent(entity.getIntroduceContent());
            if (entity.getIntroduceImgPaths() != null){
                StringBuffer imageUrls = new StringBuffer();
                for (int i = 0; i < entity.getIntroduceImgPaths().size(); i++){
                    if (i > 0){
                        imageUrls.append("#");
                    }
                    imageUrls.append(entity.getIntroduceImgPaths().get(i));
                }
                bean.setDynamicImage(imageUrls.toString());
            } else {
                bean.setDynamicImage("");
            }

            dynamicBeanList.add(bean);
        }

        mActionPastDto.setDynamicList(dynamicBeanList);

        if (mActionPastDto.isEmpty()) {
            Logger.d(mActionPastDto.toString());
            showToast("请将活动的信息补充完整");
            return;
        }
        mPresenter.releaseActionRecent(mActionPastDto);
    }
}
