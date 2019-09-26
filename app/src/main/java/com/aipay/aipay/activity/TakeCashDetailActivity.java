package com.aipay.aipay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aipay.aipay.R;
import com.aipay.aipay.base.BaseActivity;
import com.aipay.aipay.model.TakeCashDetailModel;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

/**
 * Created by zyz on 2019/5/27.
 * 提现详情
 */
public class TakeCashDetailActivity extends BaseActivity {
    String id = "";
    ProgressBar prograssBar;
    ImageView imageView1, imageView2;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18;
    LinearLayout linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takecashdetail);
    }

    @Override
    protected void initView() {
        //刷新
        setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                request("?token=" + localUserInfo.getToken()
                        + "&id=" + id);
            }

            @Override
            public void onLoadmore() {

            }
        });

        prograssBar = findViewByID_My(R.id.prograssBar);
        imageView1 = findViewByID_My(R.id.imageView1);
        imageView2 = findViewByID_My(R.id.imageView2);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);
        textView8 = findViewByID_My(R.id.textView8);
        textView9 = findViewByID_My(R.id.textView9);
        textView10 = findViewByID_My(R.id.textView10);
        textView11 = findViewByID_My(R.id.textView11);
        textView12 = findViewByID_My(R.id.textView12);
        textView13 = findViewByID_My(R.id.textView13);
        textView14 = findViewByID_My(R.id.textView14);
        textView15 = findViewByID_My(R.id.textView15);
        textView16 = findViewByID_My(R.id.textView16);
        textView17 = findViewByID_My(R.id.textView17);
        textView18 = findViewByID_My(R.id.textView18);

        linearLayout1 = findViewByID_My(R.id.linearLayout1);

    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        requestServer();
    }

    private void request(String string) {
        OkHttpClientManager.getAsyn(TakeCashDetailActivity.this, URLs.TakeCashDetail + string, new OkHttpClientManager.ResultCallback<TakeCashDetailModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(TakeCashDetailModel response) {
                MyLogger.i(">>>>>>>>>提现详情" + response);
                hideProgress();
                if (response!=null){
                    textView2.setText("¥"+response.getMoney());//提现个数
//                textView5.setText("" + response.getShow_created_at());//提现处理中时间
//                textView7.setText("" + response.getShow_updated_at());//提现完成时间

                    textView8.setText(response.getInput_money());//提现个数
                    textView9.setText("CHO");//提现平台
                    textView10.setText("" + response.getBehalf_mobile());//平台账号
                    textView11.setText(response.getService_charge_money() + "");//手续费

                    textView14.setText(response.getMember_bank_title());//兑现银行名称
                    textView17.setText(response.getMember_bank_card_account());//兑现银行卡号
                    textView18.setText(response.getMember_bank_card_proceeds_name());//兑现银行开户名

                    textView12.setText(response.getCreated_at());//提现时间
                    textView13.setText(response.getSn());//流水号
                    textView15.setText(response.getStatus_title());//状态

                    if (response.getStatus() == 2) {
                        //通过
                        textView7.setVisibility(View.VISIBLE);//完成时间
                        imageView2.setImageResource(R.mipmap.ic_rechargedetail3);//完成图标
                        prograssBar.setProgress(100);//完成进度
                        textView6.setText(getString(R.string.takecash_h19));//兑现成功文字
                        textView6.setTextColor(getResources().getColor(R.color.red));
                        textView16.setVisibility(View.GONE);//原因
                    } else if (response.getStatus() == 3) {
                        //未通过
                        textView7.setVisibility(View.VISIBLE);//完成时间
                        imageView2.setImageResource(R.mipmap.ic_rechargedetail4);//完成图标
                        prograssBar.setProgress(100);//完成进度
                        textView6.setText(getString(R.string.takecash_h27));//兑现成功文字
                        textView6.setTextColor(getResources().getColor(R.color.red));
                        textView16.setVisibility(View.VISIBLE);//原因
                        textView16.setText(getString(R.string.takecash_h28) + response.getStatus_rejected_cause());
                    } else {
                        //进行中
                        textView7.setVisibility(View.GONE);//完成时间
                        imageView2.setImageResource(R.mipmap.ic_rechargedetail2);//完成图标
                        prograssBar.setProgress(50);//完成进度
                        textView6.setText(getString(R.string.takecash_h19));//兑现成功文字
                        textView6.setTextColor(getResources().getColor(R.color.black2));
                        textView16.setVisibility(View.GONE);//原因
                    }
                }
            }
        });
    }

    @Override
    public void requestServer() {
        super.requestServer();
        showProgress(true, getString(R.string.app_loading2));
        request("?token=" + localUserInfo.getToken()
                + "&id=" + id);
    }

    @Override
    protected void updateView() {
        titleView.setTitle(getString(R.string.takecash_h25));
    }
}
