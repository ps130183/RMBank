package com.km.rmbank.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseAdapter;
import com.km.rmbank.dto.GoodsDto;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartSubListAdapter extends BaseAdapter<GoodsDto> implements BaseAdapter.IAdapter<ShoppingCartSubListAdapter.ViewHolder> {

    private boolean isShoppingCart;

    private ShoppingCartParentListAdapter parentListAdapter;
    private int positionOnParent;
    private OnSubCheckedListener onSubCheckedListener;

    public ShoppingCartSubListAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_shopping_cart_sub_list);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final GoodsDto entity = getItemData(position);
        holder.mCheckBox.setChecked(entity.isChecked());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                entity.setChecked(isChecked);
//                parentListAdapter.checkParentBySub(positionOnParent,isChecked);
                onSubCheckedListener.onSubChecked(isChecked);
            }
        });

    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);

            mCheckBox.setVisibility(isShoppingCart ? View.VISIBLE:View.GONE);
        }

        @OnClick(R.id.checkbox)
        public void checkBox(View view){
//            parentListAdapter.setSubClick(true);
        }
    }

    /**
     * 是否是购物车
     * @param shoppingCart
     */
    public void setShoppingCart(boolean shoppingCart) {
        isShoppingCart = shoppingCart;
    }
    //    public void setParentListAdapter(ShoppingCartParentListAdapter parentListAdapter,int positionOnParent) {
//        this.parentListAdapter = parentListAdapter;
//        this.positionOnParent = positionOnParent;
//    }

    interface OnSubCheckedListener{
        void onSubChecked(boolean isChecked);
    }

    public void setOnSubCheckedListener(OnSubCheckedListener onSubCheckedListener) {
        this.onSubCheckedListener = onSubCheckedListener;
    }
}
