package com.km.rmbank.entity;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class HomePersonalFuntionEntity {

    private String funtionName;
    private int funtionImgRes;

    public HomePersonalFuntionEntity(String funtionName, int funtionImgRes) {
        this.funtionName = funtionName;
        this.funtionImgRes = funtionImgRes;
    }

    public String getFuntionName() {
        return funtionName;
    }

    public void setFuntionName(String funtionName) {
        this.funtionName = funtionName;
    }

    public int getFuntionImgRes() {
        return funtionImgRes;
    }

    public void setFuntionImgRes(int funtionImgRes) {
        this.funtionImgRes = funtionImgRes;
    }
}
