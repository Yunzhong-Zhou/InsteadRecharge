package com.aipay.aipay.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aipay.aipay.R;
import com.aipay.aipay.activity.BankPaymentActivity;
import com.aipay.aipay.activity.MainActivity;
import com.aipay.aipay.activity.ScanCodePaymentActivity;
import com.aipay.aipay.activity.WebContentActivity;
import com.aipay.aipay.adapter.BannerAdapter;
import com.aipay.aipay.adapter.CardTransformer;
import com.aipay.aipay.base.BaseFragment;
import com.aipay.aipay.model.CreateRechargeModel;
import com.aipay.aipay.model.Fragment2Model;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.CommonUtil;
import com.aipay.aipay.utils.MyLogger;
import com.aipay.aipay.view.ViewPagerIndicator;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import static com.aipay.aipay.net.OkHttpClientManager.HOST;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 代充
 */
public class Fragment2 extends BaseFragment {
    int type = 0;
    ViewPager mViewpager;
    ViewPagerIndicator mIndicatorLine;
    private BannerAdapter mBannerAdapter;

    Fragment2Model model;
    EditText editText1, editText2, editText3;
    TextView textView1;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;

    String behalf_mobile = "", money = "0", behalf_type = "1";

    ImageView imageView1,imageView2,imageView3,imageView1_1,imageView2_1,imageView3_1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        StatusBarUtil.setTransparent(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (MainActivity.item == 1) {
            requestServer();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 1) {
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (MainActivity.item == 1) {
            requestServer();
        }
    }

    @Override
    protected void initView(View view) {
        findViewByID_My(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(getActivity()), 0, 0);

        mViewpager = findViewByID_My(R.id.mViewpager);
        mIndicatorLine = findViewByID_My(R.id.mIndicatorLine);

        mBannerAdapter = new BannerAdapter(getActivity(), mViewpager);
        mViewpager.setAdapter(mBannerAdapter);
        mViewpager.setOffscreenPageLimit(3);//预加载2个
        mViewpager.setPageMargin(20);//设置viewpage之间的间距
        mViewpager.setClipChildren(false);
        mViewpager.setPageTransformer(true, new CardTransformer());
        mIndicatorLine.setViewPager(mViewpager, 3);

        /*//每次滑动只能滑动一页
        mViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x= 0;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //得到当前点击时，距离屏幕的X坐标
                        x = (int)event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //如果移动的X轴距离大于某个值，这个值根据需求进行改动
                        if(Math.abs(x - event.getRawX()) > 120){
                            //设置事件为MotionEvent.ACTION_UP强行结束该事件
                            event.setAction(MotionEvent.ACTION_UP);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //事件结束，充值x为0
                        x = 0;
                        break;
                }
                return false;
            }
        });*/
        mBannerAdapter.setItemClickListener(new BannerAdapter.ItemClickListener() {

            @Override
            public void onItemClick(int index) {
                MyLogger.i("点击了图片" + index);
            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                MyLogger.i("选择了图片" + i);
                switch (i) {
                    case 0:
                        behalf_type = "2";
                        break;
                    case 1:
                        behalf_type = "1";
                        break;
                    case 2:
                        behalf_type = "3";
                        break;
                }

                /*mBannerAdapter.onPageSelected(1);
                mBannerAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewpager.setCurrentItem(1);


        imageView1 = findViewByID_My(R.id.imageView1);
        imageView2 = findViewByID_My(R.id.imageView2);
        imageView3 = findViewByID_My(R.id.imageView3);
        imageView1_1 = findViewByID_My(R.id.imageView1_1);
        imageView2_1 = findViewByID_My(R.id.imageView2_1);
        imageView3_1 = findViewByID_My(R.id.imageView3_1);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);

        setSpringViewMore(false);//需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                String string = "?token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {

            }
        });

        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editText3.getText().toString().trim().equals("") && !model.getCho_price().equals("")) {
                    String input_money = editText3.getText().toString().trim();
                    MyLogger.i(">>>>>输入币数>>>>>" + input_money);

                    //实际到账  =  eth个数  *  eth_price  /  cho_price
                    double real_money = Double.valueOf(input_money) / Double.valueOf(model.getCho_price());
                    MyLogger.i(">>>>>实际到账>>>>>" + real_money);

                    textView1.setText("" + String.format("%.2f", real_money));
                } else {
                    textView1.setText("0");
                }

            }
        });

        textView1 = findViewByID_My(R.id.textView1);
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);
        linearLayout4 = findViewByID_My(R.id.linearLayout4);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);

    }

    @Override
    protected void initData() {
//        requestServer();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView1:
                behalf_type = "2";
                imageView1.setScaleX(1f);
                imageView1.setScaleY(1f);
                imageView2.setScaleX(0.8f);
                imageView2.setScaleY(0.8f);
                imageView3.setScaleX(0.8f);
                imageView3.setScaleY(0.8f);

                imageView1.setImageResource(R.mipmap.bg_fm2_item_1_1);
                imageView2.setImageResource(R.mipmap.bg_fm2_item_2_0);
                imageView3.setImageResource(R.mipmap.bg_fm2_item_3_0);

                imageView1_1.setImageResource(R.mipmap.bg_xuanzhong);
                imageView2_1.setImageResource(R.mipmap.bg_weixuan);
                imageView3_1.setImageResource(R.mipmap.bg_weixuan);

                break;
            case R.id.imageView2:
                behalf_type = "1";

                imageView1.setScaleX(0.8f);
                imageView1.setScaleY(0.8f);
                imageView2.setScaleX(1f);
                imageView2.setScaleY(1f);
                imageView3.setScaleX(0.8f);
                imageView3.setScaleY(0.8f);

                imageView1.setImageResource(R.mipmap.bg_fm2_item_1_0);
                imageView2.setImageResource(R.mipmap.bg_fm2_item_2_1);
                imageView3.setImageResource(R.mipmap.bg_fm2_item_3_0);

                imageView1_1.setImageResource(R.mipmap.bg_weixuan);
                imageView2_1.setImageResource(R.mipmap.bg_xuanzhong);
                imageView3_1.setImageResource(R.mipmap.bg_weixuan);
                break;
            case R.id.imageView3:
                behalf_type = "3";

                imageView1.setScaleX(0.8f);
                imageView1.setScaleY(0.8f);
                imageView2.setScaleX(0.8f);
                imageView2.setScaleY(0.8f);
                imageView3.setScaleX(1f);
                imageView3.setScaleY(1f);

                imageView1.setImageResource(R.mipmap.bg_fm2_item_1_0);
                imageView2.setImageResource(R.mipmap.bg_fm2_item_2_0);
                imageView3.setImageResource(R.mipmap.bg_fm2_item_3_1);

                imageView1_1.setImageResource(R.mipmap.bg_weixuan);
                imageView2_1.setImageResource(R.mipmap.bg_weixuan);
                imageView3_1.setImageResource(R.mipmap.bg_xuanzhong);
                break;
            case R.id.linearLayout1:
                //支付宝
                toPay(model.getAlipay());
                break;
            case R.id.linearLayout2:
                //微信
                toPay(model.getWechat());
                break;
            case R.id.linearLayout3:
                //扫码
                toPay(model.getEwm());

                break;
            case R.id.linearLayout4:
                //银联
                toPay(model.getUnionpay());
                break;
        }
    }


    private void toPay(String pay_type) {
        if (match()) {
            //去充值
            MyLogger.i(">>>>>>>>" + behalf_type);
            this.showProgress(true, "正在前往充值...");
            Map<String, String> params = new HashMap<>();
            params.put("pay_type", pay_type + "");
            params.put("behalf_type", behalf_type + "");
            params.put("input_money", money + "");
            params.put("behalf_mobile", behalf_mobile + "");
            params.put("token", localUserInfo.getToken());
            requestPay(params, pay_type);
        }
    }

    private void requestPay(Map<String, String> params, final String pay_type) {
        OkHttpClientManager.postAsyn(getActivity(), URLs.Fragment2, params, new OkHttpClientManager.ResultCallback<CreateRechargeModel>() {
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
                                        CommonUtil.gotoActivity(RechargeActivity.this, SetTransactionPasswordActivity.class, false);
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
                                        CommonUtil.gotoActivity(RechargeActivity.this, BankCardSettingActivity.class, false);
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
            public void onResponse(CreateRechargeModel response) {
                MyLogger.i(">>>>>>>>>充值提交1>>>>" + response);
                hideProgress();
                editText3.setText("");
                switch (response.getPay_detail_type()) {
                    case 1:
                    case 2:
                        //请求支付网页
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("url", HOST + "/pay?top_up_id=" + response.getId() + "&pay_type=" + pay_type);
                        CommonUtil.gotoActivityWithData(getActivity(), WebContentActivity.class, bundle1, false);
                        break;
                    case 4:
                        /*myToast("充值提交成功");
                    Bundle bundle = new Bundle();
                    bundle.putString("RechargeDetail", response.getId());
                    CommonUtil.gotoActivityWithData(getActivity(), RechargeDetailActivity.class, bundle, false);*/
                        //跳转 扫码支付
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("id", response.getId());
                        CommonUtil.gotoActivityWithData(getActivity(), ScanCodePaymentActivity.class, bundle3, false);
                        break;
                    case 3:
                        /*myToast("充值提交成功");
                    Bundle bundle = new Bundle();
                    bundle.putString("RechargeDetail", response.getId());
                    CommonUtil.gotoActivityWithData(getActivity(), RechargeDetailActivity.class, bundle, false);*/
                        //跳转 银联支付
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("id", response.getId());
                        CommonUtil.gotoActivityWithData(getActivity(), BankPaymentActivity.class, bundle4, false);
                        break;
                }

            }
        }, true);
    }

