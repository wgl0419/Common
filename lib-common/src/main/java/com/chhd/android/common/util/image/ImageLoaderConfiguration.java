package com.chhd.android.common.util.image;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/18
 * desc   :
 */

public class ImageLoaderConfiguration {

    private int placeholderId; // 加载占位图
    private int errorId; // 错误占位图
    private boolean isAnimation = true; // 加载动画
    private boolean isNoPhoto = false; // 省流量模式

    public int getPlaceholderId() {
        return placeholderId;
    }

    public void setPlaceholderId(int placeholderId) {
        this.placeholderId = placeholderId;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public boolean isAnimation() {
        return isAnimation;
    }

    public void setAnimation(boolean animation) {
        isAnimation = animation;
    }

    public boolean isNoPhoto() {
        return isNoPhoto;
    }

    public void setNoPhoto(boolean noPhoto) {
        isNoPhoto = noPhoto;
    }
}
