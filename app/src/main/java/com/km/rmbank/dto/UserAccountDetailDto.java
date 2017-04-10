package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountDetailDto {

    /**
     * amount : 100
     * tradeDirection : 1
     * tradeType : 026
     * mobilePhone : 15631707132
     * createDate : 2017-04-06 14:47:13
     */

    private String amount;
    private String tradeDirection;
    private String tradeType;
    private String mobilePhone;
    private String createDate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeDirection() {
        return tradeDirection;
    }

    public void setTradeDirection(String tradeDirection) {
        this.tradeDirection = tradeDirection;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
