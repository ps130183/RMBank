package com.km.rmbank.module.personal.receiveraddress;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.utils.selectcity.SelectCityPick;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class CreateReceiverAddressActivity extends BaseActivity<CreateReceiverAddressPresenter> implements CreateReceiverAddressContract.View {

    @BindView(R.id.title)
    TextView title;
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

    private SelectCityPick cityPick;
    private ReceiverAddressDto mReceiverAddressDto;

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
    public CreateReceiverAddressPresenter getmPresenter() {
        return new CreateReceiverAddressPresenter(this);
    }

    @Override
    protected void onCreate() {
        mReceiverAddressDto = getIntent().getParcelableExtra("receiverAddressDto");
        cityPick = new SelectCityPick();
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        if (mReceiverAddressDto != null){//修改编辑
            title.setText("编辑收货地址");
            cityPick.getOptionsPosition(mReceiverAddressDto.getId());
            initReceiverAddress();
        }
        cityPick.showOptions(this,etAddressArea,vMasker);
    }

    /**
     * 编辑收货地址时   初始化页面
     */
    private void initReceiverAddress(){
        etName.setText(mReceiverAddressDto.getReceivePerson());
        etPhone.setText(mReceiverAddressDto.getReceivePersonPhone());
        String area = cityPick.getSelectedContent();
        String detail = mReceiverAddressDto.getReceiveAddress();
        detail = detail.replace(area,"");
        etAddressArea.setText(area);
        etAddressDetail.setText(detail);
    }

    public void save(){
        ReceiverAddressDto receiverAddressDto = new ReceiverAddressDto();
        receiverAddressDto.setReceivePerson(etName.getText().toString());
        receiverAddressDto.setReceivePersonPhone(etPhone.getText().toString());
        receiverAddressDto.setReceiveAddress(etAddressArea.getText().toString() + etAddressDetail.getText().toString());

        if (receiverAddressDto.isEmpty()){
            showToast("请将收货地址信息填写完整");
            return;
        }
        if (mReceiverAddressDto != null){ //更新
            receiverAddressDto.setId(mReceiverAddressDto.getId());
            mPresenter.updateReceiverAddress(receiverAddressDto);
        } else { //新增
            mPresenter.createReceiverAddress(receiverAddressDto);
        }

    }

    @Override
    public void createReceiverAddressSuccess(String id) {
        cityPick.saveOptionsPosition(id);
        finish();
    }
}
