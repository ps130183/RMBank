package com.km.rmbank.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.event.ClubIntroduceEntity;
import com.km.rmbank.ui.CircleProgressView;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.MToast;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by kamangkeji on 17/7/4.
 */

public class ClubIntroduceAdapter extends BaseAdapter<ClubIntroduceEntity> implements BaseAdapter.IAdapter<ClubIntroduceAdapter.ViewHolder> {

    private OnClickAddOrDeleteListener onClickAddOrDeleteListener;
    private OnClickUploadIntroduceImgListener onClickUploadIntroduceImgListener;

    public ClubIntroduceAdapter(Context mContext,int layoutRes) {
        super(mContext, layoutRes);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, final int position) {
        final ClubIntroduceEntity entity = getItemData(position);

//        if (entity.isEmpty()){
//            holder.btnAddDelete.setBackgroundResource(R.drawable.shape_edit_club_button_notclick);
//        } else {
//            holder.btnAddDelete.setBackgroundResource(R.drawable.shape_edit_club_button);
//        }

        if (!TextUtils.isEmpty(entity.getIntroduceImgPath())){
            GlideUtils.loadImage(holder.ivUploadIntroduceImg,entity.getIntroduceImgPath());
        } else {
            GlideUtils.loadImage(holder.ivUploadIntroduceImg,R.mipmap.ic_edit_club_upload_img);
        }

        if (holder.cpvUploadImg != null){
            if (entity.getProgress() > 0){
                holder.cpvUploadImg.setProgress(entity.getProgress());
            } else {
                holder.cpvUploadImg.setProgress(0);
            }
        }

        holder.etIntroduce.setText(TextUtils.isEmpty(entity.getIntroduceContent()) ? "" : entity.getIntroduceContent());

        String btnTitle = "";
        if (entity.isCanDelete()){
            btnTitle = "删除";
        } else {
            btnTitle = "新增";
        }
        if (entity.isCanDelete()){
            holder.etIntroduce.setFocusable(false);
            holder.etIntroduce.setFocusableInTouchMode(false);
        } else {
            holder.etIntroduce.setFocusable(true);
            holder.etIntroduce.setFocusableInTouchMode(true);
        }
        holder.btnAddDelete.setText(btnTitle);
        holder.btnAddDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.isCanDelete()){//删除
                    onClickAddOrDeleteListener.deleteClubIntroduce(position);
                } else {//新增
//                    entity.setIntroduceContent(holder.etIntroduce.getText().toString());
                    if (entity.isEmpty()){
                        MToast.showToast(mContext,"请将信息补充完整");
                        return;
                    }
                    if (entity.getIntroduceImgPath().indexOf("http:") < 0){
                        MToast.showToast(mContext,"正在上传图片，请稍后。。。");
                        return;
                    }
                    entity.setCanDelete(true);
                    int itemPosition = addData(new ClubIntroduceEntity());
                    if (itemPosition > 0){
                        notifyItemChanged(itemPosition - 1);
                    }

//                    onClickAddOrDeleteListener.addClubIntroduce(entity,position);
                }
            }
        });

        holder.ivUploadIntroduceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUploadIntroduceImgListener.clickUploadImg(position);
            }
        });

        holder.etIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && !entity.isCanDelete()){
                    Logger.d("position = " + position + " ss = " + s.toString() + "   etIntroduce = " + holder.etIntroduce.getText().toString());
                    entity.setIntroduceContent(s.toString());
                }
//                notifyItemDataChanged(position,"addOrDelete");
//                Logger.d("position  === " + position + "   data : "+ entity.toString());
            }
        });
    }


    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.btn_add_delete)
        Button btnAddDelete;
        @BindView(R.id.iv_upload_introduce_img)
        ImageView ivUploadIntroduceImg;
        @BindView(R.id.et_introduce)
        EditText etIntroduce;

        CircleProgressView cpvUploadImg;

        public ViewHolder(View itemView) {
            super(itemView);
            cpvUploadImg = (CircleProgressView) itemView.findViewById(R.id.cpv_upload_introduce_img);
        }
    }


    public void setProgress(int position, int progress){
        ClubIntroduceEntity entity = getItemData(position);
        entity.setProgress(progress);
        notifyItemChanged(position);
    }




    public interface OnClickAddOrDeleteListener{
        void addClubIntroduce(ClubIntroduceEntity curClubIntroduce,int position);
        void deleteClubIntroduce(int position);
    }

    public void setOnClickAddOrDeleteListener(OnClickAddOrDeleteListener onClickAddOrDeleteListener) {
        this.onClickAddOrDeleteListener = onClickAddOrDeleteListener;
    }

    public interface OnClickUploadIntroduceImgListener{
        void clickUploadImg(int position);
    }

    public void setOnClickUploadIntroduceImgListener(OnClickUploadIntroduceImgListener onClickUploadIntroduceImgListener) {
        this.onClickUploadIntroduceImgListener = onClickUploadIntroduceImgListener;
    }
}
