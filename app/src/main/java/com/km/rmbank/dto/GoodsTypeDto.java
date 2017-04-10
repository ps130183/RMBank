package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsTypeDto extends BaseEntity implements Parcelable {
    private String typeName;

    private boolean isChecked;
    public GoodsTypeDto(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isEmpty() {
        if (!TextUtils.isEmpty(typeName)){
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
        dest.writeString(this.typeName);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected GoodsTypeDto(Parcel in) {
        this.typeName = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GoodsTypeDto> CREATOR = new Parcelable.Creator<GoodsTypeDto>() {
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
