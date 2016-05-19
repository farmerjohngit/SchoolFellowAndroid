package com.csuft.zzc.schoolfellow.host;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.login.LoginAct;
import com.csuft.zzc.schoolfellow.user.UserManager;

/**
 * Created by wangzhi on 16/5/4.
 */
public class SplashAct extends BaseFragmentActivity {

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!UserManager.getInstance().isLogin()) {
                    startActivity(new Intent(SplashAct.this, LoginAct.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashAct.this, HostActivity.class));
                    finish();
                }
            }
        }, 1500);

    }


}
