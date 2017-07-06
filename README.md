# [Android] 监听应用前后台切换

## 使用：

复制 [ProcessMonitor](app/src/main/java/com/kwok/appmonitor/ProcessMonitor.java) 类到项目中
然后在 **Application** 初始化
```

    @Override
    public void onCreate() {
        super.onCreate();

        initProcessMonitor();
    }

    private void initProcessMonitor() {
        ProcessMonitor.init(this).setListener(new ProcessMonitor.Listener() {
            @Override
            public void onForeground() {
                // 进入前台逻辑
            }

            @Override
            public void onBackground() {
                // 进入后台逻辑
            }
        });
    }
```

## 感谢
[简书-阿敏其人(http://www.jianshu.com/p/e7f64e6bc2cc)](http://www.jianshu.com/p/e7f64e6bc2cc)