package com.csuft.zzc.schoolfellow.im.act;

import java.io.IOException;
import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.sqk.emojirelease.Emoji;
import com.sqk.emojirelease.EmojiUtil;
import com.sqk.emojirelease.FaceFragment;

/**
 * @author Joker_Ya
 */
public class EmojiAct extends BaseFragmentActivity implements FaceFragment.OnEmojiClickListener {

    private TextView textView;
    private EditText editText;
    private Button button;
    private int[] images = {R.mipmap.btn_navigation_back, R.mipmap.search};
    private static final String TAG = "EmojiAct";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emtest);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.button);
        FaceFragment faceFragment = FaceFragment.Instance();
        faceFragment.setListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container, faceFragment).commit();
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 产生0~7的随机数
                int randomId = new Random().nextInt(2);
                /*
				 * TextView显示表情图片
				 */
                String html = "这是QQ表情<img src='" + images[randomId] + "'/>";
                // 调用Html的fromHtml对html处理
                CharSequence charSequence = Html.fromHtml(html,
                        new ImageGetter() {

                            @Override
                            public Drawable getDrawable(String source) {

                                Drawable drawable = getResources().getDrawable(
                                        Integer.parseInt(source));
                                // 设置drawable的大小。设置为实际大小
                                drawable.setBounds(0, 0,
                                        drawable.getIntrinsicWidth(),
                                        drawable.getIntrinsicHeight());
                                return drawable;
                            }
                        }, null);
                textView.setText(charSequence);
				/*
				 * EditView显示表情图片
				 */
                // 得到随机表情图片的Bitmap
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        images[randomId]);
                // 得到SpannableString对象,主要用于拆分字符串
                SpannableString spannableString = new SpannableString("image");
                // 得到ImageSpan对象
                ImageSpan imageSpan = new ImageSpan(EmojiAct.this, bitmap);
                // 调用spannableString的setSpan()方法
                spannableString.setSpan(imageSpan, 0, 5,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                // 给EditText追加spannableString
                editText.append(spannableString);
            }
        });
    }

    @Override
    public void onEmojiDelete() {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(text.length() - 1, text.length()))) {
            int index = text.lastIndexOf("[");
            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
                displayTextView();
                return;
            }
            editText.getText().delete(index, text.length());
            displayTextView();
            return;
        }
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
        displayTextView();
    }

    private void displayTextView() {
        try {
            EmojiUtil.handlerEmojiText(textView, editText.getText().toString(), this);
            EmojiUtil.handlerEmojiText(editText, editText.getText().toString(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEmojiClick(Emoji emoji) {
        if (emoji != null) {
            int index = editText.getSelectionStart();
            Editable editable = editText.getEditableText();
            if (index < 0) {
                editable.append(emoji.getContent());
            } else {
                editable.insert(index, emoji.getContent());
            }
        }
        displayTextView();
    }
}
