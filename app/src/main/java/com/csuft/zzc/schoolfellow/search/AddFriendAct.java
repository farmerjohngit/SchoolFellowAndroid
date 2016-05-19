package com.csuft.zzc.schoolfellow.search;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.TopBar;
import com.csuft.zzc.schoolfellow.base.view.WebImageView;
import com.csuft.zzc.schoolfellow.host.data.UserData;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.HashMap;

/**
 * Created by wangzhi on 16/5/7.
 */
public class AddFriendAct extends BaseFragmentActivity {
    private static final String TAG = "AddFriendAct";
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fri_act);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        TextView nameTxt = (TextView) findViewById(R.id.name_txt);
        TextView contentTxt = (TextView) findViewById(R.id.content_txt);
        WebImageView avatar = (WebImageView) findViewById(R.id.head_img);
        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        topBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap();
                hashMap.put("from", UserManager.getInstance().getUser().userName);
                hashMap.put("to", mUserData.userName);
                BaseApi.getInstance().get(BaseApi.HOST_URL + "/add_friend", hashMap, AddFriendRespData.class, new CallBack<AddFriendRespData>() {
                    @Override
                    public void onSuccess(AddFriendRespData data) {
                        ScLog.i(TAG, "onSuccess");
                        showDialog(data.result.responseMsg);
                        if (data.result.responseResult == QueryUserData.FAILURE) {

                        }
                    }

                    @Override
                    public void onFailure(int code, String error) {
                        ScLog.i(TAG, "onFailure");
                    }
                });

            }
        });
        if (bundle != null) {
            mUserData = (UserData) bundle.getSerializable("user");
            if (mUserData == null) {
                finish();
            }
            nameTxt.setText(mUserData.name);
            contentTxt.setText(mUserData.introduction);
            avatar.setImageUrl(mUserData.avatar, true);

        }
    }

    public void showDialog(String msg) {
        AlertDialog dialog = new AlertDialog.Builder(AddFriendAct.this)
                .setTitle(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddFriendAct.this.finish();
                    }
                }).create();
        dialog.show();
    }
}
