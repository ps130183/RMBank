package com.km.rmbank.dto;

import android.text.TextUtils;

import com.km.rmbank.basic.BaseEntity;

/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressDto extends BaseEntity {
    private int isDefault;

    private String id;
    private String receivePerson;
    private String receivePersonPhone;
    private String receiveAddress;

    public int isDefault() {
        return isDefault;
    }

    public void setDefault(int aDefault) {
        isDefault = aDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getReceivePersonPhone() {
        return receivePersonPhone;
    }

    public void setReceivePersonPhone(String receivePersonPhone) {
        this.receivePersonPhone = receivePersonPhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(receivePerson) || TextUtils.isEmpty(receivePersonPhone) || TextUtils.isEmpty(receiveAddress)){
            return true;
        }
        return false;
    }
}
