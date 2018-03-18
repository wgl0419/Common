package com.chhd.android.common.util.image;

/**
 * author : 葱花滑蛋
 * date   : 2018/03/18
 * desc   :
 */

public class ImageLoaderConfiguration {

    private int placeholderId;
    private int errorId;
    private boolean isAnimation = true;
    private boolean isNoPhoto = false;

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
