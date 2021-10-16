package com.wix.reactnativenotifications.fcm;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.wix.reactnativenotifications.BuildConfig;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotification;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

public class CrossImplFcmInstanceIdListenerService {
    public void onMessageReceived(Application application, Bundle bundle) {
        try {
            final IPushNotification notification = PushNotification.get(application, bundle);
            notification.onReceived();
        } catch (IPushNotification.InvalidNotificationException e) {
            // An FCM message, yes - but not the kind we know how to work with.
            if(BuildConfig.DEBUG) Log.v(LOGTAG, "FCM message handling aborted", e);
        }
    }
}
