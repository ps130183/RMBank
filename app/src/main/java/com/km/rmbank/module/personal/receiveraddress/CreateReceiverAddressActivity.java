package com.km.rmbank.module.personal.receiveraddress;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.utils.PickerUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateReceiverAddressActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;

    @BindView(R.id.et_address_area)
    EditText etAddressArea;
    @BindView(R.id.vMasker)
    View vMasker;

    @Override
    protected int getContentView() {
        return R.layout.activity_create_receiver_address;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "新建收货地址";
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        PickerUtils.showOptions(this,etAddressArea,vMasker);
    }

    public void save(){
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String addressDetail = etAddressDetail.getText().toString();
        String addressArea = etAddressArea.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(addressArea) || TextUtils.isEmpty(addressDetail)){
            showToast("请将收货地址信息填写完整");
            return;
        }

        showToast("name = " + name
        +"\nphone = " + phone
        +"\naddressArea = " + addressArea
        +"\naddressDetail = " +addressDetail);
    }
}
