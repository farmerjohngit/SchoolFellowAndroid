package com.csuft.zzc.schoolfellow.im;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.utils.AppContextGetter;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.host.HostActivity;
import com.csuft.zzc.schoolfellow.host.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

public class SCMessageHandler extends AVIMMessageHandler {
    private static final String TAG = "SCMessageHandler";

    private SCMessageHandler() {
        mListeners = new ArrayList<>();
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

    public RecyclerView addOnMessageListener(MessageListener listener) {
        mListeners.add(listener);
        return null;
    }

    protected void notifyAllListener(AVIMMessage message) {
        for (MessageListener l : mListeners) {
            l.onMessage(message);
        }
    }

    //接收到消息后的处理逻辑
    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        //todo 暂时只处理文字消息
        if (message instanceof AVIMTextMessage) {
            ScLog.i(TAG, "msg ==> " + message.getFrom() + " say: " + ((AVIMTextMessage) message).getText());
            Intent intent = new Intent(AppContextGetter.instance().get(), HostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            Bundle bundle = new Bundle();
            intent.putExtra("init_fragment", FragmentFactory.PAGER_CHAT_TITLE);
//            bundle.putString("init_fragment", "FragmentFactory.PAGER_CHAT");
//            intent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(AppContextGetter.instance().get(), 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification compat = new NotificationCompat.Builder(AppContextGetter.instance().get())
                    .setContentText(((AVIMTextMessage) message).getText())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(message.getFrom())
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager mNotificationManager = (NotificationManager) AppContextGetter.instance().get().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, compat);
        }


        notifyAllListener(message);
    }

    public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {

    }
}