package com.aipay.aipay.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aipay.aipay.R;
import com.aipay.aipay.adapter.Pop_ListAdapter;
import com.aipay.aipay.base.BaseActivity;
import com.aipay.aipay.model.CollectionSettingModel;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.popupwindow.FixedPopupWindow;
import com.aipay.aipay.utils.ChooseImages_zyz;
import com.aipay.aipay.utils.FileUtil;
import com.aipay.aipay.utils.MyLogger;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;

import static com.aipay.aipay.utils.ChooseImages_zyz.REQUEST_CODE_CAPTURE_CAMEIA;
import static com.aipay.aipay.utils.ChooseImages_zyz.REQUEST_CODE_PICK_IMAGE;


/**
 * Created by zyz on 2017/9/16.
 * 收款设置
 */

public class BankCardSettingActivity extends BaseActivity {
    CollectionSettingModel model;
    //    String qk = "";
    EditText editText, editText1, editText2, editText3, editText4, editText5, editText6, editText7;
    ImageView imageView, imageView1, imageView2;
    LinearLayout linearLayout1, linearLayout2;
    TextView textView, textView1, textView2, textView3, textView4;
    private TimeCount time;
    String alipay_account = "", alipay_proceeds_name = "", bank_card_account = "",
            bank_card_proceeds_name = "", bank_title = "", code = "", bank_address = "";
    String type = "";
    String password = "";

    boolean isShow = true;
    int proceeds_display = 1;//显示

    List<CollectionSettingModel.BankListBean> banklist = new ArrayList<>();
    int i1 = 0;

    CityConfig cityConfig = null;
    //开户行控件 申明对象
    CityPickerView mPicker = new CityPickerView();
    String bank_address_temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcardsetting);

        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
        cityConfig = new CityConfig.Builder()
                .title("选择银行开户地址")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#eaeaea")//标题栏背景色
                .confirTextColor("#5383D1")//确认按钮文字颜色
                .confirmText("确定")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#6782A4")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(7)//显示item的数量
                .province("重庆市")//默认显示的省份
                .city("重庆市")//默认显示省份下面的城市
                .district("南岸区")//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
                .setCustomItemTextViewId(R.id.textView1)//自定义item布局里面的textViewid
                .drawShadows(true)//滚轮不显示模糊效果
                .setLineColor("#80CDCDCE")//中间横线的颜色
                .setLineHeigh(1)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();

        //设置自定义的属性配置
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //省份province//城市city//地区district
                textView4.setText(province.getName().toString() +
                        city.getName().toString() +
                        district.getName().toString());
                bank_address_temp = province.getName().toString() + "#" +
                        city.getName().toString();
            }

            @Override
            public void onCancel() {
//                ToastUtils.showLongToast(this, "已取消");
            }
        });

    }

    @Override
    protected void initView() {
        editText = findViewByID_My(R.id.editText);
        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);
        editText5 = findViewByID_My(R.id.editText5);
        editText6 = findViewByID_My(R.id.editText6);
        imageView = findViewByID_My(R.id.imageView);
        imageView1 = findViewByID_My(R.id.imageView1);
        imageView2 = findViewByID_My(R.id.imageView2);
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);
        textView1.setText(localUserInfo.getPhonenumber());
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);

        //开户行
        editText7 = findViewByID_My(R.id.editText7);
        textView4 = findViewByID_My(R.id.textView4);
    }

    @Override
    protected void initData() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

