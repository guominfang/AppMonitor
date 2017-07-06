package com.kwok.appmonitor;

import android.app.Application;

/**
 * @author gmf
 * @description
 * @date 2017/7/6.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        initProcessMonitor();
    }

    private void initProcessMonitor() {
        ProcessMonitor.init(this).setListener(new ProcessMonitor.Listener() {
            @Override
            public void onForeground() {
                // 进入前台
            }

            @Override
            public void onBackground() {
                // 进入后台
            }
        });
    }
}
