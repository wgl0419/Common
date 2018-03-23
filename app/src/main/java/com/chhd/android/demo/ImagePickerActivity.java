package com.chhd.android.demo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

public class ImagePickerActivity extends AppCompatActivity {

    private static onResultListener onResultListener;

    public static void start(Context context, onResultListener onResultListener) {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        context.startActivity(intent);
        ImagePickerActivity.onResultListener = onResultListener;
    }

    private final String TAG = this.getClass().getSimpleName();

    private final int REQUEST_CODE_CAMERA = 100;
    private final int REQUEST_CODE_GALLERY = 101;

    private boolean fromUser;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        iv = findViewById(R.id.iv);

        new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                    }
                });

        new AlertDialog
                .Builder(this)
                .setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                File file = new File(getCacheDir(), "" + System.currentTimeMillis());
                                Log.i("debug-app", "" + file.getPath());
                                Log.i("debug-app", "" + file.getAbsolutePath());
                                // 把文件地址转换成Uri格式
                                Uri uri = Uri.fromFile(file);
                                // 设置系统相机拍摄照片完成后图片文件的存放地址
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                            }
                            break;
                            case 1: {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                            }
                            break;
                        }
                        fromUser = true;
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (!fromUser) {
//                            finish();
                        }
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case REQUEST_CODE_CAMERA: {
                }
                break;
                case REQUEST_CODE_GALLERY:
                    try {
                        Uri uri = data.getData();
                        Glide.with(this).load(new File(uri2path(uri))).into(iv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if (bitmap != null && onResultListener != null) {
                onResultListener.onResult(bitmap);
            }
//            finish();
        }
    }

    private String uri2path(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        onResultListener = null;
        super.onDestroy();
    }

    public interface onResultListener {
        void onResult(Bitmap bitmap);
    }
}
