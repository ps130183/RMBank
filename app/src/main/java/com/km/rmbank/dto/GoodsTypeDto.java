package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsTypeDto extends BaseEntity implements Parcelable {
    private String typeId;
    private String productType;

    private boolean isChecked;
    public GoodsTypeDto(String typeName) {
        this.productType = typeName;
        typeId = "";
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean isEmpty() {
        if (!TextUtils.isEmpty(productType)){
            return false;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.typeId);
        dest.writeString(this.productType);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected GoodsTypeDto(Parcel in) {
        this.typeId = in.readString();
        this.productType = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<GoodsTypeDto> CREATOR = new Creator<GoodsTypeDto>() {
        @Override
        public GoodsTypeDto createFromParcel(Parcel source) {
            return new GoodsTypeDto(source);
        }

        @Override
        public GoodsTypeDto[] newArray(int size) {
            return new GoodsTypeDto[size];
        }
    };
}