//        qk = getIntent().getStringExtra("qk");
        showProgress(true, getString(R.string.app_loading2));
        //获取收款设置
        RequestGetCollection("?token=" + localUserInfo.getToken());
    }

    //获取收款设置
    private void RequestGetCollection(String string) {
        OkHttpClientManager.getAsyn(BankCardSettingActivity.this, URLs.Collection + string, new OkHttpClientManager.ResultCallback<CollectionSettingModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(CollectionSettingModel response) {
                hideProgress();
                MyLogger.i(">>>>>>>>银行卡设置" + response);
                model = response;
                banklist = response.getBank_list();
                textView.setText(response.getMember().getBank_title());//开户行
                editText2.setText(response.getMember().getBank_card_proceeds_name());//开户名
                editText3.setText(response.getMember().getBank_card_account());//银行卡号

                if (!response.getMember().getBank_card_proceeds_name().equals("")) {
                    editText2.setEnabled(false);
                } else {
                    editText2.setEnabled(true);
                }
//                editText4.setText(response.getMember().getAlipay_account());//支付宝账号
//                editText6.setText(response.getMember().getAlipay_proceeds_name());//支付宝收款人
                /*//微信二维码
                if (!response.getMember().getWechat_proceeds_qrcode().equals("")) {
                    Glide.with(BankCardSettingActivity.this).load(IMGHOST + response.getMember().getWechat_proceeds_qrcode())
                            .into(imageView1);//加载图片
                    imageView1.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.GONE);
                } else {
                    imageView1.setVisibility(View.GONE);
                    linearLayout1.setVisibility(View.VISIBLE);
                }
                //支付宝二维码
                if (!response.getMember().getAlipay_proceeds_qrcode().equals("")) {
                    Glide.with(BankCardSettingActivity.this).load(IMGHOST + response.getMember().getAlipay_proceeds_qrcode())
                            .into(imageView2);//加载图片
                    imageView2.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.GONE);
                } else {
                    imageView2.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                }

                //是否显示
                if (response.getMember().getProceeds_display() == 1) {
                    proceeds_display = 1;
                    imageView.setImageResource(R.mipmap.ic_mianmi_1);
                } else {
                    proceeds_display = 2;
                    imageView.setImageResource(R.mipmap.ic_mianmi_0);
                }*/

                //显示开户行
                String[] strings = response.getMember().getBank_address().split("#");
                if (strings.length > 2) {
                    textView4.setText(strings[0] + strings[1]);
                    editText7.setText(strings[2]);
                    bank_address_temp = strings[0] + "#" + strings[1];
                } else if (strings.length == 2) {
                    textView4.setText(strings[0] + strings[1]);
                    editText7.setText("");
                    bank_address_temp = strings[0] + "#" + strings[1];
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                //选择银行
                MyLogger.i(">>>>>>");
                showPopupWindow1(textView);
                break;
            case R.id.linearLayout1:
                //微信收款二维码
                type = "wechat_proceeds_qrcode";
                ChooseImages_zyz.showPhotoDialog(BankCardSettingActivity.this);
                break;
            case R.id.linearLayout2:
                //支付宝收款二维码
                type = "alipay_proceeds_qrcode";
                ChooseImages_zyz.showPhotoDialog(BankCardSettingActivity.this);
                break;
            case R.id.imageView1:
                //微信收款二维码
                type = "wechat_proceeds_qrcode";
                ChooseImages_zyz.showPhotoDialog(BankCardSettingActivity.this);
                break;
            case R.id.imageView2:
                //支付宝收款二维码
                type = "alipay_proceeds_qrcode";
                ChooseImages_zyz.showPhotoDialog(BankCardSettingActivity.this);
                break;
            case R.id.textView2:
                //获取验证码
                /*String string = "?mobile=" + localUserInfo.getPhonenumber() +
                        "&type=" + "7" +
                        "&mobile_state_code=" + localUserInfo.getMobile_State_Code();*/
                showProgress(false, "正在获取验证码...");
                textView2.setClickable(false);
                HashMap<String, String> params1 = new HashMap<>();
                params1.put("mobile", localUserInfo.getPhonenumber());
                params1.put("type", "7");
                params1.put("mobile_state_code", localUserInfo.getMobile_State_Code());
                RequestCode(params1);//获取验证码
                break;
            case R.id.textView3:
                //提交
                if (match()) {
                    if (model.getMember().getBank_card_proceeds_name().equals("")) {
                        showToast("收款人姓名不能更改，确认提交吗？",
                                "确认",
                                "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        showProgress(false, getString(R.string.app_loading1));
                                        String[] filenames = new String[]{};
                                        File[] files = new File[]{};
                                        for (int i = 0; i < listFiles.size(); i++) {
                                            filenames = listFileNames.toArray(new String[i]);
                                            files = listFiles.toArray(new File[i]);
                                        }
                                        HashMap<String, String> params = new HashMap<>();
//                    params.put("qk", qk);
//                    params.put("alipay_account", alipay_account);//支付宝账号
//                    params.put("alipay_proceeds_name", alipay_proceeds_name);//支付宝收款人姓名
                                        params.put("bank_card_account", bank_card_account);//银行卡账号
                                        params.put("bank_card_proceeds_name", bank_card_proceeds_name);//银行卡收款人姓名
                                        params.put("bank_title", bank_title);//银行名称
                                        params.put("code", code);//手机验证码
                                        params.put("token", localUserInfo.getToken());
//                                        params.put("trade_password", password);//交易密码（不能小于6位数）
//                    params.put("proceeds_display", proceeds_display + "");//是否显示收款信息
                                        params.put("bank_address", bank_address + "");//开户行
                                        RequestCollectionSetting(filenames, files, params);//

                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                    } else {
                        showProgress(false, getString(R.string.app_loading1));
                        String[] filenames = new String[]{};
                        File[] files = new File[]{};
                        for (int i = 0; i < listFiles.size(); i++) {
                            filenames = listFileNames.toArray(new String[i]);
                            files = listFiles.toArray(new File[i]);
                        }
                        HashMap<String, String> params = new HashMap<>();
//                    params.put("qk", qk);
//                    params.put("alipay_account", alipay_account);//支付宝账号
//                    params.put("alipay_proceeds_name", alipay_proceeds_name);//支付宝收款人姓名
                        params.put("bank_card_account", bank_card_account);//银行卡账号
                        params.put("bank_card_proceeds_name", bank_card_proceeds_name);//银行卡收款人姓名
                        params.put("bank_title", bank_title);//银行名称
                        params.put("code", code);//手机验证码
                        params.put("token", localUserInfo.getToken());
//                        params.put("trade_password", password);//交易密码（不能小于6位数）
//                    params.put("proceeds_display", proceeds_display + "");//是否显示收款信息
                        params.put("bank_address", bank_address + "");//开户行
                        RequestCollectionSetting(filenames, files, params);//
                    }

                }


                break;
            case R.id.textView4:
                //开户行
                mPicker.showCityPicker();
                break;
            case R.id.imageView:
                //显示
                /*isShow = !isShow;
                if (isShow) {
                    proceeds_display = 1;
                    imageView.setImageResource(R.mipmap.ic_mianmi_1);
                } else {
                    proceeds_display = 2;
                    imageView.setImageResource(R.mipmap.ic_mianmi_0);
                }*/
                break;
        }
    }

    //发送验证码
    private void RequestCode(HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(BankCardSettingActivity.this, URLs.Collection_code, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView2.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                time.start();
                MyLogger.i(">>>>>>>>>发送验证码" + response);
                myToast(getString(R.string.app_sendcode_hint));
            }
        }, true);

    }

    //收款设置
    private void RequestCollectionSetting(String[] fileKeys, File[] files, HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(BankCardSettingActivity.this, URLs.Collection, fileKeys, files, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    /*if (info.contains("交易密码")) {
                        showToast("交易密码错误或未设置，是否前往设置？",
                                "确认", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        CommonUtil.gotoActivity(BankCardSettingActivity.this, SetTransactionPasswordActivity.class, false);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                    } else if (info.contains("绑定银行卡")) {
                        showToast("请绑定银行卡",
                                "确认", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        CommonUtil.gotoActivity(BankCardSettingActivity.this, BankCardSettingActivity.class, false);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                    } else {
                        showToast(info);
                    }*/
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>收款设置" + response);
                myToast(getString(R.string.setting_h18));
                finish();
            }
        }, true);

    }

    private boolean match() {
//        bank_title = editText1.getText().toString().trim();
        bank_title = textView.getText().toString().trim();
        if (TextUtils.isEmpty(bank_title)) {
            myToast("请选择银行名称");
            return false;
        }
        bank_card_proceeds_name = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(bank_card_proceeds_name)) {
            myToast("请输入收款人姓名");
            return false;
        }
        bank_card_account = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(bank_card_account)) {
            myToast("请输入银行账号");
            return false;
        }
        alipay_account = editText4.getText().toString().trim();
        /*if (TextUtils.isEmpty(alipay_account)) {
            myToast("请输入支付宝账号");
            return false;
        }*/
        alipay_proceeds_name = editText6.getText().toString().trim();
        /*if (TextUtils.isEmpty(alipay_proceeds_name)) {
            myToast("请输入支付宝收款人姓名");
            return false;
        }*/
        code = editText5.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            myToast(getString(R.string.setting_h16));
            return false;
        }
        /*password = editText.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            myToast(getString(R.string.takecash_h10));
            return false;
        }*/

        if (!bank_address_temp.equals("")) {
            bank_address = bank_address_temp;
            /*if (editText7.getText().toString().trim().equals("")) {
                bank_address = bank_address_temp + "#" + "支行";
            } else {
                bank_address = bank_address_temp + "#" + editText7.getText().toString().trim();
            }*/
        } else {
//            bank_address = "";
            myToast("请选择银行开户地址");
            return false;
        }

        /*if (listFiles.size() != 1) {
            myToast("请按提示上传图片");
            return false;
        }*/
        return true;
    }


    @Override
    protected void updateView() {
        titleView.setTitle(getString(R.string.setting_h1));
    }

    //获取验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            textView2.setText(getString(R.string.app_reacquirecode));
            textView2.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            textView2.setClickable(false);
            textView2.setText(millisUntilFinished / 1000 + getString(R.string.app_codethen));
        }
    }

    /**
     * *****************************************选择图片********************************************
     */
    //选择图片及上传
    ArrayList<String> listFileNames = new ArrayList<>();
    ArrayList<File> listFiles = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = null;
            String imagePath = null;
            switch (requestCode) {
                case REQUEST_CODE_CAPTURE_CAMEIA:
                    //相机
                    uri = Uri.parse("");
                    uri = Uri.fromFile(new File(ChooseImages_zyz.imagepath));
                    imagePath = uri.getPath();
                    break;
                case REQUEST_CODE_PICK_IMAGE:
                    //相册
                    uri = data.getData();
                    //处理得到的url
                    ContentResolver cr = this.getContentResolver();
                    Cursor cursor = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        cursor = cr.query(uri, null, null, null, null, null);
                    }
                    if (cursor != null) {
                        cursor.moveToFirst();
                        imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    } else {
                        imagePath = uri.getPath();
                    }
                    break;
            }
            if (uri != null) {
                MyLogger.i(">>>>>>>>>>获取到的图片路径1：" + imagePath);
                //图片过大解决方法
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 32;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

                if (type.equals("wechat_proceeds_qrcode")) {//微信收款二维码
                    imageView1.setImageBitmap(bitmap);
                    imageView1.setVisibility(View.VISIBLE);
                    linearLayout1.setVisibility(View.GONE);
                } else {
                    //支付宝图片
                    imageView2.setImageBitmap(bitmap);
                    imageView2.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.GONE);
                }

                Uri uri1 = Uri.parse("");
                uri1 = Uri.fromFile(new File(imagePath));
                File file1 = new File(FileUtil.getPath(this, uri1));

