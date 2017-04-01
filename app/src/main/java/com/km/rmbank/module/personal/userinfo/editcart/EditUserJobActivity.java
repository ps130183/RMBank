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

public class EditUserJobActivity extends BaseActivity {

    @BindView(R.id.et_job)
    EditText etJob;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_job;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "职位";
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job = etJob.getText().toString();
                if (TextUtils.isEmpty(job)){
                    showToast("请填写正确的职位");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("job",job);
                setResult(EditUserCardActivity.REQUEST_CODE_ET_JOB,bundle);
            }
        });
        init();
    }

    private void init(){
        String job = getIntent().getStringExtra("job");
        etJob.setText(job);
    }

    @OnClick(R.id.iv_clear)
    public void clear(View view){
        etJob.setText("");
    }
}
