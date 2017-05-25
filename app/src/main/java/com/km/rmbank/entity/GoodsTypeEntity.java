package com.km.rmbank.entity;

/**
 * Created by kamangkeji on 17/5/18.
 */

public class GoodsTypeEntity {
    private String gtName;

    private boolean isChecked;
    public GoodsTypeEntity(String gtName) {
        this.gtName = gtName;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
