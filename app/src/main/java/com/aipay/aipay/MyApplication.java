package com.aipay.aipay;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.aipay.aipay.base.ScreenAdaptation;
import com.aipay.aipay.utils.MyLogger;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.hjq.toast.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by zyz on 2018/1/18.
 */

public class MyApplication extends Application {
    // 上下文菜单
    private Context mContext;

    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        mContext = this;
        myApplication = this;
        //腾讯bugly 异常上报初始化-建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(getApplicationContext(), "789711ef96", false);

        //toast初始化
        ToastUtils.init(this);


        //闪验-一键登录初始化
        OneKeyLoginManager.getInstance().init(getApplicationContext(), "XNzZS70G", "FHxcOeco", new InitListener() {
            @Override
            public void getInitStatus(int i, String s) {
                if (i == 1022) {
                    MyLogger.i(">>>>>>闪验-一键登录初始化成功");

                } else {
                    MyLogger.i(">>>>>>闪验-一键登录初始化失败\n" + s);
                }
            }
        });

        new ScreenAdaptation(this, 828, 1792).register();
//        new ScreenAdaptation(this,720,1280).register();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);//方法数超过64k
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
