package com.chape.mynet;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/1/1.
 */

public class App extends Application {
    private static App INSTANCE;
    private static Context context;
    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
