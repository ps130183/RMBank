package com.km.rmbank.module.club;

import android.Manifest;
import android.app.Dialog;
import android.os.Looper;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ClubIntroduceAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.event.ClubIntroduceEntity;
import com.km.rmbank.event.ImageTextInfoEvent;
import com.km.rmbank.event.RefreshClubInfoEvent;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.module.personal.userinfo.UserInfoActivity;
import com.km.rmbank.ui.CircleProgressView;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.event.CompressImageEvent;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.SoftKeyInputHidWidget;
import com.ps.androidlib.utils.StatusBarUtil;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class EditMyClubActivity extends BaseActivity<EditMyClubPresenter> implements EditMyClubContract.View {

    private ClubDto mClubDto;

    @BindView(R.id.tv_upload_logo)
    TextView tvUploadLogo;

    @BindView(R.id.rv_club_introduce)
    RecyclerView rvClubIntroduce;

    @BindView(R.id.iv_upload_logo)
    ImageView ivUploadLogo;

    private int imgUploadPosition = -1;
    private int introduceImgPosition = -1;

    @BindView(R.id.iv_background)
    ImageView ivBackground;

    @BindView(R.id.et_club_name)
    EditText etClubName;
    @BindView(R.id.et_club_introduce)
    EditText etClubIntroduce;

    //上传图片
    @BindView(R.id.cpv_upload_logo)
    CircleProgressView cpvUploadLogo;

    private boolean isCreate = false;


    @Override
    protected int getContentView() {
        return R.layout.activity_edit_my_club;
    }

    @Override
    protected String getTitleName() {
        return "编辑俱乐部";
    }

    @Override
    public EditMyClubPresenter getmPresenter() {
        return new EditMyClubPresenter(this);
    }

    @Override
    protected void onCreate() {

        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMyClub();
            }
        });
        initClubIntroduceList();

        mClubDto = getIntent().getParcelableExtra("clubDto");
        if (mClubDto == null) {
            isCreate = true;
            mClubDto = new ClubDto();
        } else {
            setData();
        }

    }

    private void setData() {

        GlideUtils.loadCircleImage(ivUploadLogo, mClubDto.getClubLogo());
        GlideUtils.loadImage(ivBackground, mClubDto.getBackgroundImg());
        etClubName.setText(mClubDto.getClubName());
        etClubIntroduce.setText(mClubDto.getContent());

        tvUploadLogo.setVisibility(View.GONE);
        ivUploadLogo.setVisibility(View.VISIBLE);


        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
        List<ClubIntroduceEntity> clubIntroduceEntities = new ArrayList<>();
        List<ClubDto.ClubDetailBean> detailBeanList = mClubDto.getClubDetailList();
        if (detailBeanList != null && detailBeanList.size() > 0) {
            for (int i = 0; i < detailBeanList.size(); i++) {
                ClubIntroduceEntity entity = new ClubIntroduceEntity();
                entity.setIntroduceImgPaths(detailBeanList.get(i).getClubImageList());
                entity.setIntroduceContent(detailBeanList.get(i).getClubContent());
                if (i != detailBeanList.size() - 1) {
                    entity.setCanDelete(true);
                } else {
                    entity.setCanDelete(false);
                }
                clubIntroduceEntities.add(entity);
            }
            adapter.addData(clubIntroduceEntities);
        }

    }

    /**
     * 初始化 俱乐部 介绍 列表
     */
    private void initClubIntroduceList() {

        RVUtils.addDivideItemForRv(rvClubIntroduce);
        RVUtils.setLinearLayoutManage(rvClubIntroduce, LinearLayoutManager.VERTICAL);
        final ClubIntroduceAdapter adapter = new ClubIntroduceAdapter(this, R.layout.item_rv_edit_club_introduce);
        adapter.setType(0);
        rvClubIntroduce.setAdapter(adapter);
    }


    /**
     * 上传俱乐部Logo
     *
     * @param view
     */
    @OnClick({R.id.tv_upload_logo, R.id.iv_upload_logo})
    public void onClickUploadLogo(View view) {
        imgUploadPosition = 1;
        PermissionGen.with(EditMyClubActivity.this)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    /**
     * 去编辑图文详情
     * @param view
     */
    @OnClick(R.id.btn_create_content)
    public void toEditImageText(View view){
        toNextActivity(EditClubImageTextInfoActivity.class);
    }

    @OnClick(R.id.iv_background)
    public void onClickUploadCludBackground(View view) {
        imgUploadPosition = 2;
        PermissionGen.with(EditMyClubActivity.this)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();
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
                            isCrop = true;
                        } else {
                            isCrop = false;
                        }
                        ImageUtils.getImageFromCamera(EditMyClubActivity.this, isCrop, selectImageListener);
                        break;
                    case 1:
                        ImageUtils.ImageType imageType;
                        if (imgUploadPosition == 1) {
                            imageType = ImageUtils.ImageType.PROTRAIT;
                        } else {
                            imageType = ImageUtils.ImageType.PRODUCT;
                        }
                        ImageUtils.getImageFromPhotoAlbum(EditMyClubActivity.this,
                                imageType,
                                ImageUtils.ImageNumber.SINGLE,
                                null,
                                selectImageListener);
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
                ivUploadLogo.setVisibility(View.VISIBLE);
                GlideUtils.loadCircleImage(ivUploadLogo, imagePath);
            } else if (imgUploadPosition == 2) {
                GlideUtils.loadImage(ivBackground, imagePath);
            } else if (imgUploadPosition == 3) {
                if (introduceImgPosition < 0) {
                    return;
                }
//                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
//                ClubIntroduceEntity entity = adapter.getItemData(introduceImgPosition);
//                entity.setIntroduceImgPath(imagePath);
//                adapter.notifyItemDataChanged(introduceImgPosition);
            }
