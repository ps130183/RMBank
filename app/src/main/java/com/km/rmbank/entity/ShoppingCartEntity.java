package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.dto.GoodsDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartEntity implements Parcelable {
    private List<GoodsDto> goodsEntities;

    private boolean isChecked;

    public ShoppingCartEntity() {
    }

    public List<GoodsDto> getGoodsEntities() {
        return goodsEntities;
    }

    public void setGoodsEntities(List<GoodsDto> goodsEntities) {
        this.goodsEntities = goodsEntities;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.goodsEntities);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected ShoppingCartEntity(Parcel in) {
        this.goodsEntities = in.createTypedArrayList(GoodsDto.CREATOR);
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<ShoppingCartEntity> CREATOR = new Creator<ShoppingCartEntity>() {
        @Override
        public ShoppingCartEntity createFromParcel(Parcel source) {
            return new ShoppingCartEntity(source);
        }

        @Override
        public ShoppingCartEntity[] newArray(int size) {
            return new ShoppingCartEntity[size];
        }
    };
}
