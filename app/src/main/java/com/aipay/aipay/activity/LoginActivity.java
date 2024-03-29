package com.aipay.aipay.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aipay.aipay.R;
import com.aipay.aipay.base.BaseActivity;
import com.aipay.aipay.model.LoginModel;
import com.aipay.aipay.model.OneClickLoginModel;
import com.aipay.aipay.model.UpgradeModel;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.CommonUtil;
import com.aipay.aipay.utils.ConfigUtils;
import com.aipay.aipay.utils.MyLogger;
import com.aipay.aipay.utils.permission.PermissionsActivity;
import com.aipay.aipay.utils.permission.PermissionsChecker;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.chuanglan.shanyan_sdk.utils.DES;
import com.cy.dialog.BaseDialog;
import com.google.gson.Gson;
import com.maning.updatelibrary.InstallUtils;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 登录
 */
public class LoginActivity extends BaseActivity {
    private EditText editText1, editText2;
    private TextView textView1, textView2, textView3;

    private String phonenum = "", password = "";
    private TimeCount time = null;

    //更新
    UpgradeModel model_up;

    private int REQUEST_CODE = 0; // 请求码
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            android.Manifest.permission.CALL_PHONE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            //定位
            /*android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION*/

            /*Manifest.permission.RECORD_AUDIO,
            Manifest.permission.VIBRATE*/

            /*Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.VIBRATE*/
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        mImmersionBar.reset().init();

        setSwipeBackEnable(false); //主 activity 可以调用该方法，禁止滑动删除

