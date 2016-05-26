package com.csuft.zzc.schoolfellow.regi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.login.LoginAct;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wangzhi on 16/5/6.
 */
public class RegiAct extends BaseFragmentActivity {
    private static final String TAG = "RegiAct";
    private EditText mPassword;
    private EditText mUserEdit;
    private Spinner mEduStartSpinner;
    private Spinner mSchoolSpinner;
    private Spinner mCollegeSpinner;
    private Spinner mProfessionSpinner;
    //todo
    private ArrayList<String> arrs;
    private RadioGroup mRankRadioGroup;
    private int mSelectRank = 0;
    private String mEduStartDate;
    private EditText mNameEdit;
    private EditText mClassEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regi_act);
        mUserEdit = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.password);
        mNameEdit = (EditText) findViewById(R.id.name);
        mClassEdit = (EditText) findViewById(R.id.class_edit);
        mEduStartSpinner = (Spinner) findViewById(R.id.edu_start_date_spinner);
        mSchoolSpinner = (Spinner) findViewById(R.id.school_spinner);
        mCollegeSpinner = (Spinner) findViewById(R.id.colleges_spinner);
        mProfessionSpinner = (Spinner) findViewById(R.id.profession_spinner);

        mRankRadioGroup = (RadioGroup) findViewById(R.id.group);
        mRankRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_tea:
                        mSelectRank = 2;
                        break;
                    case R.id.radio_stu:
                        mSelectRank = 0;
                        break;
                    case R.id.radio_graduate:
                        mSelectRank = 1;
                        break;
                }
            }
        });
        genetateStartSchoolDataArr();
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrs);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mEduStartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ScLog.i("position :  " + position + "  " + arrs.get(position));
                mEduStartDate = arrs.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mEduStartSpinner.setAdapter(adapter2);
    }

    public void genetateStartSchoolDataArr() {
        arrs = new ArrayList<>();
        for (int i = 1999; i <= 2016; i++) {
            arrs.add(i + "");
        }
    }


    public void reqData() {
        HashMap<String, String> hashMap = new HashMap<>();
        Gson gson = new Gson();
        UserData userData = new UserData();
        userData.userName = mUserEdit.getText().toString();
        userData.userPwd = mPassword.getText().toString();
        userData.name = mNameEdit.getText().toString();
        userData.rank = mSelectRank;
        userData.eduStartDate = mEduStartDate;
        userData.classs = mClassEdit.getText().toString();
        userData.school = (String) mSchoolSpinner.getSelectedItem();
        userData.colleges = (String) mCollegeSpinner.getSelectedItem();
        userData.profession = (String) mProfessionSpinner.getSelectedItem();
        String str = gson.toJson(userData);
        ScLog.i(TAG, "json: " + str);
        hashMap.put("registerUser", str);
        BaseApi.getInstance().post(BaseApi.HOST_URL + "/register", hashMap, RegisterData.class, new CallBack<RegisterData>() {
            @Override
            public void onSuccess(RegisterData data) {
                ScLog.i(TAG, "onSuccess " + data.result.regiResult);
                if (RegisterData.REGI_SUCCESS == data.result.regiResult) {
                    ScLog.i(TAG, data.result.user);
                    Intent intent = new Intent(RegiAct.this, LoginAct.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(RegiAct.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegiAct.this, "用户已经存在～", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.i(TAG, "onFailure " + error);
            }
        });
    }

    public void register(View view) {
        if (TextUtils.isEmpty(mUserEdit.getText().toString()) || TextUtils.isEmpty(mPassword.getText().toString())) {
            Toast.makeText(this, "账号和密码不能为空～", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mNameEdit.getText().toString())) {
            Toast.makeText(this, "请填写姓名～", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mClassEdit.getText().toString())) {
            Toast.makeText(this, "请填写班级～", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!RegiCheckUtil.isFormatAccountOrPwd(mUserEdit.getText().toString())){
            Toast.makeText(this, "账号必须是7-12位英文字母或数字", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!RegiCheckUtil.isFormatAccountOrPwd(mPassword.getText().toString())){
            Toast.makeText(this, "密码必须是7-12位英文字母或数字", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!RegiCheckUtil.isFormatName(mNameEdit.getText().toString())){
            Toast.makeText(this, "姓名必须是2到10位中文汉字", Toast.LENGTH_SHORT).show();
            return;
        }

        reqData();

    }
}
