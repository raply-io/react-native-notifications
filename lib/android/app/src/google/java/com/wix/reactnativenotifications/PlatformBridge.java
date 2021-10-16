package com.wix.reactnativenotifications;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class PlatformBridge {
    public static void initialize(Application application) {
        FirebaseApp.initializeApp(application.getApplicationContext());
    }

    public static void requestNewToken(Context context, IOnNewToken onNewToken) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> onNewToken.onNewTokenReceived(instanceIdResult.getToken()));
    }

    public interface IOnNewToken {
        void onNewTokenReceived(String token);
    }
}
