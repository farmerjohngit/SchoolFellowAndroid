package com.csuft.zzc.schoolfellow.publish;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.csuft.zzc.schoolfellow.R;
import com.csuft.zzc.schoolfellow.base.act.BaseFragmentActivity;
import com.csuft.zzc.schoolfellow.base.data.QueryData;
import com.csuft.zzc.schoolfellow.base.net.BaseApi;
import com.csuft.zzc.schoolfellow.base.net.CallBack;
import com.csuft.zzc.schoolfellow.base.utils.BitmapUtil;
import com.csuft.zzc.schoolfellow.base.utils.ScLog;
import com.csuft.zzc.schoolfellow.base.utils.ScreenUtil;
import com.csuft.zzc.schoolfellow.base.view.ScToast;
import com.csuft.zzc.schoolfellow.base.view.TopBar;
import com.csuft.zzc.schoolfellow.user.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangzhi on 16/5/18.
 */
public class PublishAct extends BaseFragmentActivity {

    private LinearLayout mSelectImages;
    ImageView addImageView;
    String imagePath;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_act);
        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        mEdit = (EditText) findViewById(R.id.edit_content);
        addImageView = (ImageView) findViewById(R.id.add_img);
        topBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap params = new HashMap();
                params.put("text", mEdit.getText().toString());
                params.put("userName", UserManager.getInstance().getUser().userName);
                addImageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = addImageView.getDrawingCache();
                String bitmapPath = BitmapUtil.saveBiteMapToSdTemp("temp", bitmap);
                addImageView.setDrawingCacheEnabled(false);
                if (!TextUtils.isEmpty(bitmapPath)) {
                    ScLog.i(TAG, "bitmap path is " + bitmapPath);
                    ArrayList fileList = new ArrayList();
                    if (!TextUtils.isEmpty(imagePath)) {
                        fileList.add(imagePath);
                    }
                    ScLog.i(TAG, "fileList size  " + fileList.size());
                    BaseApi.getInstance().postWithImage(BaseApi.HOST_URL + "/publish", params, fileList, PublishRespData.class, new CallBack<PublishRespData>() {
                        @Override
                        public void onSuccess(PublishRespData data) {
                            ScLog.i(TAG, "onSuccess");
                            if (QueryData.SUCCESS == data.result.responseResult) {
                                setResult(RESULT_OK);
                                finish();
                            }
                            ScToast.toast(data.result.responseMsg);
                        }

                        @Override
                        public void onFailure(int code, String error) {
                            ScLog.i(TAG, "onFailure:" + error);
                        }
                    });
                } else {
                    ScLog.i(TAG, "bitmap path is null");
                }

            }
        });
        mSelectImages = (LinearLayout) findViewById(R.id.select_img);
    }

    public void selectImg(View view) {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }

    private static final String TAG = "PublishAct";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {

                String realPath = "";
                ScLog.i(TAG, "urk " + uri.getScheme() + "<>" + uri.getPath() + "<>" + uri.getHost());
                if (uri.getScheme().equals("file:")) {
                    realPath = uri.getPath();
                } else {
                    realPath = getRealPathFromURI(this, uri);
                }
                ScLog.i(TAG, "图片真实路径：" + realPath);
                if (realPath != null) {
                    imagePath = realPath;
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);


                options.inSampleSize = Math.min(options.outWidth, options.outHeight) / ScreenUtil.instance().dip2px(100);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);

                ImageView imageView = addImageView;
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
                imageView.setBackground(null);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.instance().dip2px(80), ScreenUtil.instance().dip2px(80));
//                params.setMargins(4, 0, 4, 0);
//                imageView.setLayoutParams(params);
//                mSelectImages.addView(imageView, 0);
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            return getRealPathFromURI_API19(context, uri);
        } else {
            return getRealPathFromURI_API11to18(context, uri);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = null;

            wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return getRealPathFromURI_BelowAPI11(context, uri);
        }

    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