//            mPresenter.uploadClubImg(imagePath, imgUploadPosition, introduceImgPosition);
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
                        mPresenter.uploadClubImg(s, imgUploadPosition, introduceImgPosition);
                    }
                });

    }

    /**
     * 编辑完 图文详情 返回的结果
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditImageText(ImageTextInfoEvent event){
        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
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


    /**
     * 保存俱乐部信息
     */
    private void createMyClub() {
        mClubDto.setClubName(etClubName.getText().toString());
        mClubDto.setContent(etClubIntroduce.getText().toString());


        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
        List<ClubDto.ClubDetailBean> clubDetailBeens = new ArrayList<>();
        for (ClubIntroduceEntity entity : adapter.getAllData()) {
            ClubDto.ClubDetailBean detailBean = new ClubDto.ClubDetailBean();
            if (entity.getIntroduceImgPaths() != null){
                StringBuffer imageUrls = new StringBuffer();
                for (int i = 0; i < entity.getIntroduceImgPaths().size(); i++){
                    if (i > 0){
                        imageUrls.append("#");
                    }
                    imageUrls.append(entity.getIntroduceImgPaths().get(i));
                }
                detailBean.setClubImage(imageUrls.toString());
            } else {
                detailBean.setClubImage("");
            }

            detailBean.setClubContent(entity.getIntroduceContent());
            clubDetailBeens.add(detailBean);
        }
        if (mClubDto.getClubDetailList() != null) {
            mClubDto.getClubDetailList().clear();
        }
        mClubDto.setClubDetailList(clubDetailBeens);

        if (mClubDto.isEmpty()) {
            showToast("请将俱乐部信息补充完整");
            return;
        }

        if (isCreate) {
            mPresenter.createMyClub(mClubDto);
        } else {
            mPresenter.editMyClub(mClubDto);
        }

    }

    @Override
    public void createMyClubSuccess() {
        EventBusUtils.post(new RefreshClubInfoEvent());
        toNextActivity(ClubInfoActivity.class);
        finish();
    }


    @Override
    public void imageUploadSuccess(String imageUrl, int imageType) {
        Logger.d("imageurl = " + imageUrl);
        if (imageType == 1) {
            mClubDto.setClubLogo(imageUrl);
        } else if (imageType == 2) {
            mClubDto.setBackgroundImg(imageUrl);
        } else if (imageType == 3) {
            if (introduceImgPosition < 0) {
                return;
            }
//            ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
//            ClubIntroduceEntity entity = adapter.getItemData(introduceImgPosition);
//            entity.setIntroduceImgPath(imageUrl);
        }
    }

    @Override
    public void showImageUploadProgress(int imageType, int position, int progress) {
        switch (imageType) {
            case 1:
                cpvUploadLogo.setProgress(progress);
                break;
            case 2:
                break;
            case 3:
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
                adapter.setProgress(position, progress);
                break;
        }
    }
}
