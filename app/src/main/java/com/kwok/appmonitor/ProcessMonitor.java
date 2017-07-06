package com.kwok.appmonitor;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * @author gmf
 * @description 前后台切换监听
 * @date 2017/7/6.
 * <p>
 * 注意：只支持 API 14+ （Android 4.0+）
 * 使用：
 * 在 Application 类中调用 ProcessMonitor.init(application).setListener(listener);
 */
public class ProcessMonitor implements Application.ActivityLifecycleCallbacks {

    private static final String KEY_PROCESS_MONITOR = "ProcessMonitor";
    private static ProcessMonitor mInstance;

    private int mStartCount = 0;
    private boolean mIsForeground = false;
    private Listener mListener;

    private static final long CHECK_DELAY = 500;
    private Handler mHandler = new Handler();
    private Runnable mCheck;

    private ProcessMonitor() {
    }

    public static ProcessMonitor init(Application application) {
        if (mInstance == null) {
            mInstance = new ProcessMonitor();
            application.registerActivityLifecycleCallbacks(mInstance);
        }
        return mInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mStartCount++;
        if (mCheck != null) {
            mHandler.removeCallbacks(mCheck);
        }

        if (!mIsForeground) {
            mIsForeground = true;
            if (mListener != null) {
                Log.w(KEY_PROCESS_MONITOR, " onForeground " + mStartCount);
                mListener.onForeground();
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mStartCount--;
        if (mStartCount == 0) {
            if (mCheck != null) {
                mHandler.removeCallbacks(mCheck);
            }

            mHandler.postDelayed(mCheck = new Runnable() {
                @Override
                public void run() {
                    mIsForeground = false;
                    if (mListener != null) {
                        Log.w(KEY_PROCESS_MONITOR, " onBackground " + mStartCount);
                        mListener.onBackground();
                    }
                }
            }, CHECK_DELAY);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void onForeground();

        void onBackground();
    }

}
