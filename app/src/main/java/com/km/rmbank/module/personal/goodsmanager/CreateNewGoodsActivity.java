package com.km.rmbank.module.personal.goodsmanager;

import android.Manifest;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.AddImageAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.entity.ImageEntity;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.GlideUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class CreateNewGoodsActivity extends BaseActivity {

    private @IdRes int selectWidgetId = 0;

    private List<String> bannerPathList;
    @BindView(R.id.rv_banner)
    RecyclerView mrvBanner;

    private List<String> goodsDetailPathList;
    @BindView(R.id.rv_goods_detail)
    RecyclerView mrvGoodsDetail;

    @BindView(R.id.iv_action1)
    ImageView ivAction1;
    @BindView(R.id.iv_action2)
    ImageView ivAction2;
    @BindView(R.id.iv_action3)
    ImageView ivAction3;



    @Override
    protected int getContentView() {
        return R.layout.activity_create_new_goods;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "编辑商品";
    }

    @Override
    protected void onCreate() {
        bannerPathList = new ArrayList<>();
        goodsDetailPathList = new ArrayList<>();
        initBannerRecyclerView();
        initGoodsDetailRecyclerView();
    }

    /**
     * banner图片选择
     */
    private void initBannerRecyclerView(){
        RVUtils.setGridLayoutManage(mrvBanner,3);
        RVUtils.addDivideItemForRv(mrvBanner,RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.HORIZONTAL,5);
        RVUtils.addDivideItemForRv(mrvBanner,RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.VERTICAL,5);
        AddImageAdapter addImageAdapter = new AddImageAdapter(this);
        addImageAdapter.setmExistFooterView(true);
        mrvBanner.setAdapter(addImageAdapter);
        addImageAdapter.setAddImageListener(new AddImageAdapter.onClickAddImageListener() {
            @Override
            public void addImage() {
                selectImage(mrvBanner.getId());
            }
        });
    }

    /**
     * 详情图片选择
     */
    private void initGoodsDetailRecyclerView(){
        RVUtils.setGridLayoutManage(mrvGoodsDetail,3);
        RVUtils.addDivideItemForRv(mrvGoodsDetail,RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.HORIZONTAL,5);
        RVUtils.addDivideItemForRv(mrvGoodsDetail,RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.VERTICAL,5);
        AddImageAdapter addImageAdapter = new AddImageAdapter(this);
        addImageAdapter.setmExistFooterView(true);
        mrvGoodsDetail.setAdapter(addImageAdapter);
        addImageAdapter.setAddImageListener(new AddImageAdapter.onClickAddImageListener() {
            @Override
            public void addImage() {
                selectImage(mrvGoodsDetail.getId());
            }
        });
    }

    @OnClick({R.id.iv_action1,R.id.iv_action2,R.id.iv_action3})
    public void selectActionImage(View view){
        selectImage(view.getId());
    }

    private void selectImage(@IdRes int id){
        selectWidgetId = id;
        PermissionGen.needPermission(CreateNewGoodsActivity.this,1, Manifest.permission.CAMERA);
    }


    @PermissionSuccess(requestCode = 1)
    public void getCarmeraPermissionSuccess(){
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i){
                    case 0://拍照
                        ImageUtils.getImageFromCamera(CreateNewGoodsActivity.this,false,selectImageListener);
                        break;
                    case 1://我的相册
                        //默认多选
                        ImageUtils.ImageNumber number = ImageUtils.ImageNumber.MULTIPLE;
                        if (selectWidgetId != R.id.rv_banner && selectWidgetId != R.id.rv_goods_detail){//单选
                            number = ImageUtils.ImageNumber.SINGLE;
                        }
                        ImageUtils.getImageFromPhotoAlbum(CreateNewGoodsActivity.this,
                                ImageUtils.ImageType.PRODUCT,
                                number,
                                null,
                                selectImageListener);
                        break;
                }
            }
        });
    }
    @PermissionFail(requestCode = 1)
    public void getCameraPermissionFail(){
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener =  new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            selectImageResult(photoList);
        }
    };

    private void selectImageResult(List<String> photoList){
        switch (selectWidgetId){
            case R.id.rv_banner:
                bannerPathList.addAll(photoList);
                AddImageAdapter adapter = (AddImageAdapter) mrvBanner.getAdapter();
                List<ImageEntity> imageEntities = new ArrayList<>();
                for (String path : bannerPathList){
                    imageEntities.add(new ImageEntity(path));
                }
                adapter.addData(imageEntities);
                break;
            case R.id.rv_goods_detail:
                goodsDetailPathList.addAll(photoList);
                AddImageAdapter adapter1 = (AddImageAdapter) mrvGoodsDetail.getAdapter();
                List<ImageEntity> imageEntities1 = new ArrayList<>();
                for (String path : goodsDetailPathList){
                    imageEntities1.add(new ImageEntity(path));
                }
                adapter1.addData(imageEntities1);
                break;
            case R.id.iv_action1:
                GlideUtils.loadImage(ivAction1,photoList.get(0));
                break;
            case R.id.iv_action2:
                GlideUtils.loadImage(ivAction2,photoList.get(0));
                break;
            case R.id.iv_action3:
                GlideUtils.loadImage(ivAction3,photoList.get(0));
                break;
        }
    }
}
