package com.soundium;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Iterator;
import java.util.Set;

public class PlaylistManagerModule extends ReactContextBaseJavaModule {

    private Callback _callback = null;
    private ReactApplicationContext reactApplicationContext;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WritableMap payload = Arguments.createMap();

            payload.putString("action",  intent.getAction());
            /*payload.putString("artist", intent.getStringExtra("artist"));
            payload.putString("album", intent.getStringExtra("album"));
            payload.putString("track", intent.getStringExtra("track"));*/

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Set<String> keys = bundle.keySet();

                for (String key : keys) {
                    payload.putString(key, bundle.get(key).toString());
                }
            }

            reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("myEvent", payload);
        }
    };

    PlaylistManagerModule (ReactApplicationContext applicationContext) {
        super(applicationContext);
        reactApplicationContext = applicationContext;

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.musicservicecommand");
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.updateprogress");
        iF.addAction("com.google.android.youtube");
        iF.addAction("com.vanced.android.apps.youtube");
        iF.addCategory("com.spotify.mobile.android.ui");

        applicationContext.registerReceiver(broadcastReceiver, iF);
    }

    @NonNull
    @Override
    public String getName() {
        return "PlaylistManagerModule";
    }

    @ReactMethod
    public void setCallback(Callback callback) {
        _callback = callback;
    }

    @ReactMethod
    public void getSongAsync (Callback callback) {
        callback.invoke("Beto");
    }

}
