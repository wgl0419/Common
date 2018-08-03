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

    public static String getName() {
        return getName(getPackageName());
    }


    public static String getName(String packageName) {
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

    public static String getVersionName() {
        return getVersionName(getPackageName());
    }


    public static String getVersionName(String packageName) {
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

    public static int getVersionCode() {
        return getVersionCode(getPackageName());
    }

    public static int getVersionCode(String packageName) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean isInstalled(String appPackageName) {
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
