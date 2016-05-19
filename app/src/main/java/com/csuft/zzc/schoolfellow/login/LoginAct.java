package com.csuft.zzc.schoolfellow.login;

import android.os.Bundle;

import com.csuft.zzc.schoolfellow.react.ReactNativeBaseAct;

/**
 * Created by wangzhi on 16/5/3.
 */
public class LoginAct extends ReactNativeBaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    @Override
    public String getAppName() {
        return "SchoolFellow_Reg";
    }


}
