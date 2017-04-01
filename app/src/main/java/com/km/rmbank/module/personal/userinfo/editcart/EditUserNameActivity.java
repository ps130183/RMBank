package com.km.rmbank.module.personal.userinfo.editcart;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.basic.BaseActivity;
import com.km.rmbank.module.personal.userinfo.EditUserCardActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserNameActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_name;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "姓名";
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                if (TextUtils.isEmpty(name)){
                    showToast("请填写内容再保存");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                setResult(EditUserCardActivity.REQUEST_CODE_ET_NAME,bundle);
            }
        });
        initEtName();
    }

    private void initEtName(){
        String name = getIntent().getStringExtra("name");
        etName.setText(name);
    }

    @OnClick(R.id.iv_clear)
    public void clear(View view){
        etName.setText("");
    }
}
