package com.chhd.android.common.util.image;

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
 * author : 葱花滑蛋
 * date   : 2018/03/18
 * desc   :
 */

public class ImageLoader {

    private static ImageLoader imageLoader = new ImageLoader();

    public static ImageLoader getInstance() {
        return imageLoader;
    }

    private ImageLoaderConfiguration configuration = new ImageLoaderConfiguration();

    private Activity activity;
    private Fragment fragment;
    private Object model;
    private boolean isAnimation = true;
    private int placeholderId;
    private int errorId;

    private ImageLoader() {
    }

    private ImageLoader with(Activity activity) {
        this.activity = activity;
        return this;
    }

    private ImageLoader with(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    private ImageLoader load(Object model) {
        this.model = model;
        return this;
    }

    private ImageLoader dontAnimate() {
        this.isAnimation = false;
        return this;
    }

    private ImageLoader placeholderId(int placeholderId) {
        this.placeholderId = placeholderId;
        return this;
    }

    private ImageLoader errorId(int errorId) {
        this.errorId = errorId;
        return this;
    }

    private void into(ImageView imageView) {

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
        } else if (configuration.getPlaceholderId() != 0) {
            requestBuilder.apply(RequestOptions.placeholderOf(configuration.getPlaceholderId()));
        }
        if (errorId != 0) {
            requestBuilder.apply(RequestOptions.errorOf(errorId));
        } else if (configuration.getErrorId() != 0) {
            requestBuilder.apply(RequestOptions.errorOf(configuration.getErrorId()));
        }
        if (configuration.isAnimation() && isAnimation) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        }
        if (configuration.isNoPhoto() && isMobileConnected(imageView.getContext())) {
            requestBuilder.apply(new RequestOptions().onlyRetrieveFromCache(true));
        }

        requestBuilder.into(imageView);

        reset();
    }

    private void reset() {
        model = null;
        placeholderId = 0;
        errorId = 0;
        isAnimation = true;
    }

    private Boolean isMobileConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (null != networkInfo && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return networkInfo.isAvailable();
        }
        return false;
    }
}
