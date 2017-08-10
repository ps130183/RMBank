package com.km.rmbank.module.personal.goodsmanager;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.AddImageAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.entity.ImageEntity;
import com.km.rmbank.event.GoodsTypeEvent;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.ui.CircleProgressView;
import com.km.rmbank.utils.InputFilterUtils;
import com.ps.androidlib.event.CompressImageEvent;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class CreateNewGoodsActivity extends BaseActivity<CreateNewGoodsPresenter> implements CreateNewGoodsContract.View {

    private
    @IdRes
    int selectWidgetId = 0;

    private List<String> bannerPathList;
    private StringBuffer bannerPathBuf;
    @BindView(R.id.rv_banner)
    RecyclerView mrvBanner;

    private List<String> goodsDetailPathList;
    private StringBuffer goodsDetailPathBuf;
    @BindView(R.id.rv_goods_detail)
    RecyclerView mrvGoodsDetail;

    @BindView(R.id.iv_action1)
    ImageView ivAction1;
    @BindView(R.id.cpro_action1)
    CircleProgressView cproAction1;

    @BindView(R.id.iv_action2)
    ImageView ivAction2;
    @BindView(R.id.cpro_action2)
    CircleProgressView cproAction2;

    @BindView(R.id.iv_action3)
    ImageView ivAction3;
    @BindView(R.id.cpro_action3)
    CircleProgressView cproAction3;

    private String actionUrl1;
    private String actionUrl2;
    private String actionUrl3;


    @BindView(R.id.et_goods_type)
    EditText etGoodsType;
    private HomeGoodsTypeDto levelOneGoodsTypeDto;
    private HomeGoodsTypeDto levelTwoGoodsType;

    //价格、运费
    @BindView(R.id.et_goods_price)
    EditText etGoodsPrice;
    @BindView(R.id.et_frieght)
    EditText etFrieght;
    @BindView(R.id.et_frieght_add)
    EditText etFrieghtAdd;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_sub_title)
    EditText etSubTitle;

    @BindView(R.id.btn_create_new_goods)
    Button btnCreateNewGoods;

    private String mProductNo;

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
    public CreateNewGoodsPresenter getmPresenter() {
        return new CreateNewGoodsPresenter(this);
    }

    @Override
    protected void onCreate() {
        mProductNo = getIntent().getStringExtra("productNo");
        bannerPathList = new ArrayList<>();
        goodsDetailPathList = new ArrayList<>();
        bannerPathBuf = new StringBuffer();
        goodsDetailPathBuf = new StringBuffer();
        initImageWidget();
        initPriceFrieght();
        if (!TextUtils.isEmpty(mProductNo)){
            mPresenter.getGoodsInfo(mProductNo);
            btnCreateNewGoods.setText("保存修改");
        }
    }

    private void initImageWidget() {
        initBannerRecyclerView();
        initGoodsDetailRecyclerView();
    }

    /**
     * banner图片选择
     */
    private void initBannerRecyclerView() {
        RVUtils.setGridLayoutManage(mrvBanner, 3);
        RVUtils.addDivideItemForRv(mrvBanner, RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.HORIZONTAL, 5);
        RVUtils.addDivideItemForRv(mrvBanner, RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.VERTICAL, 5);
        AddImageAdapter addImageAdapter = new AddImageAdapter(this);
        addImageAdapter.setmExistFooter(true);
        addImageAdapter.setmExistEmptyView(false);
        mrvBanner.setAdapter(addImageAdapter);
        addImageAdapter.setAddImageListener(new AddImageAdapter.onClickAddImageListener() {
            @Override
            public void addImage() {
                selectImage(mrvBanner.getId());
            }
        });
        addImageAdapter.setOnclickDeleteImageListener(new AddImageAdapter.OnclickDeleteImageListener() {
            @Override
            public void clickDelete(int position) {
                bannerPathList.remove(position);
            }
        });
    }

    /**
     * 详情图片选择
     */
    private void initGoodsDetailRecyclerView() {
        RVUtils.setGridLayoutManage(mrvGoodsDetail, 3);
        RVUtils.addDivideItemForRv(mrvGoodsDetail, RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.HORIZONTAL, 5);
        RVUtils.addDivideItemForRv(mrvGoodsDetail, RVUtils.DIVIDER_COLOR_WHITE, GridLayoutManager.VERTICAL, 5);
        AddImageAdapter addImageAdapter = new AddImageAdapter(this);
        addImageAdapter.setmExistFooter(true);
        addImageAdapter.setmExistEmptyView(false);
        mrvGoodsDetail.setAdapter(addImageAdapter);
        addImageAdapter.setAddImageListener(new AddImageAdapter.onClickAddImageListener() {
            @Override
            public void addImage() {
                selectImage(mrvGoodsDetail.getId());
            }
        });
        addImageAdapter.setOnclickDeleteImageListener(new AddImageAdapter.OnclickDeleteImageListener() {
            @Override
            public void clickDelete(int position) {
                goodsDetailPathList.remove(position);
            }
        });
    }

    /**
     * 初始化价格和运费输入框
     */
    private void initPriceFrieght() {
        etGoodsPrice.setFilters(InputFilterUtils.filters2);
        etFrieght.setFilters(InputFilterUtils.filters2);
        etFrieghtAdd.setFilters(InputFilterUtils.filters2);
    }

    @OnClick({R.id.iv_action1, R.id.iv_action2, R.id.iv_action3})
    public void selectActionImage(View view) {
        selectImage(view.getId());
    }

    private void selectImage(@IdRes int id) {
        selectWidgetId = id;
        PermissionGen.needPermission(CreateNewGoodsActivity.this, 1, Manifest.permission.CAMERA);
    }


    @PermissionSuccess(requestCode = 1)
    public void getCarmeraPermissionSuccess() {
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i) {
                    case 0://拍照
                        ImageUtils.getImageFromCamera(CreateNewGoodsActivity.this, false, selectImageListener);
                        break;
                    case 1://我的相册
                        //默认多选
                        ImageUtils.ImageNumber number = ImageUtils.ImageNumber.SINGLE;
                        if (selectWidgetId != R.id.rv_banner && selectWidgetId != R.id.rv_goods_detail) {//单选
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
    public void getCameraPermissionFail() {
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(final List<String> photoList) {
            //将选择的图片加载到页面中 并返回位置
            Observable observable1 = Observable.fromIterable(photoList)
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<String, Integer>() {
                        @Override
                        public Integer apply(@io.reactivex.annotations.NonNull String imagePath) throws Exception {
                            return selectImageResult(imagePath);
                        }
                    });
            //获取选择的图片
            Observable observable2 = Observable.fromIterable(photoList).observeOn(AndroidSchedulers.mainThread());
            //将observable1获取的position  和 observable2 的每个图片的路径 合并，将图片上传到服务器
            Observable.zip(observable1, observable2, new BiFunction<Integer, String, Integer>() {
                @Override
                public Integer apply(@io.reactivex.annotations.NonNull Integer position, @io.reactivex.annotations.NonNull String imagePath) throws Exception {
                    EventBusUtils.post(new UploadImageEvent(imagePath));
//                    mPresenter.uploadImage(imagePath, selectWidgetId, imagePosition);
                    imagePosition = position;
                    return 0;
                }

            }).subscribeOn(Schedulers.io()).subscribe(new Consumer() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                    //无操作，只是启动被观察者
                }
            });

        }
    };

    private Dialog compressImageDialog;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void compressImageing(CompressImageEvent event){
        if (compressImageDialog == null){
            compressImageDialog = DialogUtils.showLoadingDialog("正在上传图片，请稍后。。。");
        } else {
            compressImageDialog.show();
        }
    }

    private int imagePosition = -1;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(UploadImageEvent event){
        com.ps.androidlib.utils.ImageUtils.compressImage(event.getImagePath())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {
                        compressImageDialog.dismiss();
                        mPresenter.uploadImage(s, selectWidgetId, imagePosition);
                    }
                });

    }

    /**
     * 将图片设置到页面中，并返回所在位置
     * @param imagePath
     * @return
     */
    private int selectImageResult(String imagePath) {
        int position = 0;
        switch (selectWidgetId) {
            case R.id.rv_banner://选择banner图片
                AddImageAdapter adapter = (AddImageAdapter) mrvBanner.getAdapter();
                position = adapter.addData(new ImageEntity(imagePath));
                break;
            case R.id.rv_goods_detail://选择商品详情图片
                AddImageAdapter adapter1 = (AddImageAdapter) mrvGoodsDetail.getAdapter();
                position = adapter1.addData(new ImageEntity(imagePath));
                break;
            case R.id.iv_action1://活动位置1
                GlideUtils.loadImage(ivAction1, imagePath);
                break;
            case R.id.iv_action2://活动位置2
                GlideUtils.loadImage(ivAction2, imagePath);
                break;
            case R.id.iv_action3://活动位置3
                GlideUtils.loadImage(ivAction3, imagePath);
                break;
        }
        return position;
    }

    /**
     * 去选择商品类型
     *
     * @param view
     */
    @OnClick(R.id.et_goods_type)
    public void selectGoodsType(View view) {
        Bundle bundle = new Bundle();
//        bundle.putParcelable("levelOneGoodsTypeDto", levelOneGoodsTypeDto);
        toNextActivityForResult(GoodsTypeActivity.class, 1000, bundle);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverGoodsType(GoodsTypeEvent event){
        levelOneGoodsTypeDto = event.getLevelOneType();
        levelTwoGoodsType = event.getLevelTwoType();
        if (levelTwoGoodsType != null){
            etGoodsType.setText(levelTwoGoodsType.getProductTypeName());
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 1000) {//商品类型
//            levelOneGoodsTypeDto = data.getParcelableExtra("levelOneGoodsTypeDto");
//            if (levelOneGoodsTypeDto != null) {
//                etGoodsType.setText(levelOneGoodsTypeDto.getProductTypeName());
//            }
//        }
//    }

    @Override
    public void showImageUploadResult(int photoType, String photoUrl) {
        switch (photoType) {
            case R.id.rv_banner://选择banner图片
                bannerPathList.add(photoUrl);
                break;
            case R.id.rv_goods_detail://选择商品详情图片
                goodsDetailPathList.add(photoUrl);
                break;
            case R.id.iv_action1://活动位置1
                actionUrl1 = photoUrl;
                break;
            case R.id.iv_action2://活动位置2
                actionUrl2 = photoUrl;
                break;
            case R.id.iv_action3://活动位置3
                actionUrl3 = photoUrl;
                break;
        }
    }

    @Override
    public void createNewGoodsSuccess() {
        if (TextUtils.isEmpty(mProductNo)){
            showToast("发布成功，请等待后台审核");
        } else {
            showToast("保存成功");
        }
        finish();
    }

    @Override
    public void showImageUploadingProgress(int photoType, int progress, int position) {
        switch (photoType) {
            case R.id.rv_banner://选择banner图片
                AddImageAdapter bannerAdapter = (AddImageAdapter) mrvBanner.getAdapter();
                bannerAdapter.setProgress(position, progress);
                break;
            case R.id.rv_goods_detail://选择商品详情图片
                AddImageAdapter goodsDetailAdapter = (AddImageAdapter) mrvGoodsDetail.getAdapter();
                goodsDetailAdapter.setProgress(position, progress);
                break;
            case R.id.iv_action1://活动位置1
                cproAction1.setProgress(progress);
                break;
            case R.id.iv_action2://活动位置2
                cproAction2.setProgress(progress);
                break;
            case R.id.iv_action3://活动位置3
                cproAction3.setProgress(progress);
                break;
        }
    }

    @Override
    public void showGoodsInfo(GoodsDetailsDto goodsDetailsDto) {
//        levelTwoGoodsType = goodsDetailsDto.getGoodsTypeDto();
        etGoodsType.setText(goodsDetailsDto.getProductType());
        etName.setText(goodsDetailsDto.getName());
        etSubTitle.setText(goodsDetailsDto.getSubtitle());
        etGoodsPrice.setText(goodsDetailsDto.getPrice());

        etFrieght.setText(goodsDetailsDto.getFreightInMaxCount());
        etFrieghtAdd.setText(goodsDetailsDto.getFreightInEveryAdd());

        bannerPathList.addAll(goodsDetailsDto.getProductBannerList());
        goodsDetailPathList.addAll(goodsDetailsDto.getProductDetailList());

        List<ImageEntity> bannerEntity = new ArrayList<>();
        for (String path : bannerPathList){
            bannerEntity.add(new ImageEntity(path));
        }
        AddImageAdapter bannerAdapter = (AddImageAdapter) mrvBanner.getAdapter();
        bannerAdapter.addData(bannerEntity);

        List<ImageEntity> detailEntity = new ArrayList<>();
        for (String path : goodsDetailPathList){
            detailEntity.add(new ImageEntity(path));
        }
        AddImageAdapter detailAdapter = (AddImageAdapter) mrvGoodsDetail.getAdapter();
        detailAdapter.addData(detailEntity);

        String[] actionUrls = goodsDetailsDto.getBannerUrl().split("#");
        actionUrl1 = actionUrls[0];
        actionUrl2 = actionUrls[1];
        actionUrl3 = actionUrls[2];

        GlideUtils.loadImage(ivAction1, actionUrl1);
        GlideUtils.loadImage(ivAction2, actionUrl2);
        GlideUtils.loadImage(ivAction3, actionUrl3);
    }

    /**
     * 将多张图片地址 转换成 字符串 并用#号分割
     *
     * @param photoList
     * @return
     */
    private String getImageUrl(List<String> photoList) {
        StringBuffer buffer = new StringBuffer();

        for (String photo : photoList) {
            buffer.append(photo).append("#");
        }
        if (buffer.length() > 1) {
            buffer.replace(buffer.length() - 1, buffer.length(), "");
        }

        return buffer.toString();
    }

    @OnClick(R.id.btn_create_new_goods)
    public void createNewGoods(View view) {
        GoodsDetailsDto goodsDetailsDto = new GoodsDetailsDto();
        goodsDetailsDto.setName(etName.getText().toString());
        goodsDetailsDto.setSubtitle(etSubTitle.getText().toString());
        goodsDetailsDto.setPrice(etGoodsPrice.getText().toString());
        goodsDetailsDto.setFreightInMaxCount(etFrieght.getText().toString());
        goodsDetailsDto.setFreightInEveryAdd(etFrieghtAdd.getText().toString());
        goodsDetailsDto.setProductBannerUrl(getImageUrl(bannerPathList));
        goodsDetailsDto.setProductDetail(getImageUrl(goodsDetailPathList));
        goodsDetailsDto.setIsInIndexActivity(levelTwoGoodsType.getId());
        if (TextUtils.isEmpty(actionUrl1) || TextUtils.isEmpty(actionUrl2) || TextUtils.isEmpty(actionUrl3)) {
            showToast("请上传活动图片");
            return;
        }
        goodsDetailsDto.setBannerUrl(actionUrl1 + "#" + actionUrl2 + "#" + actionUrl3);
        if (goodsDetailsDto.isEmpty()) {
            showToast("请将商品的信息补充完整");
            return;
        }
        if (TextUtils.isEmpty(mProductNo)){
            mPresenter.createNewGoods(goodsDetailsDto);
        } else {
            goodsDetailsDto.setProductNo(mProductNo);
            mPresenter.updateGoodsInfo(goodsDetailsDto);
        }

    }
}
