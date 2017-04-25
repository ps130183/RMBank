package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.IndustryDto;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/29.
 */

public class IndustrySubAdapter extends BaseAdapter<IndustryDto> implements BaseAdapter.IAdapter<IndustrySubAdapter.ViewHolder> {

    private onSubCheckedListener onSubCheckedListener;

    public IndustrySubAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_resource_sub);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
        if (payloads == null || payloads.isEmpty()){
            onBindViewHolder(holder,position);
        } else {
            IndustryDto industryDto = getItemData(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.checkBox.setChecked(industryDto.isChecked());
        }
    }

    @Override
    public void createView(ViewHolder holder, final int position) {

        final IndustryDto entity = getItemData(position);
        holder.tvIndustryName.setText(entity.getName());
        holder.checkBox.setChecked(entity.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entity.setChecked(isChecked);
                if (onSubCheckedListener != null){
                    onSubCheckedListener.subChecked(entity,isChecked);
                }
            }
        });

        holder.rlSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity.setChecked(!entity.isChecked());
                notifyItemDataChanged(position,10);
            }
        });

    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.checkbox)
        CheckBox checkBox;

        @BindView(R.id.tv_industry_name)
        TextView tvIndustryName;

        @BindView(R.id.rl_sub)
        RelativeLayout rlSub;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    interface onSubCheckedListener{
        void subChecked(IndustryDto subEntity, boolean isChecked);
    }

    public void setOnSubCheckedListener(IndustrySubAdapter.onSubCheckedListener onSubCheckedListener) {
        this.onSubCheckedListener = onSubCheckedListener;
    }
}