//                listFiles.add(file1);
                listFileNames.add(type);
                File newFile = null;
                try {
                    newFile = new Compressor(this).compressToFile(file1);
                    listFiles.add(newFile);

                } catch (IOException e) {
                    e.printStackTrace();
                    myToast(getString(R.string.app_imgerr));
                }
            }
        }

    }

    private void showPopupWindow1(View v) {
        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(BankCardSettingActivity.this).inflate(
                R.layout.pop_list_1, null);
        final FixedPopupWindow popupWindow = new FixedPopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        contentView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = contentView.findViewById(R.id.pop_listView).getTop();
                int height1 = contentView.findViewById(R.id.pop_listView).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindow.dismiss();
                    }
                    if (y > height1) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        // 设置按钮的点击事件
        ListView pop_listView = (ListView) contentView.findViewById(R.id.pop_listView);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < banklist.size(); i++) {
            list.add(banklist.get(i).getTitle());
        }
        final Pop_ListAdapter adapter = new Pop_ListAdapter(BankCardSettingActivity.this, list);
        adapter.setSelectItem(i1);
        pop_listView.setAdapter(adapter);
        pop_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                i1 = i;
                adapter.setSelectItem(i);
                adapter.notifyDataSetChanged();

                bank_title = banklist.get(i).getTitle();
                textView.setText(banklist.get(i).getTitle());
                popupWindow.dismiss();

            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.transparent));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(v);
    }
}
