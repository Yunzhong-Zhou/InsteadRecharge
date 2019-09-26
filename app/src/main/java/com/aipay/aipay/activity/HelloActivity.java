package com.aipay.aipay.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.aipay.aipay.R;
import com.aipay.aipay.utils.LocalUserInfo;

import java.util.Locale;


/**
 * Created by zyz on 2016/6/17.
 * Email：1125213018@qq.com
 * description：启动页
 */
public class HelloActivity extends Activity {
    private static final String SHARE_APP_TAG = "HelloActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现，建议该方法放在Application的初始化方法中
        SDKInitializer.initialize(getApplicationContext());*/
        //设置无标题
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*//设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/


        Resources resources = getResources();

        // 获取应用内语言
        final Configuration configuration = resources.getConfiguration();
//        Locale locale=configuration.locale;
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (LocalUserInfo.getInstance(this).getLanguage_Type().equals("en")) {
            //设置为英文
            configuration.locale = new Locale("en", "US");
            resources.updateConfiguration(configuration, displayMetrics);
        } else {
            //设置为中文
            configuration.locale = new Locale("zh", "CN");
            resources.updateConfiguration(configuration, displayMetrics);
        }

        // 判断是否是第一次开启应用
        SharedPreferences setting = getSharedPreferences(SHARE_APP_TAG, 0);
        Boolean user_first = setting.getBoolean("FIRST", true);


        /*// 如果是第一次启动，则先进入功能引导页
        if (user_first) {
            setting.edit().putBoolean("FIRST", false).commit();
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            // 如果不是第一次启动app，则正常显示启动屏
            setContentView(R.layout.viewpager_page);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enterHomeActivity();
                }
            }, 2000);
        }*/
        // 如果不是第一次启动app，则正常显示启动屏
        setContentView(R.layout.viewpager_page);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);

    }

    private void enterHomeActivity() {
        if (LocalUserInfo.getInstance(HelloActivity.this).getUserId().equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();*/

        finish();
    }


}
