package com.csuft.zzc.schoolfellow.react;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.host.HostActivity;
import com.csuft.zzc.schoolfellow.login.LoginData;
import com.csuft.zzc.schoolfellow.regi.RegiAct;
import com.csuft.zzc.schoolfellow.user.UserManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;

/**
 * Created by wangzhi on 16/5/4.
 */
public class LoginModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "Login";

    public LoginModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void goRegi() {
        ScLog.i(MODULE_NAME, "goRegi");
        getCurrentActivity().startActivity(new Intent(getCurrentActivity(), RegiAct.class));
    }

    @ReactMethod
    public void login(String acc, String pwd) {

        if (TextUtils.isEmpty(acc) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(getCurrentActivity(), "账号和密码不能为空～", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("account", acc);
        map.put("password", pwd);
        BaseApi.getInstance().post(BaseApi.HOST_URL + "/login", map, LoginData.class, new CallBack<LoginData>() {
            @Override
            public void onSuccess(LoginData data) {
                if (data.result.responseResult == LoginData.SUCCESS) {
                    UserManager.getInstance().setUser(data.result.user, true);

                    Intent intent = new Intent(getCurrentActivity(), HostActivity.class);
                    getCurrentActivity().startActivity(intent);
                    getCurrentActivity().finish();
                } else {
                    Toast.makeText(getCurrentActivity(), data.result.responseMsg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int code, String error) {
                ScLog.e("onFailure " + error);
                Toast.makeText(getCurrentActivity(), "网络不好~", Toast.LENGTH_SHORT).show();
            }
        });

    }
}