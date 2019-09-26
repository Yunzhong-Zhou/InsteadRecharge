package com.aipay.aipay.activity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.aipay.aipay.R;
import com.aipay.aipay.base.BaseActivity;
import com.aipay.aipay.net.HttpUtils;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.CommonUtil;
import com.aipay.aipay.utils.LocalUserInfo;
import com.aipay.aipay.utils.MyLogger;
import com.squareup.okhttp.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aipay.aipay.net.OkHttpClientManager.HOST;


/**
 * Created by fafukeji01 on 2017/4/25.
 * 注册
 */

public class RegisteredActivity extends BaseActivity {
    private TextView textView, textView1, textView2, textView3;
    private EditText editText1, editText2, editText3, editText4;

    String phonenum = "", password1 = "", password2 = "", code = "", num = "", nickname = "", register_addr = "";
    private TimeCount time;

    boolean isNickName = false;


    String open_id = "";


    String register_agreement = "";
    /**
     * 定位
     */
    // 定位相关
    /*public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();*/

    /**
     * 极验
     */
    private GT3GeetestUtils gt3GeetestUtils;
    private GT3ConfigBean gt3ConfigBean;

    private static String TAG = RegisteredActivity.class.getSimpleName();
    /**
     * api1，需替换成自己的服务器URL
     */
    String captchaURL = HOST + URLs.Registered_API1;
    /**
     * api2，需替换成自己的服务器URL
     */
    String validateURL = HOST + "/admin/geetest/api2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
       /* mImmersionBar.reset()
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
                .init();*/
//        findViewByID_My(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);

        /*// 定位初始化
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();*/

        // TODO 务必在oncreate方法里初始化
        gt3GeetestUtils = new GT3GeetestUtils(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*String string3 = "?lang_type=" + localUserInfo.getLanguage_Type();
        RequestSmsCodeList(string3);//手机号国家代码集合

        textView.setText("+" + localUserInfo.getMobile_State_Code());

        if (!localUserInfo.getCountry_IMG().equals(""))
            Glide.with(RegisteredActivity.this)
                    .load(IMGHOST + localUserInfo.getCountry_IMG())
                    .centerCrop()
//                    .placeholder(R.mipmap.ic_guoqi)//加载站位图
//                    .error(R.mipmap.ic_guoqi)//加载失败
                    .into(title_right);//加载图片
        else
            title_right.setImageResource(R.mipmap.ic_guoqi);*/
    }

    @Override
    protected void initView() {
        textView = findViewByID_My(R.id.textView);
//        textView.setText("+"+localUserInfo.getMobile_State_Code());
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);

        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);

        /*//失去焦点时触发
        editText6.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    MyLogger.i(">>>>>>>>>>" + editText6.getText().toString().trim());
                    if (!editText6.getText().toString().trim().equals("")) {
                        String string = "?nickname=" + editText6.getText().toString().trim();
                        RequestNickName(string);//检测昵称是否可用
                    }
                }
            }
        });*/

    }

    @Override
    protected void initData() {
        open_id = getIntent().getStringExtra("open_id");

//        request(captchaURL);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                //返回登录
                finish();
                break;

            case R.id.textView1:
                //发送验证码
                phonenum = editText1.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    Toast.makeText(this, getString(R.string.registered_h1), Toast.LENGTH_SHORT).show();
                } else {
                    customVerity();//极验
                    /*showProgress(true, "正在获取短信验证码...");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "1");
//                    params.put("code", piccode);
                    RequestCode(params);//获取验证码*/

                    /*showProgress(true, "正在生成图形验证码...");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mobile", phonenum);
                    params.put("type", "1");
                    RequestPicCode(params);*/

                }
                break;
            case R.id.textView2:
                //用户注册协议
                if (LocalUserInfo.getInstance(this).getLanguage_Type().equals("1")) {
                    //英文
                    register_agreement = HOST + "/wechat/article/detail?id=5c0b1d00748ba942a2428eef7b14be8b";
                } else {
                    register_agreement = HOST + "/wechat/article/detail?id=4bfc3e49c1e0304beb173f8cc1a6f3a2";
                }
                if (!register_agreement.equals("")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", register_agreement);
                    CommonUtil.gotoActivityWithData(RegisteredActivity.this, WebContentActivity.class, bundle, false);
                }
                break;
            case R.id.textView3:
                //确认注册
