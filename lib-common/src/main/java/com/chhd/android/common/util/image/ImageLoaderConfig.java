package com.chhd.android.common.util.image;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/18
 * desc   :
 */

public class ImageLoaderConfig {

    private int placeholderId; // 加载占位图
    private int errorId; // 错误占位图
    private boolean isAnimation = true; // 加载动画
    private boolean isNoPhoto = false; // 省流量模式

    public int getPlaceholderId() {
        return placeholderId;
    }

    public ImageLoaderConfig setPlaceholderId(int placeholderId) {
        this.placeholderId = placeholderId;
        return this;
    }

    public int getErrorId() {
        return errorId;
    }

    public ImageLoaderConfig setErrorId(int errorId) {
        this.errorId = errorId;
        return this;
    }

    public boolean isAnimation() {
        return isAnimation;
    }

    public ImageLoaderConfig setAnimation(boolean animation) {
        this. isAnimation = animation;
        return this;
    }

    public boolean isNoPhoto() {
        return isNoPhoto;
    }

    public ImageLoaderConfig setNoPhoto(boolean noPhoto) {
        this.isNoPhoto = noPhoto;
        return this;
    }
}
