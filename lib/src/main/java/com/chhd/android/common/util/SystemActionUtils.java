package com.chhd.android.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;


/**
 * 系统意图工具类
 *
 * @author : 葱花滑蛋 (2018/8/27)
 */
public class SystemActionUtils {

    private SystemActionUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    /**
     * 跳转电话界面，并指定电话号码
     *
     * @param phoneNumber 电话号码
     */
    public static void dial(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNumber);
            intent.setData(data);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转短信界面，并指定电话号码
     *
     * @param phoneNumber 电话号码
     */
    public static void sendTo(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri data = Uri.parse("smsto:" + phoneNumber);
            intent.setData(data);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转应用详细界面
     *
     * @param packageName 包名
     */
    public static void appDetailSettings(String packageName) {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", packageName, null));
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
