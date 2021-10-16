package com.wix.reactnativenotifications.fcm;

import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.wix.reactnativenotifications.BuildConfig;

import java.util.Map;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

/**
 * Instance-ID + token refreshing handling service. Contacts the FCM to fetch the updated token.
 *
 * @author amitd
 */
public class FcmInstanceIdListenerService extends HmsMessageService {
    CrossImplFcmInstanceIdListenerService fcmInstanceIdListenerService = new CrossImplFcmInstanceIdListenerService();

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FcmToken.get(this).onNewTokenReady();
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> map = message.getDataOfMap();

        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }

        if (BuildConfig.DEBUG) Log.d(LOGTAG, "New message from FCM: " + bundle);


        fcmInstanceIdListenerService.onMessageReceived(getApplication(), bundle);
    }
}
