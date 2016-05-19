package com.csuft.zzc.schoolfellow.im.act;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SendCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.im.ImUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.ScToast;
import com.csuft.zzc.schoolfellow.host.data.UserData;

import java.util.Arrays;

/**
 * Created by wangzhi on 16/4/27.
 */
public class P2PChatAct extends BaseFragmentActivity {
    private static final String TAG = "P2PChatAct";

    UserData receUser;
    private EditText mMEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2pchat_act);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            receUser = (UserData) bundle.getSerializable("user");
        }
        mMEditText = (EditText) findViewById(R.id.text);
    }


    public void send(View view) {
        ScLog.i(TAG, "send");
        ImUtil.sendTextMsgToSingle(receUser.userName, mMEditText.getText().toString());
    }
}
