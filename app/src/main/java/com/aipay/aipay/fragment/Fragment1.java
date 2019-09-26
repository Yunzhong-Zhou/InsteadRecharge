package com.aipay.aipay.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aipay.aipay.R;
import com.aipay.aipay.activity.BankCardSettingActivity;
import com.aipay.aipay.activity.MainActivity;
import com.aipay.aipay.activity.TakeCashDetailActivity;
import com.aipay.aipay.adapter.Pop_ListAdapter;
import com.aipay.aipay.base.BaseFragment;
import com.aipay.aipay.model.CreateTakeCashModel;
import com.aipay.aipay.model.Fragment1Model;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.CommonUtil;
import com.aipay.aipay.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 兑换
 */

public class Fragment1 extends BaseFragment {
    Fragment1Model model;
    int i1 = 0;
    LinearLayout linearLayout1, linearLayout2;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
    EditText editText1, editText2;
    String behalf_mobile = "", input_money = "0", behalf_type = "1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 0) {
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (MainActivity.item == 0) {
            requestServer();
        }
    }

    @Override
    protected void initView(View view) {
        findViewByID_My(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(getActivity()), 0, 0);
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
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);
        textView8 = findViewByID_My(R.id.textView8);
        textView4.setOnClickListener(this);
        textView8.setOnClickListener(this);
        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editText2.getText().toString().trim().equals("") && !model.getWithdrawal_service_charge().equals("")) {
                    String input_money = editText2.getText().toString().trim();
                    MyLogger.i(">>>>>输入币数>>>>>" + input_money);

                    //可得金额 = 输入币数 * (100 - withdrawal_service_charge） / 100 * cho_price
                    double real_money = Double.valueOf(input_money) * (100 - Double.valueOf(model.getWithdrawal_service_charge())) / 100 * Double.valueOf(model.getCho_price());
                    MyLogger.i(">>>>>实际到账>>>>>" + real_money);

                    textView5.setText("¥" + String.format("%.2f", real_money));
                } else {
                    textView1.setText("¥0");
                }

            }
        });

    }

    @Override
    protected void initData() {
//        requestServer();

    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Fragment1 + string, new OkHttpClientManager.ResultCallback<Fragment1Model>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(Fragment1Model response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>兑现" + response);
                model = response;
                if (!response.getMember_bank_card_proceeds_name().equals("")) {
                    linearLayout1.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.GONE);
                    textView1.setText(response.getMember_bank_title());
                    textView2.setText(response.getMember_bank_card_proceeds_name());
                    textView3.setText(response.getMember_bank_card_account());
                } else {
                    linearLayout1.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                }
//                textView5.setText("¥"+response.);//可用
                textView6.setText("手续费：" + response.getWithdrawal_service_charge() + "%");//手续费

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout2:
                //添加银行卡
                CommonUtil.gotoActivity(getActivity(), BankCardSettingActivity.class);
                break;
            case R.id.textView4:
                //选择平台
                showPopupWindow(textView4);
                break;
            case R.id.textView8:
                //提交
                if (match()) {
                    this.showProgress(true, "正在提交兑现...");
                    Map<String, String> params = new HashMap<>();
//                    params.put("pay_type", pay_type + "");
                    params.put("behalf_type", behalf_type + "");
                    params.put("input_money", input_money + "");
                    params.put("behalf_mobile", behalf_mobile + "");
                    params.put("token", localUserInfo.getToken());
                    params.put("hk", model.getHk());
                    requestTakeCash(params);
                }
                break;
        }
    }

    private void requestTakeCash(Map<String, String> params) {
        OkHttpClientManager.postAsyn(getActivity(), URLs.Fragment1, params, new OkHttpClientManager.ResultCallback<CreateTakeCashModel>() {
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
                requestServer();
            }

            @Override
            public void onResponse(CreateTakeCashModel response) {
                MyLogger.i(">>>>>>>>>充值提交1>>>>" + response);
                hideProgress();
                editText2.setText("");
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", response.getId());
                CommonUtil.gotoActivityWithData(getActivity(), TakeCashDetailActivity.class, bundle3, false);
            }
        }, true);
    }

    private boolean match() {

        behalf_mobile = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(behalf_mobile)) {
            myToast("请输入平台账号");
            return false;
        }
        /*String behalf_mobile2 = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(behalf_mobile2)) {
            myToast("请再次输入平台账号");
            return false;
        }
        if (!behalf_mobile.equals(behalf_mobile2)) {
            myToast("两次输入的账号不一致");
            return false;
        }*/
        input_money = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(input_money)) {
            myToast("请输入兑现个数");
            return false;
        }
        if (Double.valueOf(input_money) <= 0) {
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

    private void showPopupWindow(View v) {
        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_list1, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
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
        final List<String> list1 = new ArrayList<String>();
        list1.add(getString(R.string.app_type_CHO));
        list1.add(getString(R.string.app_type_GRE));
        list1.add(getString(R.string.app_type_QDS));
        final Pop_ListAdapter adapter = new Pop_ListAdapter(getActivity(), list1);
        adapter.setSelectItem(i1);
        pop_listView.setAdapter(adapter);
        pop_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectItem(i);
                adapter.notifyDataSetChanged();
                i1 = i;
                textView4.setText(list1.get(i));
                behalf_type = i + 1 + "";
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

        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.translucent));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(v);
    }
}
