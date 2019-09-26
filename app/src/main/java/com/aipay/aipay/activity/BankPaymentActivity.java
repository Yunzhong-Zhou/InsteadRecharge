package com.aipay.aipay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aipay.aipay.R;
import com.aipay.aipay.base.BaseActivity;
import com.aipay.aipay.model.RechargeDetailModel;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

/**
 * Created by zyz on 2019/3/24.
 * 银联支付
 */
public class BankPaymentActivity extends BaseActivity {
    String id = "";
    ImageView imageView;
    TextView textView1, textView2, textView3, textView4;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankpayment);
//        mImmersionBar.reset().init();
//        findViewById(R.id.linearLayout).setPadding(0, (int) CommonUtil.getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void initView() {
        setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                String string = "?id=" + id
                        + "&token=" + localUserInfo.getToken();
                RequestRechargeDetail(string);//充值详情

            }

            @Override
            public void onLoadmore() {

            }
        });
        imageView = findViewByID_My(R.id.imageView);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        linearLayout = findViewByID_My(R.id.linearLayout);
    }

    @Override
    protected void initData() {
        showProgress(true, getString(R.string.app_loading2));
        id = getIntent().getStringExtra("id");
        String string = "?id=" + id
                + "&token=" + localUserInfo.getToken();
        RequestRechargeDetail(string);//充值详情

    }

    //充值详情
    private void RequestRechargeDetail(String string) {
        OkHttpClientManager.getAsyn(BankPaymentActivity.this, URLs.RechargeDetail + string, new OkHttpClientManager.ResultCallback<RechargeDetailModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(RechargeDetailModel response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>充值详情" + response);
                textView1.setText(response.getTop_up().getMoney());//充值金额
                textView2.setText(response.getBank_title());//银行
                textView3.setText(response.getBank_card_account());//卡号
                textView4.setText(response.getBank_card_proceeds_name());//账户

                showToast("请按生成的金额充值，否则将充值失败（充值时必须输入后两位小数）。");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;

        }
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }

}
