package com.km.rmbank.module.club;

import android.Manifest;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ForumImageAdapter;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.event.ClubIntroduceEntity;
import com.km.rmbank.event.ImageTextInfoEvent;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.module.personal.forum.ReleaseForumActivity;
import com.ps.androidlib.event.CompressImageEvent;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.imageselector.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class EditClubImageTextInfoActivity extends BaseActivity<EditClubImageTextInfoPresenter> implements EditClubImageTextInfoContract.View {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.et_rule_content)
    EditText etRuleContent;

    @BindView(R.id.rv_forum_image)
    RecyclerView rvForumImage;

    private int imgPosition;

    private int position;
    private ClubIntroduceEntity mClubIntroduceEntity;

    private boolean isActionRecent = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_club_image_text_info;
    }

    @Override
    protected String getTitleName() {
        return "新增图文介绍";
    }

    @Override
    public EditClubImageTextInfoPresenter getmPresenter() {
        return new EditClubImageTextInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textContent = etRuleContent.getText().toString();
                ForumImageAdapter adapter = (ForumImageAdapter) rvForumImage.getAdapter();
                List<String> imageList = adapter.getAllData();
                if (!isActionRecent && imageList != null && imageList.size() > 0 && imageList.size() < 3){
                    imageList = imageList.subList(0,imageList.size() - 1);
                }
                if (TextUtils.isEmpty(textContent) && (imageList == null || imageList.size() == 0)){
                    showToast("请至少编辑一项内容");
                    return;
                }
                ImageTextInfoEvent event = new ImageTextInfoEvent(textContent,imageList);
                event.setPosition(position);
                EventBusUtils.post(event);
                finish();
            }
        });
        initRecyclerImage();
        position = getIntent().getIntExtra("position",-1);
        mClubIntroduceEntity = getIntent().getParcelableExtra("imageTextData");
        isActionRecent = getIntent().getBooleanExtra("isActionRecent",false);
        if (isActionRecent){
            mTitle.setText("新增特邀嘉宾");
            etRuleContent.setHint("请编辑嘉宾介绍");
        }
        if (position >= 0){
            setData();
        }
    }

    /**
     * 初始化 图片选择
     */
    private void initRecyclerImage() {
        RVUtils.setGridLayoutManage(rvForumImage, 3);
        final ForumImageAdapter adapter = new ForumImageAdapter(this);
        adapter.addData("");
        rvForumImage.setAdapter(adapter);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<String>() {
            @Override
            public void onItemClick(final String itemData, int position) {
                if (TextUtils.isEmpty(itemData)) {
                    imgPosition = position;
                    openCamera();
                } else {
                    DialogUtils.showDefaultAlertDialog("是否要删除这张图片？", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            int count = adapter.getItemCount();
                            if ((count == 3 && !"".equals(adapter.getItemData(2))) || isActionRecent) {
                                adapter.addData("");
                            }
                            adapter.removeData(itemData);
                        }
                    });
                }
            }
        });
    }

    private void setData(){
        etRuleContent.setText(mClubIntroduceEntity.getIntroduceContent());
        ForumImageAdapter adapter = (ForumImageAdapter) rvForumImage.getAdapter();
        if (mClubIntroduceEntity.getIntroduceImgPaths() != null){
            adapter.addData(mClubIntroduceEntity.getIntroduceImgPaths());
            if (mClubIntroduceEntity.getIntroduceImgPaths().size() < 3){
                adapter.addData("");
            }
        }
    }

    /**
     * 打开选择 图片弹出框
     */
    private void openCamera() {
        PermissionGen.with(EditClubImageTextInfoActivity.this)
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
                        ImageUtils.getImageFromCamera(EditClubImageTextInfoActivity.this, false, selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(EditClubImageTextInfoActivity.this,
                                ImageUtils.ImageType.PRODUCT,
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
            EventBusUtils.post(new UploadImageEvent(imagePath));

        }


    };
    private Dialog compressImageDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void compressImageing(CompressImageEvent event) {
        if (compressImageDialog == null) {
            compressImageDialog = DialogUtils.showLoadingDialog("正在上传图片，请稍后。。。");
        } else {
            compressImageDialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(final UploadImageEvent event) {
        com.ps.androidlib.utils.ImageUtils.compressImage(event.getImagePath())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mPresenter.uploadImage(s, imgPosition);
                    }
                });

    }

    @Override
    public void showImage(String imageUrl, int position) {
        compressImageDialog.dismiss();
        ForumImageAdapter adapter = (ForumImageAdapter) rvForumImage.getAdapter();
//        adapter.setImageUrl(imageUrl,position);
        adapter.addDataOnFirst(imageUrl);
        if (adapter.getItemCount() > 3 || isActionRecent) {
            adapter.removeData("");
        }
    }
}
