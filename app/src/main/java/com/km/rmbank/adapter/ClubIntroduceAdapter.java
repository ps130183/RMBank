package com.km.rmbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.basic.RVUtils;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.event.ClubIntroduceEntity;
import com.km.rmbank.module.club.EditClubImageTextInfoActivity;
import com.km.rmbank.ui.CircleProgressView;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.MToast;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by kamangkeji on 17/7/4.
 */

public class ClubIntroduceAdapter extends BaseAdapter<ClubIntroduceEntity> implements BaseAdapter.IAdapter<ClubIntroduceAdapter.ViewHolder> {

    private int type; //0:俱乐部  1：近期活动  2：往期活动

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

        if (type != 2){
            if (entity.getIntroduceImgPaths() == null || entity.getIntroduceImgPaths().size() == 0){
                holder.rvImage.setVisibility(View.GONE);
            } else {
                holder.rvImage.setVisibility(View.VISIBLE);
            }
            holder.adapter.addData(entity.getIntroduceImgPaths());
        } else {
            holder.ivPortrait.setVisibility(View.VISIBLE);
            GlideUtils.loadCircleImage(holder.ivPortrait,entity.getIntroduceImgPaths().get(0));
        }
        holder.tvContent.setText(entity.getIntroduceContent());

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDefaultAlertDialog("是否要删除该项数据", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        removeData(entity);
                    }
                });
            }
        });

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditClubImageTextInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putParcelable("imageTextData",entity);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }


    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_edit)
        TextView tvEdit;

        @BindView(R.id.rv_image)
        RecyclerView rvImage;
        ImageTextAdapter adapter;

        @BindView(R.id.tv_content)
        TextView tvContent;

        ImageView ivPortrait;


        public ViewHolder(View itemView) {
            super(itemView);
            ivPortrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            initRv();
        }

        private void initRv(){
            RVUtils.setLinearLayoutManage(rvImage, LinearLayoutManager.VERTICAL);
            RVUtils.addDivideItemForRv(rvImage,RVUtils.DIVIDER_COLOR_WHITE);
            adapter = new ImageTextAdapter(mContext);
            rvImage.setAdapter(adapter);
        }

    }


    public void setProgress(int position, int progress){
        ClubIntroduceEntity entity = getItemData(position);
        entity.setProgress(progress);
        notifyItemChanged(position);
    }

    public void setType(int type) {
        this.type = type;
    }
}
