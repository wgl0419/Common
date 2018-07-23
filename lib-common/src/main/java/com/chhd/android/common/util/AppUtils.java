package com.chhd.android.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * AppUtils
 *
 * @author : 葱花滑蛋 (2018/7/20)
 */

public class AppUtils {

    private AppUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    private static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    /* -------------------------- 获取App包名 -------------------------- */

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /* -------------------------- 获取App名字 -------------------------- */

    public static String getAppName() {
        return getAppName(getPackageName());
    }


    public static String getAppName(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.applicationInfo.loadLabel(pm).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /* -------------------------- 获取App版本名称 -------------------------- */

    public static String getAppVersionName() {
        return getAppVersionName(getPackageName());
    }


    public static String getAppVersionName(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? "" : pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /* -------------------------- 获取App版本号 -------------------------- */

    public static int getAppVersionCode() {
        return getAppVersionCode(getPackageName());
    }

    public static int getAppVersionCode(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean isAppInstalled(String appPackageName) {
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        if (packageInfoList != null) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                if (appPackageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }
}
