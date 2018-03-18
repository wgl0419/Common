package com.chhd.android.common.util;

import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.util.Log;

import com.chhd.android.common.global.BaseApplication;

import java.lang.reflect.Method;

/**
 * author : 葱花滑蛋
 * date   : 2018/01/05
 * desc   : 应用缓存工具类
 */

public class CacheUtils {

    private CacheUtils() {
    }

    private static Context getContext() {
        return CommonUtils.getApplication();
    }

    /**
     * 安卓原生方法获取应用大小信息
     *
     * @param observer
     */
    public static void getPackageSizeInfo(final IPackageStatsObserver observer) {
        try {
            Class<?> clazz = Class.forName("android.content.pm.PackageManager");
            Method method = clazz.getMethod("getPackageSizeInfo", String.class,
                    android.content.pm.IPackageStatsObserver.class);
            method.invoke(getContext().getPackageManager(), getContext().getPackageName(),
                    new android.content.pm.IPackageStatsObserver.Stub() {
                        @Override
                        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                                throws RemoteException {
                            observer.onGetStatsCompleted(pStats, succeeded);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IPackageStatsObserver {
        void onGetStatsCompleted(PackageStats pStats, boolean succeeded);
    }

    /**
     * 安卓原生方法清空应用缓存，仅在安卓6.0以下有效
     */
    public static void freeStorageAndNotify() {
        try {
            PackageManager packageManager = getContext().getPackageManager();
            Class<?> clazz = Class.forName("android.content.pm.PackageManager");
            Method method = clazz.getMethod("freeStorageAndNotify", long.class,
                    IPackageDataObserver.class);
            method.invoke(packageManager, Long.MAX_VALUE,
                    new android.content.pm.IPackageDataObserver.Stub() {
                        @Override
                        public void onRemoveCompleted(String packageName, boolean succeeded)
                                throws RemoteException {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
