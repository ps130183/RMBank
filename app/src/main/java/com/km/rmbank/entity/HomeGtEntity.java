package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/5/12.
 */

public class HomeGtEntity implements Parcelable {
    private String gtName;
    private int gtImageRes;

    public HomeGtEntity(String gtName, int gtImageRes) {
        this.gtName = gtName;
        this.gtImageRes = gtImageRes;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
    }

    public int getGtImageRes() {
        return gtImageRes;
    }

    public void setGtImageRes(int gtImageRes) {
        this.gtImageRes = gtImageRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gtName);
        dest.writeInt(this.gtImageRes);
    }

    protected HomeGtEntity(Parcel in) {
        this.gtName = in.readString();
        this.gtImageRes = in.readInt();
    }

    public static final Parcelable.Creator<HomeGtEntity> CREATOR = new Parcelable.Creator<HomeGtEntity>() {
        @Override
        public HomeGtEntity createFromParcel(Parcel source) {
            return new HomeGtEntity(source);
        }

        @Override
        public HomeGtEntity[] newArray(int size) {
            return new HomeGtEntity[size];
        }
    };
}
