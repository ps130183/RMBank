package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class PayOrderDto {
    /**
     * payNumber : REO20170406112139254
     * sumPrice : 20000.00
     */

    private String payNumber;
    private String sumPrice;

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }
}
