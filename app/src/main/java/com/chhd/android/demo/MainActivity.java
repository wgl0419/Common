package com.chhd.android.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chhd.android.common.http.HttpObserver;
import com.chhd.android.common.http.ResponseTransformer;
import com.chhd.android.common.ui.activity.H5Activity;
import com.chhd.android.common.ui.activity.base.ToolbarActivity;
import com.chhd.android.common.ui.view.Toolbar;
import com.chhd.android.common.util.SoftKeyboardUtils;
import com.chhd.android.common.util.ToastUtils;
import com.chhd.android.common.util.image.ImageLoader;
import com.chhd.android.common.util.image.ImageLoaderConfig;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends ToolbarActivity {

    private final String imgUrl = "http://pic.jj20.com/up/allimg/1011/121416110642/161214110642-2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(instance, H5Activity.class);
//                intent.putExtra("url", "https://m.baidu.com/");
//                startActivity(intent);

//                H5Activity.start(instance, R.style.AppTheme, "https://m.baidu.com/");

                startActivity(new Intent(instance, Demo1Activity.class));
            }
        });

        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(instance, Demo2Activity.class));
            }
        });

        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(instance, Demo3Activity.class));

//                H5Activity.start(instance, R.style.AppTheme, "", null);

//                setToolbarTitle("abc");

//                ImagePickerActivity.start(instance, new ImagePickerActivity.onResultListener() {
//                    @Override
//                    public void onResult(Bitmap bitmap) {
//                        Log.i("debug-app", "" + bitmap);
//                    }
//                });

//                Intent intent = new Intent(instance, TransparentActivity.class);
//                overridePendingTransition(0, 0);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                overridePendingTransition(0, 0);
//                startActivity(intent);

                startActivity(new Intent(instance, CodeActivity.class));
            }
        });

//        findViewById(R.id.tv3).setOnTouchListener(this);
//        findViewById(R.id.card1).setOnTouchListener(this);

        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.actionBarSize,
                outValue, true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.GREEN);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(50, RelativeLayout.LayoutParams.MATCH_PARENT);
//        relativeLayout.setLayoutParams(layoutParams);
//        setToolbarContainer(relativeLayout);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
//        toolbar.setToolbarContainer(relativeLayout);
        toolbar.setTitle("hello");

        ImageLoader.init(new ImageLoaderConfig().setNoPhoto(true));
        ImageLoader.getInstance().load(R.mipmap.ic_placeholder).into((ImageView) findViewById(R.id.iv));

//        Observable
//                .interval(2000, TimeUnit.MILLISECONDS)
//                .compose(this.<Long>bindToLifecycle())
//                .compose(RxHelper.<Long>ioMainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.i("debug-app", "" + aLong);
//                    }
//                });


        EditText et1 = findViewById(R.id.et1);
        SoftKeyboardUtils.showSoftInput(et1);

        toolbar.setTitle("213");

        findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, Demo4Activity.class);
                startActivity(intent);
            }
        });

        Observable
                .create(new ObservableOnSubscribe<ResponseData<String>>() {
                    @Override
                    public void subscribe(ObservableEmitter<ResponseData<String>> e) throws Exception {
                        ResponseData<String> responseData = new ResponseData<>();
                        responseData.setCode(0);
                        responseData.setMsg("成功");
                        e.onNext(responseData);
                        e.onComplete();
                    }
                })
                .compose(ResponseTransformer.<String>transform())
                .subscribe(new HttpObserver<String>() {
                    @Override
                    protected void onSucceed(String s) {
                    }
                });
    }

    @Override
    public int getContainerResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 10, 0, "菜单1");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected String getToolbarTitle() {
        return "hello";
    }

}
