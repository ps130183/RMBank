package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.MasterWorkDto;

import butterknife.BindView;

/**
 * Created by PengSong on 17/10/18.
 */

public class OrderWorkAdapter extends BaseAdapter<MasterWorkDto> implements BaseAdapter.IAdapter<OrderWorkAdapter.ViewHolder> {

    public OrderWorkAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_order_work);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MasterWorkDto masterWorkDto = getItemData(position);
        holder.tvTitle.setText(masterWorkDto.getHeadings());
        if (masterWorkDto.isChecked()){
            holder.llContent.setBackgroundResource(R.color.color_text_gray2);
        } else {
            holder.llContent.setBackgroundResource(R.color.color_white);
        }
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void checkedWork(MasterWorkDto masterWorkDto){
        for (MasterWorkDto workDto : getAllData()){
            workDto.setChecked(false);
        }
        masterWorkDto.setChecked(true);
        notifyDataSetChanged();
    }

    public MasterWorkDto getCheckedWork(){
        MasterWorkDto masterWorkDto = null;
        for (MasterWorkDto workDto : getAllData()){
            if (workDto.isChecked()){
                masterWorkDto = workDto;
                break;
            }
        }
        return masterWorkDto;
    }
}
