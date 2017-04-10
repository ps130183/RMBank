package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.GoodsTypeDto;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsTypeAdapter extends BaseAdapter<GoodsTypeDto> implements BaseAdapter.IAdapter<GoodsTypeAdapter.ViewHolder> {

    public GoodsTypeAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_goods_type);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final GoodsTypeDto goodsTypeDto = getItemData(position);
        holder.cbGoodsType.setText(goodsTypeDto.getTypeName());
        holder.cbGoodsType.setChecked(goodsTypeDto.isChecked());
       holder.cbGoodsType.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setChecked(goodsTypeDto);
           }
       });
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.cb_goods_type)
        CheckBox cbGoodsType;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void setChecked(GoodsTypeDto typeDto){
        for (GoodsTypeDto goodsTypeDto : getAllData()){
            goodsTypeDto.setChecked(false);
        }
        typeDto.setChecked(true);
        notifyDataChanged();
    }

    /**
     * 返回当前选中的类型
     * @return
     */
    public GoodsTypeDto getCheckedGoodsType(){
        for (GoodsTypeDto goodsTypeDto : getAllData()){
            if (goodsTypeDto.isChecked()){
                return goodsTypeDto;
            }
        }
        return new GoodsTypeDto("");
    }

    public void setDefaultChecked(GoodsTypeDto goodsTypeDto){
        if (goodsTypeDto == null){
            return;
        }
        for (GoodsTypeDto typeDto : getAllData()){
            if (typeDto.getTypeName().equals(goodsTypeDto.getTypeName())){
                typeDto.setChecked(true);
                break;
            }
        }

        notifyDataChanged();
    }
}
