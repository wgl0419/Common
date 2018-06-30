package com.chhd.android.common.util.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;


/**
 * 图片加载器，基于Glide实现，统一图片加载入口
 *
 * @author : 葱花滑蛋 (2018/03/18)
 */

public class ImageLoader {

    private static ImageLoaderConfig config = new ImageLoaderConfig();

    public static void init(ImageLoaderConfig config) {
        ImageLoader.config = config;
    }

    @SuppressLint("StaticFieldLeak")
    private static ImageLoader imageLoader = new ImageLoader();

    public static ImageLoader getInstance() {
        return new ImageLoader();
    }

    private Activity activity;
    private Fragment fragment;
    private Object model;
    /**
     * 加载动画
     */
    private boolean isAnimation = true;
    /**
     * 加载占位图
     */
    private int placeholderId;
    /**
     * 错误占位图
     */
    private int errorId;

    private ImageLoader() {
    }


    /**
     * 绑定当前Activity的生命周期
     *
     * @param activity 当前Activity
     * @return ImageLoader
     */
    public ImageLoader with(Activity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * 绑定当前Fragment的生命周期
     *
     * @param fragment 当前Fragment
     * @return ImageLoader
     */
    public ImageLoader with(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    /**
     * 加载图片
     *
     * @param model 本地图片、网络图片
     * @return ImageLoader
     */
    public ImageLoader load(Object model) {
        this.model = model;
        return this;
    }

    /**
     * 关闭加载动画
     *
     * @return ImageLoader
     */
    public ImageLoader dontAnimate() {
        this.isAnimation = false;
        return this;
    }

    /**
     * 设置加载占位图
     *
     * @param placeholderId 加载占位图
     * @return ImageLoader
     */
    public ImageLoader placeholderId(int placeholderId) {
        this.placeholderId = placeholderId;
        return this;
    }

    /**
     * 设置错误占位图
     *
     * @param errorId 错误占位图
     * @return ImageLoader
     */
    public ImageLoader errorId(int errorId) {
        this.errorId = errorId;
        return this;
    }

    public void into(ImageView imageView) {

        RequestManager requestManager;
        if (activity != null) {
            requestManager = Glide.with(activity);
        } else if (fragment != null) {
            requestManager = Glide.with(fragment);
        } else {
            requestManager = Glide.with(imageView);
        }

        RequestBuilder requestBuilder = requestManager.load(model);

        if (placeholderId != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(placeholderId));
        } else if (config.getPlaceholderId() != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(config.getPlaceholderId()));
        }
        if (errorId != 0) {
            requestBuilder.apply(RequestOptions.errorOf(errorId));
        } else if (config.getErrorId() != 0) {
            requestBuilder.apply(RequestOptions.errorOf(config.getErrorId()));
        }
        String appCompatImageViewClazzName = "android.support.v7.widget.AppCompatImageView";
        String imageViewClazzName = "android.widget.ImageView";
        String clazzName = imageView.getClass().getName();
        if (config.isAnimation() && isAnimation
                && (appCompatImageViewClazzName.equals(clazzName) || imageViewClazzName.equals(clazzName))) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        }
        if (config.isNoPhoto() && isMobileConnected(imageView.getContext()) && isUrl(model)) {
            requestBuilder.apply(new RequestOptions().onlyRetrieveFromCache(true));
        }

        requestBuilder.into(imageView);

        reset();
    }

    private void reset() {
        activity = null;
        fragment = null;
        model = null;
        isAnimation = true;
        placeholderId = 0;
        errorId = 0;
    }

    /**
     * 是否移动数据连接
     */
    private boolean isMobileConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    private boolean isUrl(Object model) {
        if (model instanceof String) {
            String str = (String) model;
            if (str.startsWith("http://") || str.startsWith("https://")) return true;
        }
        return false;
    }

}