    private boolean match() {

        behalf_mobile = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(behalf_mobile)) {
            myToast("请输入平台账号");
            return false;
        }
        String behalf_mobile2 = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(behalf_mobile2)) {
            myToast("请再次输入平台账号");
            return false;
        }
        if (!behalf_mobile.equals(behalf_mobile2)) {
            myToast("两次输入的账号不一致");
            return false;
        }
        money = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            myToast("请输入充值金额");
            return false;
        }
        if (Double.valueOf(money) <= 0) {
            myToast("充值金额不能小于0");
        }
        return true;
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));

        String string = "?token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Fragment2 + string, new OkHttpClientManager.ResultCallback<Fragment2Model>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(final Fragment2Model response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>代充" + response);
                model = response;
                if (!response.getAlipay().equals("")) {
                    linearLayout1.setVisibility(View.VISIBLE);
                } else {
                    linearLayout1.setVisibility(View.GONE);
                }

                if (!response.getWechat().equals("")) {
                    linearLayout2.setVisibility(View.VISIBLE);
                } else {
                    linearLayout2.setVisibility(View.GONE);
                }

                if (!response.getEwm().equals("")) {
                    linearLayout3.setVisibility(View.VISIBLE);
                } else {
                    linearLayout3.setVisibility(View.GONE);
                }

                if (!response.getUnionpay().equals("")) {
                    linearLayout4.setVisibility(View.VISIBLE);
                } else {
                    linearLayout4.setVisibility(View.GONE);
                }
            }
        });
    }

}