        mPermissionsChecker = new PermissionsChecker(this);
    }


    @Override
    protected void initView() {

        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);

    }

    @Override
    protected void initData() {
        RequestUpgrade("?app_type=" + 1);//检查更新

        //闪验-一键登录预取号
        OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
            @Override
            public void getPhoneInfoStatus(int i, String s) {
                if (i == 1022) {
                    MyLogger.i(">>>>>>闪验-一键登录预取号成功\n" + s);
                } else {
                    MyLogger.i(">>>>>>闪验-一键登录预取号失败\n" + s);
//                    getPhoneInfo(){"resultCode":"200010","resultDesc":"imsi获取失败或者没有sim卡，预取号失败"}

                }
            }
        });
        //自定 商授 界面
        OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getCJSConfig(getApplicationContext()));


        /*byte[] mBytes = null;
        String mString = "{阿达大as家阿sdf什顿附asd件好久}";
        AES mAes = new AES();
        try {
            mBytes = mString.getBytes("UTF-8");
        } catch (Exception e) {
            Log.i("qing", "MainActivity----catch");
        }
        String enString = mAes.encrypt(mBytes);
        MyLogger.i("加密后：" + enString);
        String deString = mAes.decrypt("P9ezA6lsRKVID383Rg5mwQ==");
        MyLogger.i("解密后：" + deString);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                //忘记密码
                CommonUtil.gotoActivity(LoginActivity.this, ForgetPasswordActivity.class, false);
                break;
            case R.id.textView2:
                //注册
                Bundle bundle1 = new Bundle();
                bundle1.putString("open_id", "");
                CommonUtil.gotoActivityWithData(LoginActivity.this, RegisteredActivity.class, bundle1, false);
                break;
            case R.id.textView3:
                //确认登录
                if (match()) {
                    textView3.setClickable(false);
                    this.showProgress(true, getString(R.string.login_h7));
                    params.put("uuid", CommonUtil.getIMEI(LoginActivity.this));//IMEI
                    params.put("mobile", phonenum);
                    params.put("password", password);
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                    RequestLogin(params);//登录
                }
//                CommonUtil.gotoActivity(LoginActivity.this, MainActivity.class, true);
                break;
            case R.id.textView4:

                //闪验-一键登录拉起授权页
                OneKeyLoginManager.getInstance().openLoginAuth(true, new OpenLoginAuthListener() {
                    @Override
                    public void getOpenLoginAuthStatus(int code, String result) {
                        if (code == 1000) {
                            MyLogger.i(">>>>>>闪验-一键登录拉起授权页成功");
                        } else {
                            MyLogger.i(">>>>>>闪验-一键登录拉起授权页失败\n" + result);
                            if(result.indexOf("{") != -1) {
                                System.out.println("包含该字符串");
                                String newresult = result.substring(result.indexOf("{"));//截取之后的字符串
                                MyLogger.i(">>>>>>"+newresult);
                                try {
                                    JSONObject mJsonObject = new JSONObject(newresult);
                                    String resultDesc = mJsonObject.getString("resultDesc");
                                    myToast(resultDesc);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();

                                }
                            }else {
                                myToast(result);
                            }
                            //loginAuth(){"resultCode":"200048","authType":"0","authTypeDes":"其他","resultDesc":"手机未安装SIM卡"}

                        }
                    }
                }, new OneKeyLoginListener() {
                    @Override
                    public void getOneKeyLoginStatus(int code, String result) {
                        if (code == 1000) {
                            //解析
//                            myToast("一键登录成功");
                            showProgress(true, getString(R.string.login_h7));
                            Gson gson = new Gson();
                            OneClickLoginModel oneClickLoginModel = gson.fromJson(result, OneClickLoginModel.class);
                            MyLogger.i(">>>>>>闪验-一键登录成功\n" + result);
                            Map<String, String> params = new HashMap<>();
                            params.put("appId", oneClickLoginModel.getAppId());
                            params.put("accessToken", oneClickLoginModel.getAccessToken());
                            params.put("telecom", oneClickLoginModel.getTelecom());
                            params.put("timestamp", oneClickLoginModel.getTimestamp());
                            params.put("randoms", oneClickLoginModel.getRandoms());
                            params.put("sign", oneClickLoginModel.getSign());
                            params.put("version", oneClickLoginModel.getVersion());
                            params.put("device", oneClickLoginModel.getDevice());

                            if (oneClickLoginModel.getTelecom().equals("CMCC")) {//移动
                                RequestOneClickLogin(params, "https://api.253.com/open/flashsdk/mobile-query-m");
//                                RequestOneClickLogin(params,OkHttpClientManager.HOST+"/api/member/quick-login");
                            } else if (oneClickLoginModel.getTelecom().equals("CUCC")) {//联通
                                RequestOneClickLogin(params, "https://api.253.com/open/flashsdk/mobile-query-u");
                            } else if (oneClickLoginModel.getTelecom().equals("CTCC")) {//电信
                                RequestOneClickLogin(params, "https://api.253.com/open/flashsdk/mobile-query-t");
                            }

                        } else {
                            MyLogger.i(">>>>>>闪验-一键登录失败\n" + result);

                        }
                    }
                });
                break;
        }
    }

    //登录
    private void RequestLogin(Map<String, String> params) {
        OkHttpClientManager.postAsyn(LoginActivity.this, URLs.Login, params, new OkHttpClientManager.ResultCallback<LoginModel>() {
            @Override
            public void onError(final Request request, String info, Exception e) {
                hideProgress();
                textView3.setClickable(true);
//                myToast("密码错误，请重新输入");
                if (!info.equals("")) {
                    if (info.equals("100")) {
                        MyLogger.i(">>>>>>>>" + e.getMessage());
                        //特殊情况-登录后手机不是注册时的手机
                        dialog = new BaseDialog(LoginActivity.this);
                        dialog.contentView(R.layout.dialog_loginerror)
                                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT))
                                .animType(BaseDialog.AnimInType.CENTER)
                                .canceledOnTouchOutside(true)
                                .dimAmount(0.8f)
                                .show();
//                        TextView textView1 = dialog.findViewById(R.id.textView1);
//                        textView1.setText(e.getMessage());
                        final TextView tv2 = dialog.findViewById(R.id.textView2);

                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //发送验证码
                                if (time != null) {
                                    time.cancel();
                                }
                                time = new TimeCount(60000, 1000, tv2);//构造CountDownTimer对象

                                LoginActivity.this.showProgress(true, getString(R.string.app_sendcode_hint1));
                                tv2.setClickable(false);
                                /*String string = "?mobile=" + phonenum +
                                        "&type=" + "10";//类型*/
                                HashMap<String, String> params = new HashMap<>();
                                params.put("mobile", phonenum);
                                params.put("type", "10");
                                params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                                RequestCode(params, tv2);//获取验证码
                            }
                        });
                        final EditText editText1 = dialog.findViewById(R.id.editText1);
                        dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!editText1.getText().toString().trim().equals("")) {
                                    CommonUtil.hideSoftKeyboard_fragment(v, LoginActivity.this);
                                    dialog.dismiss();
                                    showProgress(true, getString(R.string.login_h10));
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("uuid", CommonUtil.getIMEI(LoginActivity.this));//IMEI
                                    params.put("code", editText1.getText().toString().trim());
                                    params.put("mobile", phonenum);
                                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                                    RequestAuthorization(params);
                                } else {
                                    myToast(getString(R.string.login_h2));
                                }
                            }
                        });
                        dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    } else if (info.equals("102")) {
                        //该账户尚未激活，请完成人脸识别后进行操作
                        showToast(getString(R.string.login_h13), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /*Bundle bundle1 = new Bundle();
                                bundle1.putString("mobile", phonenum);
                                CommonUtil.gotoActivityWithData(LoginActivity.this, RecordVideoActivity.class, bundle1);
                                dialog.dismiss();*/
                            }
                        });
                    } else {
                        myToast(info);
                    }
                }
            }

            @Override
            public void onResponse(final LoginModel response) {
                MyLogger.i(">>>>>>>>>登录" + response);
                textView3.setClickable(true);
                localUserInfo.setTime(System.currentTimeMillis() + "");
                if (response.getStatus() == 3) {
                    //封号
                    /*showToast(getString(R.string.login_h9),
                            getString(R.string.app_confirm),
                            getString(R.string.app_cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //保存Token
//                                String token = jObj.getString("token");
                                    localUserInfo.setToken(response.getFresh_token());
                                    CommonUtil.gotoActivity(LoginActivity.this, OnlineServiceActivity.class, false);
                                    dialog.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });*/
                } else {
                    //保存Token
//                            String token = jObj1.getString("fresh_token");
                    localUserInfo.setToken(response.getFresh_token());
//                            String id = jObj1.getString("id");
                    localUserInfo.setUserId(response.getId());
                    //保存电话号码
//                            String mobile = jObj1.getString("mobile");
                    localUserInfo.setPhoneNumber(response.getMobile());
//                            localUserInfo.setPhoneNumber(phonenum);
                    //保存用户昵称
//                            String nickname = jObj1.getString("nickname");
                    localUserInfo.setNickname(response.getNickname());

                    //是否为商户
//                    localUserInfo.setMerchant(response.getMerchant() + "");
                    //是否开通支付
//                    localUserInfo.setPay(response.getPay() + "");
                    //是否开通收款
//                    localUserInfo.setGather(response.getGather() + "");

                    //保存钱包地址
//                        String walletaddr = jObj1.getString("wallet_addr");
//                        localUserInfo.setWalletaddr(walletaddr);
                    //保存邮箱
//                        String email = jObj1.getString("email");
//                        localUserInfo.setEmail(email);
                    //保存姓名
//                    localUserInfo.setUserName(jObj1.getString("name"));
                    //保存头像
//                        localUserInfo.setUserImage(jObj1.getString("head"));

                    hideProgress();
                    CommonUtil.gotoActivityWithFinishOtherAll(LoginActivity.this, MainActivity.class, true);

                }
            }
        }, true);

    }

    //获取验证码
    private void RequestCode(HashMap<String, String> params, final TextView tv) {
        OkHttpClientManager.postAsyn(LoginActivity.this, URLs.Code, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                tv.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>验证码" + response);
                tv.setClickable(true);
                time.start();//开始计时
                myToast(getString(R.string.app_sendcode_hint));
            }
        }, true);

    }

    //设备授权
    private void RequestAuthorization(HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(LoginActivity.this, URLs.Login_Authorization, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>授权" + response);
                HashMap<String, String> params = new HashMap<>();
                params.put("uuid", CommonUtil.getIMEI(LoginActivity.this));//IMEI
                params.put("mobile", phonenum);
                params.put("password", password);
                params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                RequestLogin(params);//登录
            }
        });
    }

    private boolean match() {
        phonenum = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(phonenum)) {
            myToast(getString(R.string.login_h1));
            return false;
        }
        password = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            myToast(getString(R.string.login_h2));
            return false;
        }

        return true;
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }

    //屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    //获取验证码倒计时
    class TimeCount extends CountDownTimer {
        TextView tv;

        public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.tv = tv;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tv.setText(getString(R.string.app_reacquirecode));
            tv.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv.setClickable(false);
            tv.setText(millisUntilFinished / 1000 + getString(R.string.app_codethen));
        }
    }

    private void RequestUpgrade(String string) {
        /*OkhttpUtil.okHttpGet(HOST + URLs.Upgrade, new CallBackUtil<UpgradeModel>() {

            @Override
            public UpgradeModel onParseResponse(Call call, Response response) {
                MyLogger.i(">>>>>>"+response.toString());
                return null;
            }

            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(UpgradeModel response) {
                MyLogger.i(">>>>>>"+response.toString());
            }
        });*/

        OkHttpClientManager.getAsyn(LoginActivity.this, URLs.Upgrade + string, new OkHttpClientManager.ResultCallback<UpgradeModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                hideProgress();
            }

            @Override
            public void onResponse(UpgradeModel response) {
                MyLogger.i(">>>>>>>>>更新" + response);
//                hideProgress();
                model_up = response;
                if (Integer.valueOf(CommonUtil.getVersionCode(LoginActivity.this)) < Integer.valueOf(response.getVersion_code())) {
//                    handler1.sendEmptyMessage(0);
                    showUpdateDialog();
                } else {
//                    showToast("已经是最新版，无需更新");
                }
            }
        });
    }

    //显示是否要更新的对话框
    private void showUpdateDialog() {
        dialog.contentView(R.layout.dialog_upgrade)
                .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT))
                .animType(BaseDialog.AnimInType.CENTER)
                .canceledOnTouchOutside(true)
                .dimAmount(0.8f)
                .show();
        TextView textView1 = dialog.findViewById(R.id.textView1);
        TextView textView2 = dialog.findViewById(R.id.textView2);
        TextView textView3 = dialog.findViewById(R.id.textView3);
        TextView textView4 = dialog.findViewById(R.id.textView4);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                        /*Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(model_up.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);*/
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);    //进度条，在下载的时候实时更新进度，提高用户友好度
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setCancelable(false);//点击外部不消失，返回键没用
//        progressDialog.setCanceledOnTouchOutside(false);//点击外部不消失，返回键有用
                    progressDialog.setTitle(getString(R.string.update_hint3));
                    progressDialog.setMessage(getString(R.string.update_hint4));
                    progressDialog.setProgress(0);
                    progressDialog.show();

                    //下载APK
                    InstallUtils.with(LoginActivity.this)
                            //必须-下载地址
                            .setApkUrl(model_up.getUrl())
                            //非必须-下载保存的文件的完整路径+/name.apk，使用自定义路径需要获取读写权限
