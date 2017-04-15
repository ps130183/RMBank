package com.km.rmbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.entity.ImageEntity;
import com.km.rmbank.ui.CircleProgressView;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.glide.GlideUtils;
import com.ps.androidlib.utils.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/29.
 */

public class AddImageAdapter extends BaseAdapter<ImageEntity> implements BaseAdapter.IAdapter<AddImageAdapter.ViewHolder> {


    private onClickAddImageListener addImageListener;
    private OnclickDeleteImageListener onclickDeleteImageListener;

    public AddImageAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_image);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, final int position) {
        final ImageEntity entity = getItemData(position);
        GlideUtils.loadImage(holder.ivImage,entity.getImagePath());
        holder.circleProgress.setProgress(entity.getProgress());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDefaultAlertDialog("是否要删除该图片？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        removeData(entity);
                        onclickDeleteImageListener.clickDelete(position);
                    }
                });
            }
        });
    }

    @Override
    protected BaseFooterViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(ViewUtils.getView(inflater,parent,R.layout.item_rv_addimage));
    }

    class ViewHolder extends BaseViewHolder{
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.circleProgress)
        CircleProgressView circleProgress;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterViewHolder extends BaseFooterViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        @OnClick(R.id.iv_addimage)
        public void addImage(View view){
            if (addImageListener != null){
                addImageListener.addImage();
            }
        }

    }

    public interface onClickAddImageListener{
        void addImage();
    }

    public void setAddImageListener(onClickAddImageListener addImageListener) {
        this.addImageListener = addImageListener;
    }

    public interface OnclickDeleteImageListener{
        void clickDelete(int position);
    }

    public OnclickDeleteImageListener getOnclickDeleteImageListener() {
        return onclickDeleteImageListener;
    }

    public void setOnclickDeleteImageListener(OnclickDeleteImageListener onclickDeleteImageListener) {
        this.onclickDeleteImageListener = onclickDeleteImageListener;
    }

    /**
     * 刷新图片上传进度
     * @param position
     * @param progress
     */
    public void setProgress(int position,int progress){
        ImageEntity imageEntity = getItemData(position);
        imageEntity.setProgress(progress);
        notifyItemChanged(position);
    }
}