//                MyLogger.i(">>>>>>" + CommonUtil.isRealMachine() + CommonUtil.getIMEI(RegisteredActivity.this));
//                if (CommonUtil.isRealMachine()){
                //是真机
                if (match()) {
                    textView3.setClickable(false);
                    showProgress(true, getString(R.string.registered_h14));
                    HashMap<String, String> params = new HashMap<>();
                    params.put("thirdparty_open_id", open_id);//授权
                    params.put("mobile", phonenum);//手机号
                    params.put("password", password1);//密码（不能小于6位数）
                    params.put("code", code);//手机验证码
//                    params.put("nickname", nickname);//昵称
//                    params.put("invite_code", num);//邀请码
                    params.put("uuid", CommonUtil.getIMEI(RegisteredActivity.this));//IMEI
                    params.put("register_addr", register_addr);//注册地址
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                    RequestRegistered(params);//注册
                }
//                }else {
//                    //不是真机
//                    myToast("该设备不能进行注册");
//                }
                break;
        }
    }

    private boolean match() {
        phonenum = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(phonenum)) {
            myToast(getString(R.string.registered_h1));
            return false;
        }
        code = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            myToast(getString(R.string.registered_h2));
            return false;
        }
        password1 = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(password1)) {
            myToast(getString(R.string.registered_h3));
            return false;
        }
        password2 = editText4.getText().toString().trim();
        if (TextUtils.isEmpty(password2)) {
            myToast(getString(R.string.registered_h4));
            return false;
        }
        if (!password1.equals(password2)) {
            myToast(getString(R.string.registered_h12));
            return false;
        }
        /*num = editText5.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            myToast(getString(R.string.registered_h5));
            return false;
        }

        nickname = editText6.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            myToast(getString(R.string.registered_h11));
            return false;
        }*/
        /*if (isNickName==false){
            myToast("昵称不可用");
            return false;
        }*/
        /*if (register_addr.equals("")) {
            showToast(getString(R.string.registered_position_hint),
                    getString(R.string.app_confirm),
                    getString(R.string.app_cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            register_addr = getString(R.string.registered_noposition) +"";
                            dialog.dismiss();
                        }
                    });
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredActivity.this);
            builder.setMessage(getString(R.string.registered_position_hint));
            builder.setTitle(getString(R.string.app_prompt));
            builder.setPositiveButton(getString(R.string.app_confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getString(R.string.app_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    register_addr = getString(R.string.registered_noposition);
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return false;
        }*/

        return true;
    }

    /*//检测昵称
    private void RequestNickName(String params) {
        OkHttpClientManager.getAsyn(RegisteredActivity.this, URLs.Registered_NickName + params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                isNickName = false;
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>检测昵称" + response);
                isNickName = true;
                myToast(getString(R.string.registered_h13));
            }
        });

    }
    //获取图形验证码
    private void RequestPicCode(HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(RegisteredActivity.this, URLs.RegisteredPicCode, params, new OkHttpClientManager.ResultCallback<PicCodeModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(PicCodeModel response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>验证码" + response);
               *//* time.start();//开始计时
                myToast(getString(R.string.app_sendcode_hint));*//*
                dialog.config(R.layout.dialog_baseedit, 0.8f, Gravity.CENTER,
                        BaseDialog.AnimInType.CENTER, WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT, true).show();
                TextView textView2 = dialog.findViewById(R.id.textView2);
                textView2.setText("请输入图中图形验证码");
                ImageView imageView1 = dialog.findViewById(R.id.imageView1);
                imageView1.setImageBitmap(CommonUtil.stringtoBitmap(response.getCode_url()));
                final EditText editText = dialog.findViewById(R.id.editText);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String piccode = editText.getText().toString().trim();
                        if (!piccode.equals("")) {
                            dialog.dismiss();
                            showProgress(true, "正在获取短信验证码...");
                            HashMap<String, String> params = new HashMap<>();
                            params.put("mobile", phonenum);
                            params.put("type", "1");
                            params.put("code", piccode);
                            RequestCode(params);//获取验证码
                        } else {
                            myToast("请输入图形验证码");
                        }


                    }
                });
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //重新获取图片验证码
                        dialog.dismiss();
                        showProgress(true, "正在重新生成图形验证码...");
                        HashMap<String, String> params = new HashMap<>();
                        params.put("mobile", phonenum);
                        RequestPicCode(params);
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        }, false);

    }*/

    //获取验证码
    private void RequestCode(Map<String, String> params) {
        OkHttpClientManager.postAsyn(RegisteredActivity.this, URLs.Code, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>验证码" + response);
                if (time != null) {
                    time.cancel();
                }
                time = new TimeCount(60000, 1000);//构造CountDownTimer对象
                time.start();//开始计时
                myToast(getString(R.string.app_sendcode_hint));
            }
        }, true);
    }

    //注册
    private void RequestRegistered(Map<String, String> params) {
        OkHttpClientManager.postAsyn(RegisteredActivity.this, URLs.Registered, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView3.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(final String response) {
                MyLogger.i(">>>>>>>>>注册" + response);
                textView3.setClickable(true);
//                localUserInfo.setTime(System.currentTimeMillis() + "");
                /*showToast("该账户尚未激活，请完成人脸识别后进行操作", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mobile",phonenum);
                        CommonUtil.gotoActivityWithData(RegisteredActivity.this,
                                RecordVideoActivity.class,bundle1);
                        dialog.dismiss();
                    }
                });
                hideProgress();*/
                hideProgress();
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);

                    JSONObject jObj1 = jObj.getJSONObject("data");
                    //保存Token
                    String token = jObj1.getString("fresh_token");
                    localUserInfo.setToken(token);
                    //保存用户id
                    final String id = jObj1.getString("id");

                    //保存电话号码
                    String mobile = jObj1.getString("mobile");
                    localUserInfo.setPhoneNumber(mobile);
                    localUserInfo.setPhoneNumber(phonenum);
                    //保存用户昵称
                    String nickname = jObj1.getString("nickname");
                    localUserInfo.setNickname(nickname);

                    localUserInfo.setUserId(id);

                    Bundle bundle = new Bundle();
//                    bundle.putInt("isShowAd", jObj1.getInt("experience"));
                    bundle.putInt("isShowAd", 1);
                    CommonUtil.gotoActivityWithFinishOtherAllAndData(RegisteredActivity.this, MainActivity.class, bundle, true);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

            }
        }, true);

    }

    @Override
    protected void updateView() {
//        titleView.setTitle(getString(R.string.registered_title));
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
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            textView1.setText(RegisteredActivity.this.getString(R.string.app_reacquirecode) + "");
            textView1.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            textView1.setClickable(false);
            textView1.setText(millisUntilFinished / 1000 + RegisteredActivity.this.getString(R.string.app_codethen) + "");
        }
    }


    /**
     * ********************************************定位**********************************************
     */
    /*private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");

        option.setScanSpan(1000);

        option.setOpenGps(true);

        option.setIsNeedAddress(true);

        option.setLocationNotify(true);//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);

        option.SetIgnoreCacheException(false);

//        option.setWifiCacheTimeOut(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
//            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
//
//            String coorType = location.getCoorType();//获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//
//            int errorCode = location.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            String addr = location.getAddrStr();    //获取详细地址信息
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
//            String city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息

            register_addr = addr + "";

            MyLogger.i(">>>>>>>>>>>>获取定位详细信息:" + addr + longitude + ">>>>" + latitude);

            mLocationClient.stop();

        }
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        super.onDestroy();
    }*/

    String user_id = "";
    String gtserver = "";

    private void customVerity() {
        // 配置bean文件，也可在oncreate初始化
        gt3ConfigBean = new GT3ConfigBean();
        // 设置验证模式，1：bind，2：unbind
        gt3ConfigBean.setPattern(1);
        // 设置点击灰色区域是否消失，默认不消息
        gt3ConfigBean.setCanceledOnTouchOutside(false);
        // 设置debug模式，开代理可调试 TODO 线上版本关闭
        gt3ConfigBean.setDebug(false);
        // 设置语言，如果为null则使用系统默认语言
        gt3ConfigBean.setLang(null);
        // 设置webview加载超时
        gt3ConfigBean.setTimeout(9000);
        // 设置webview请求超时
        gt3ConfigBean.setWebviewTimeout(6000);
        // 设置回调监听
        gt3ConfigBean.setListener(new GT3Listener() {

            /**
             * api1结果回调
             * @param result
             */
            @Override
            public void onApi1Result(String result) {
                Log.e(TAG, "GT3BaseListener-->onApi1Result-->" + result);
                if (result != null) {
                    JSONObject jObj;
                    try {
                        jObj = new JSONObject(result);
                        user_id = jObj.getString("user_id");
                        gtserver = jObj.getString("gtserver");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }

            /**
             * 验证码加载完成
             * @param duration 加载时间和版本等信息，为json格式
             */
            @Override
            public void onDialogReady(String duration) {
                Log.e(TAG, "GT3BaseListener-->onDialogReady-->" + duration);
            }

            /**
             * 验证结果
             * @param result
             */
            @Override
            public void onDialogResult(String result) {
                Log.e(TAG, "GT3BaseListener-->onDialogResult-->" + result);
                gt3GeetestUtils.showSuccessDialog();//成功


                JSONObject jObj;
                try {
                    jObj = new JSONObject(result);

                    String geetest_challenge = jObj.getString("geetest_challenge");
                    String geetest_seccode = jObj.getString("geetest_seccode");
                    String geetest_validate = jObj.getString("geetest_validate");

//                    showProgress(true, "正在获取短信验证码...");
                    final HashMap<String, String> params = new HashMap<>();
//                    params.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                    params.put("mobile", phonenum);
                    params.put("type", "1");
                    params.put("geetest_challenge", geetest_challenge);
                    params.put("geetest_seccode", geetest_seccode);
                    params.put("geetest_validate", geetest_validate);
//                    params.put("code", piccode);
                    params.put("user_id", user_id);
                    params.put("gtserver", gtserver);

                    MyLogger.i(">>>>>>>>>" + params.toString());

                    RequestCode(params);//获取验证码


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // 开启api2逻辑
//                new RequestAPI2().execute(result);
            }

            /**
             * api2回调
             * @param result
             */
            @Override
            public void onApi2Result(String result) {
                Log.e(TAG, "GT3BaseListener-->onApi2Result-->" + result);
            }

            /**
             * 统计信息，参考接入文档
             * @param result
             */
            @Override
            public void onStatistics(String result) {
                Log.e(TAG, "GT3BaseListener-->onStatistics-->" + result);
            }

            /**
             * 验证码被关闭
             * @param num 1 点击验证码的关闭按钮来关闭验证码, 2 点击屏幕关闭验证码, 3 点击返回键关闭验证码
             */
            @Override
            public void onClosed(int num) {
                Log.e(TAG, "GT3BaseListener-->onClosed-->" + num);
            }

            /**
             * 验证成功回调
             * @param result
             */
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "GT3BaseListener-->onSuccess-->" + result);
            }

            /**
             * 验证失败回调
             * @param errorBean 版本号，错误码，错误描述等信息
             */
            @Override
            public void onFailed(GT3ErrorBean errorBean) {
                Log.e(TAG, "GT3BaseListener-->onFailed-->" + errorBean.toString());
            }

            /**
             * api1回调
             */
            @Override
            public void onButtonClick() {
                new RequestAPI1().execute();
            }
        });
        gt3GeetestUtils.init(gt3ConfigBean);
        // 开启验证
        gt3GeetestUtils.startCustomFlow();
    }

    /**
     * 请求api1
     */
    class RequestAPI1 extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            String string = HttpUtils.requestGet(captchaURL);
            Log.e(TAG, "doInBackground: " + string);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject parmas) {
            // 继续验证
            Log.i(TAG, "RequestAPI1-->onPostExecute: " + parmas);
            // SDK可识别格式为
            // {"success":1,"challenge":"06fbb267def3c3c9530d62aa2d56d018","gt":"019924a82c70bb123aae90d483087f94","new_captcha":true}
            // TODO 设置返回api1数据，即使为null也要设置，SDK内部已处理
            gt3ConfigBean.setApi1Json(parmas);
            // 继续api验证
            gt3GeetestUtils.getGeetest();
        }
    }

    /**
     * 请求api2
     */
    class RequestAPI2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (!TextUtils.isEmpty(params[0])) {
                return HttpUtils.requestPost(validateURL, params[0]);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "RequestAPI2-->onPostExecute: " + result);
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    if ("success".equals(status)) {
                        gt3GeetestUtils.showSuccessDialog();//成功
                        /*showProgress(true, "正在获取短信验证码...");
                        HashMap<String, String> params = new HashMap<>();
                        params.put("mobile", phonenum);
                        params.put("type", "1");
//                    params.put("code", piccode);
                        RequestCode(params);//获取验证码*/
                    } else {
                        gt3GeetestUtils.showFailedDialog();
                    }
                } catch (Exception e) {
                    gt3GeetestUtils.showFailedDialog();
                    e.printStackTrace();
                }
            } else {
                gt3GeetestUtils.showFailedDialog();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO 销毁资源，务必添加
        gt3GeetestUtils.destory();

        if (time != null) {
            time.cancel();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 横竖屏切换
        gt3GeetestUtils.changeDialogLayout();
    }
}
