package com.chhd.android.common.util.image;

/**
 * 图片加载器配置
 *
 * @author : 陈伟强 (2018/03/18)
 */
public class ImageLoaderConfig {

    /**
     * 加载占位图
     */
    private int placeholderId;
    /**
     * 错误占位图
     */
    private int errorId;
    /**
     * 加载动画
     */
    private boolean isAnimation = true;
    /**
     * 省流量模式
     */
    private boolean isNoPhoto = false;

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