//                                    .setApkPath(Constants.APK_SAVE_PATH)
                            //非必须-下载回调
                            .setCallBack(new InstallUtils.DownloadCallBack() {
                                @Override
                                public void onStart() {
                                    //下载开始
                                }

                                @Override
                                public void onComplete(final String path) {
                                    progressDialog.cancel();
                                    //下载完成
                                    //先判断有没有安装权限---适配8.0
                                    //如果不想用封装好的，可以自己去实现8.0适配
                                    InstallUtils.checkInstallPermission(LoginActivity.this, new InstallUtils.InstallPermissionCallBack() {
                                        @Override
                                        public void onGranted() {
                                            //去安装APK
                                            //一加手机8.0碰到了安装解析失败问题请添加下面判断
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                //先获取是否有安装未知来源应用的权限
                                                boolean haveInstallPermission = LoginActivity.this.getPackageManager().canRequestPackageInstalls();
                                                if (!haveInstallPermission) {
                                                    //跳转设置开启允许安装
                                                    Uri packageURI = Uri.parse("package:" + LoginActivity.this.getPackageName());
                                                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                                    startActivityForResult(intent, 1000);
                                                    return;
                                                }
                                            }
                                            InstallUtils.installAPK(LoginActivity.this, path, new InstallUtils.InstallCallBack() {
                                                @Override
                                                public void onSuccess() {
                                                    myToast(getString(R.string.update_hint5));
                                                }

                                                @Override
                                                public void onFail(Exception e) {
                                                    myToast(getString(R.string.update_hint6) + e.toString());
                                                }
                                            });
                                        }

                                        @Override
                                        public void onDenied() {
                                            //弹出弹框提醒用户
                                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                                                    .setTitle(getString(R.string.update_hint7))
                                                    .setMessage(getString(R.string.update_hint8))
                                                    .setNegativeButton(getString(R.string.app_cancel), null)
                                                    .setPositiveButton(getString(R.string.update_hint9), new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //打开设置页面
                                                            InstallUtils.openInstallPermissionSetting(LoginActivity.this, new InstallUtils.InstallPermissionCallBack() {
                                                                @Override
                                                                public void onGranted() {
                                                                    //去安装APK
                                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                        //先获取是否有安装未知来源应用的权限
                                                                        boolean haveInstallPermission = LoginActivity.this.getPackageManager().canRequestPackageInstalls();
                                                                        if (!haveInstallPermission) {
                                                                            //跳转设置开启允许安装
                                                                            Uri packageURI = Uri.parse("package:" + LoginActivity.this.getPackageName());
                                                                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                                                            startActivityForResult(intent, 1000);
                                                                            return;
                                                                        }
                                                                    }
                                                                    InstallUtils.installAPK(LoginActivity.this, path, new InstallUtils.InstallCallBack() {
                                                                        @Override
                                                                        public void onSuccess() {
                                                                            myToast(getString(R.string.update_hint5));
                                                                        }

                                                                        @Override
                                                                        public void onFail(Exception e) {
                                                                            myToast(getString(R.string.update_hint6) + e.toString());
                                                                        }
                                                                    });
                                                                }

                                                                @Override
                                                                public void onDenied() {
                                                                    //还是不允许咋搞？
                                                                    finish();
//                                                                            Toast.makeText(MainActivity.this, "不允许安装咋搞？强制更新就退出应用程序吧！", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    })
                                                    .create();
                                            alertDialog.show();
                                        }
                                    });

                                }

                                @Override
                                public void onLoading(long total, long current) {
                                    //下载中
                                    progressDialog.setMax((int) total);
                                    progressDialog.setProgress((int) current);
                                }

                                @Override
                                public void onFail(Exception e) {
                                    //下载失败
                                }

                                @Override
                                public void cancle() {
                                    //下载取消
                                }
                            })
                            //开始下载
                            .startDownload();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.update_hint1),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        /*showToast(getString(R.string.update_hint) + model_up.getVersion_title(),
                getString(R.string.app_confirm_true),
                getString(R.string.app_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        finish();
                    }
                });*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }


    private void RequestOneClickLogin(Map<String, String> params, String url) {
        OkHttpClientManager.postAsyn(url, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
//                hideProgress();
                MyLogger.i(">>>>>>>>>获取手机号接口" + response);
                try {
                    if (dialog != null) {
                        dialog.cancel();
                    }
                    JSONObject json = new JSONObject(response);
                    int code = json.optInt("code");
                    if (code == 200000) {
                        JSONObject data = json.getJSONObject("data");
                        String mobileName = data.optString("mobileName");
                        //获取到的手机号
                        String phone = DES.decryptDES(mobileName, "FHxcOeco");
                        MyLogger.i(">>>>>>" + phone);
                        Map<String, String> params = new HashMap<>();
                        params.put("uuid", CommonUtil.getIMEI(LoginActivity.this));//IMEI
                        params.put("mobile", phone);
                        RequestOneClickLogin1(params);
                    } else {
                        String msg = json.optString("message");
                        myToast(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void RequestOneClickLogin1(Map<String, String> params) {
        OkHttpClientManager.postAsyn(LoginActivity.this, URLs.Login1, params, new OkHttpClientManager.ResultCallback<LoginModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(LoginModel response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>一键登录" + response);
                //保存Token
//                            String token = jObj1.getString("fresh_token");
                localUserInfo.setToken(response.getFresh_token());
//                            String id = jObj1.getString("id");
                localUserInfo.setUserId(response.getId());
                //保存电话号码
//                            String mobile = jObj1.getString("mobile");
                localUserInfo.setPhoneNumber(response.getMobile());
//                            localUserInfo.setPhoneNumber(phonenum);
                //保存用户昵称
//                            String nickname = jObj1.getString("nickname");
                localUserInfo.setNickname(response.getNickname());

                hideProgress();
                CommonUtil.gotoActivityWithFinishOtherAll(LoginActivity.this, MainActivity.class, true);
            }
        }, true);
    }
}
