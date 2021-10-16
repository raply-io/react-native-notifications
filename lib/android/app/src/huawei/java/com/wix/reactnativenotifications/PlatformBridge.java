package com.wix.reactnativenotifications;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;

import static com.huawei.hms.push.HmsMessaging.DEFAULT_TOKEN_SCOPE;

public class PlatformBridge {

    public static void initialize(Application application) {
    }

    public static void requestNewToken(Context context, IOnNewToken onNewToken) {

        String appId = getHuaweiAppId(context);
        if (BuildConfig.DEBUG) Log.e("huawei app id ~>", appId);
        
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String token = HmsInstanceId.getInstance(context).getToken(appId, DEFAULT_TOKEN_SCOPE);
                    new Handler(Looper.getMainLooper()).post((Runnable) () -> {
                        onNewToken.onNewTokenReceived(token);
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private static String getHuaweiAppId(Context context) {
        try {
            Log.e("getPackageName", context.getPackageName());
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return String.valueOf(applicationInfo.metaData.getString("com.wix.reactnativenotifications.HUAWEI_APP_ID").replace("HMS_", ""));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public interface IOnNewToken {
        void onNewTokenReceived(String token);
    }
}
