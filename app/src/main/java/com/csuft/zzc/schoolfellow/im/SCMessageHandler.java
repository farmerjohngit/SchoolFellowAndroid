package com.csuft.zzc.schoolfellow.im;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.view.ScToast;

import java.util.List;

public class SCMessageHandler extends AVIMMessageHandler {
    private static final String TAG = "SCMessageHandler";

    private SCMessageHandler() {

    }

    public static class SingletonHolder {
        private static SCMessageHandler INSTANCE = new SCMessageHandler();
    }

    public static SCMessageHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    List<MessageListener> mListeners;

    public interface MessageListener {
        void onMessage(AVIMMessage message);
    }

    public void addOnMessageListener(MessageListener listener) {
        mListeners.add(listener);
    }

    protected void notifyAllListener(AVIMMessage message) {
        for (MessageListener l : mListeners) {
            l.onMessage(message);
        }
    }

    //接收到消息后的处理逻辑
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        if (message instanceof AVIMTextMessage) {
            ScLog.i(TAG, "msg ==> " + message.getFrom() + " say: " + ((AVIMTextMessage) message).getText());
        }
        notifyAllListener(message);
    }

    public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {

    }
}