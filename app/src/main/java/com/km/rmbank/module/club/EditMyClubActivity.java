package com.km.rmbank.module.club;

import android.Manifest;
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
import com.km.rmbank.module.personal.userinfo.UserInfoActivity;
import com.km.rmbank.ui.CircleProgressView;
import com.km.rmbank.utils.fileupload.FileUploadingListener;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.ui.KeyboardLayout;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.SoftKeyInputHidWidget;
import com.ps.androidlib.utils.StatusBarUtil;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        if (mClubDto == null){
            isCreate = true;
            mClubDto = new ClubDto();
        } else {
            setData();
        }

    }

    private void setData(){

        GlideUtils.loadCircleImage(ivUploadLogo,mClubDto.getClubLogo());
        GlideUtils.loadImage(ivBackground,mClubDto.getBackgroundImg());
        etClubName.setText(mClubDto.getClubName());
        etClubIntroduce.setText(mClubDto.getContent());

        tvUploadLogo.setVisibility(View.GONE);
        ivUploadLogo.setVisibility(View.VISIBLE);



        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
        List<ClubIntroduceEntity> clubIntroduceEntities = new ArrayList<>();
        List<ClubDto.ClubDetailBean> detailBeanList = mClubDto.getClubDetailList();
        for (int i = 0; i < detailBeanList.size(); i++){
            ClubIntroduceEntity entity = new ClubIntroduceEntity();
            entity.setIntroduceImgPath(detailBeanList.get(i).getClubImage());
            entity.setIntroduceContent(detailBeanList.get(i).getClubContent());
            if (i != detailBeanList.size() - 1){
                entity.setCanDelete(true);
            } else {
                entity.setCanDelete(false);
            }
            clubIntroduceEntities.add(entity);
        }
        adapter.addData(clubIntroduceEntities);
    }

    /**
     * 初始化 俱乐部 介绍 列表
     */
    private void initClubIntroduceList() {

        RVUtils.addDivideItemForRv(rvClubIntroduce);
        RVUtils.setLinearLayoutManage(rvClubIntroduce, LinearLayoutManager.VERTICAL);
        final ClubIntroduceAdapter adapter = new ClubIntroduceAdapter(this,R.layout.item_rv_edit_club_introduce);
        rvClubIntroduce.setAdapter(adapter);

        adapter.setOnClickAddOrDeleteListener(new ClubIntroduceAdapter.OnClickAddOrDeleteListener() {

            @Override
            public void addClubIntroduce(ClubIntroduceEntity curClubIntroduce, int position) {
                curClubIntroduce.setCanDelete(true);
                adapter.addData(new ClubIntroduceEntity());
                adapter.notifyDataChanged();
                Logger.d(adapter.getAllData().toString());
//                rvClubIntroduce.getLayoutManager().scrollToPosition(position+1);
            }

            @Override
            public void deleteClubIntroduce(final int position) {
                DialogUtils.showDefaultAlertDialog("是否删除该项介绍？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        adapter.removeData(adapter.getItemData(position));
                        adapter.notifyDataChanged();
                    }
                });

            }
        });

        adapter.setOnClickUploadIntroduceImgListener(new ClubIntroduceAdapter.OnClickUploadIntroduceImgListener() {
            @Override
            public void clickUploadImg(int position) {
                introduceImgPosition = position;
                imgUploadPosition = 3;
                PermissionGen.with(EditMyClubActivity.this)
                        .addRequestCode(1)
                        .permissions(Manifest.permission.CAMERA)
                        .request();
            }

        });
        adapter.addData(new ClubIntroduceEntity());

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
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
                ClubIntroduceEntity entity = adapter.getItemData(introduceImgPosition);
                entity.setIntroduceImgPath(imagePath);
                adapter.notifyItemDataChanged(introduceImgPosition);
            }
//            mPresenter.uploadProtrait(photoList.get(0));
            Logger.d("--------------------- selectImageListener   uploadClubImg ---------------------");
            mPresenter.uploadClubImg(imagePath, imgUploadPosition,introduceImgPosition);
        }
    };


    /**
     * 保存俱乐部信息
     */
    private void createMyClub(){
        mClubDto.setClubName(etClubName.getText().toString());
        mClubDto.setContent(etClubIntroduce.getText().toString());


        ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
        List<ClubDto.ClubDetailBean> clubDetailBeens = new ArrayList<>();
        for (ClubIntroduceEntity entity : adapter.getAllData()){
            ClubDto.ClubDetailBean detailBean = new ClubDto.ClubDetailBean();
            detailBean.setClubImage(entity.getIntroduceImgPath());
            detailBean.setClubContent(entity.getIntroduceContent());
            clubDetailBeens.add(detailBean);
        }
        if (mClubDto.getClubDetailList() != null){
            mClubDto.getClubDetailList().clear();
        }
        mClubDto.setClubDetailList(clubDetailBeens);

        if (mClubDto.isEmpty()){
            showToast("请将俱乐部信息补充完整");
            return;
        }

        if (isCreate){
            mPresenter.createMyClub(mClubDto);
        } else {
            mPresenter.editMyClub(mClubDto);
        }

    }

    @Override
    public void createMyClubSuccess() {
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
            ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
            ClubIntroduceEntity entity = adapter.getItemData(introduceImgPosition);
            entity.setIntroduceImgPath(imageUrl);
        }
    }

    @Override
    public void showImageUploadProgress(int imageType, int position, int progress) {
        switch (imageType){
            case 1:
                cpvUploadLogo.setProgress(progress);
                break;
            case 2:
                break;
            case 3:
                ClubIntroduceAdapter adapter = (ClubIntroduceAdapter) rvClubIntroduce.getAdapter();
                adapter.setProgress(position,progress);
                break;
        }
    }
}
