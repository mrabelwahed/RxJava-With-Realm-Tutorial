package com.ramadan_apps.rxjavawithrealmcache;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Mahmoud Ramadan on 12/22/17.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }
}
