package com.aipay.aipay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipay.aipay.R;
import com.aipay.aipay.activity.BankCardSettingActivity;
import com.aipay.aipay.activity.LoginActivity;
import com.aipay.aipay.activity.MainActivity;
import com.aipay.aipay.activity.MyRechargeActivity;
import com.aipay.aipay.activity.MyTakeCashActivity;
import com.aipay.aipay.base.BaseFragment;
import com.aipay.aipay.model.Fragment3Model;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.CommonUtil;
import com.aipay.aipay.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

import static com.aipay.aipay.net.OkHttpClientManager.IMGHOST;


/**
 * Created by fafukeji01 on 2016/1/6.
 */
public class Fragment3 extends BaseFragment {
    ImageView imageView1;
    TextView textView1, textView2, textView3, textView4, textView5,textView6;
    LinearLayout linearLayout1, linearLayout2, linearLayout3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 2) {
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (MainActivity.item == 2) {
            requestServer();
        }
    }

    @Override
    protected void initView(View view) {
        findViewByID_My(R.id.headview).setPadding(0, (int) CommonUtil.getStatusBarHeight(getActivity()), 0, 0);
        setSpringViewMore(false);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requestCenter("?token=" + localUserInfo.getToken());
            }

            @Override
            public void onLoadmore() {

            }
        });
        imageView1 = findViewByID_My(R.id.imageView1);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView6.setOnClickListener(this);

        textView1.setText(localUserInfo.getPhonenumber());
        if (!localUserInfo.getUserImage().equals(""))
            Glide.with(getActivity())
                    .load(IMGHOST + localUserInfo.getUserImage())
                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                    .into(imageView1);//加载图片
        else
            imageView1.setImageResource(R.mipmap.headimg);

        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        requestServer();
    }

    private void requestCenter(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Center + string, new OkHttpClientManager.ResultCallback<Fragment3Model>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(Fragment3Model response) {
                MyLogger.i(">>>>>>>>>我的" + response);
                //昵称
                textView1.setText(response.getMobile());
                //头像
                localUserInfo.setUserImage(response.getHead());
                if (!response.getHead().equals(""))
                    Glide.with(getActivity()).load(IMGHOST + response.getHead())
                            .centerCrop()
//                            .placeholder(R.mipmap.headimg)//加载站位图
//                            .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片
                else
                    imageView1.setImageResource(R.mipmap.headimg);

                if (!response.getBank_card_account().equals("")){
                    textView3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.GONE);

                    textView2.setText(response.getBank_title());
                    textView3.setText(response.getBank_card_proceeds_name());
                    textView4.setText(response.getBank_card_account());
                }else {
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                    textView5.setVisibility(View.VISIBLE);
                }

                hideProgress();
            }
        });
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();

        showProgress(true, getString(R.string.app_loading));
        requestCenter("?token=" + localUserInfo.getToken());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout1:
                //银行卡
                CommonUtil.gotoActivity(getActivity(), BankCardSettingActivity.class);
                break;
            case R.id.linearLayout2:
                //代充记录
                CommonUtil.gotoActivity(getActivity(), MyRechargeActivity.class);
                break;
            case R.id.linearLayout3:
                //兑现记录
                CommonUtil.gotoActivity(getActivity(), MyTakeCashActivity.class);
                break;
            case R.id.textView6:
                showToast(getString(R.string.myprofile_h11),
                        getString(R.string.app_confirm),
                        getString(R.string.app_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                localUserInfo.setUserId("");
                                localUserInfo.setToken("");
                                localUserInfo.setPhoneNumber("");
                                localUserInfo.setNickname("");
                                localUserInfo.setWalletaddr("");
                                localUserInfo.setEmail("");
                                localUserInfo.setUserImage("");
                                CommonUtil.gotoActivityWithFinishOtherAll(getActivity(), LoginActivity.class, true);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                break;
        }
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
