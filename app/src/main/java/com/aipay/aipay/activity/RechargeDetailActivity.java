package com.aipay.aipay.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipay.aipay.R;
import com.aipay.aipay.base.BaseActivity;
import com.aipay.aipay.model.RechargeDetailModel;
import com.aipay.aipay.net.OkHttpClientManager;
import com.aipay.aipay.net.URLs;
import com.aipay.aipay.utils.CommonUtil;
import com.aipay.aipay.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.aipay.aipay.net.OkHttpClientManager.HOST;


/**
 * Created by zyz on 2017/9/4.
 * 充值详情
 */

public class RechargeDetailActivity extends BaseActivity {
    String id = "";
    RechargeDetailModel model;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7,
            textView3_2, textView3_3, textView3_4;

    LinearLayout linearLayout_1, linearLayout_2, linearLayout_3;

    ImageView imageView;

    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;

    TextView textView;
    File file = null;
    private Thread mThread;
    private static final int MSG_SUCCESS = 0;// 获取成功的标识
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {// 此方法在ui线程运行
            switch (msg.what) {
                case MSG_SUCCESS:
                    showToast("二维码已经保存到相册");
                    textView.setClickable(true);
                    /*if (file != null) {
                        showToast("二维码已经保存到相册");
                        textView.setClickable(true);
                    } else {
                        showToast("图片保存失败", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textView.setClickable(true);
                                dialog.dismiss();
                                initData();
                            }
                        });
                    }*/
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargedetail);
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
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);


        linearLayout_1 = findViewByID_My(R.id.linearLayout_1);
        linearLayout_2 = findViewByID_My(R.id.linearLayout_2);
        linearLayout_3 = findViewByID_My(R.id.linearLayout_3);
        textView3_2 = findViewByID_My(R.id.textView3_2);
        textView3_3 = findViewByID_My(R.id.textView3_3);
        textView3_4 = findViewByID_My(R.id.textView3_4);

        imageView = findViewByID_My(R.id.imageView);

        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);
        linearLayout4 = findViewByID_My(R.id.linearLayout4);

        textView = findViewByID_My(R.id.textView);
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
        OkHttpClientManager.getAsyn(RechargeDetailActivity.this, URLs.RechargeDetail + string, new OkHttpClientManager.ResultCallback<RechargeDetailModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                onHttpResult();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(RechargeDetailModel response) {
                onHttpResult();
                MyLogger.i(">>>>>>>>>充值详情" + response);
                model = response;
                textView1.setText("¥" + response.getTop_up().getInput_money() + "");//代充金额
//                textView2.setText(response.getTop_up().getPay_type() + "");//代充平台
                textView3.setText(response.getTop_up().getBehalf_mobile() + "");//代充账号
                textView4.setText(response.getTop_up().getMoney() + "");//实到CHO
                textView5.setText(response.getTop_up().getCreated_at() + "");//代充时间
                textView6.setText(response.getTop_up().getSn() + "");//流水号
                textView7.setText(response.getTop_up().getStatus_title() + "");//状态

                if (response.getTop_up().getStatus() == 1) {
                    switch (response.getTop_up().getPay_detail_type()) {
                        case 1:
                        case 2:
                            //显示支付布局
                            linearLayout_1.setVisibility(View.VISIBLE);
                            linearLayout_2.setVisibility(View.GONE);
                            linearLayout_3.setVisibility(View.GONE);
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
                        /*if (!response.getEwm().equals("")){
                            linearLayout3.setVisibility(View.VISIBLE);
                        }else {
                            linearLayout3.setVisibility(View.GONE);
                        }
                        if (!response.getUnionpay().equals("")) {
                            linearLayout4.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout4.setVisibility(View.GONE);
                        }*/
                            break;
                        case 3:
                            //显示银联支付
                            showToast("请按生成的金额充值，否则将充值失败（充值时必须输入后两位小数）。");
                            linearLayout_1.setVisibility(View.GONE);
                            linearLayout_2.setVisibility(View.GONE);
                            linearLayout_3.setVisibility(View.VISIBLE);
                            textView3_2.setText(response.getBank_title());//银行
                            textView3_3.setText(response.getBank_card_account());//卡号
                            textView3_4.setText(response.getBank_card_proceeds_name());//账户
                            break;
                        case 4:
                            //显示二维码布局
                            showToast("请按生成的金额充值，否则将充值失败（充值时必须输入后两位小数）。");
                            linearLayout_1.setVisibility(View.GONE);
                            linearLayout_2.setVisibility(View.VISIBLE);
                            linearLayout_3.setVisibility(View.GONE);
                        /*//生成二维码
                        Bitmap mBitmap = ZxingUtils.createQRCodeBitmap(response.getEwm_qrcode(), 480, 480);
                        imageView.setImageBitmap(mBitmap);*/
                            if (!response.getEwm_qrcode().equals(""))
                                Glide.with(RechargeDetailActivity.this).load(OkHttpClientManager.IMGHOST + response.getEwm_qrcode())
                                        .centerCrop().into(imageView);//加载图片
                            else
                                imageView.setImageResource(R.mipmap.headimg);
                            break;
                    }

                } else {
                    //不显示下面布局
                    linearLayout_1.setVisibility(View.GONE);
                    linearLayout_2.setVisibility(View.GONE);
                    linearLayout_3.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.textView:
                //保存二维码
                textView.setClickable(false);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        //这是新的线程，可在这儿处理耗时的操作，更新不了UI界面的操作的
                        file = printScreen(imageView, "ScanQRCode" + System.currentTimeMillis());
                    }
                };

                if (mThread == null) {
                    mThread = new Thread(runnable);
                    mThread.start();// 线程启动
                }
                break;
        }
    }

    private void toPay(String pay_type) {
        //请求支付网页
        Bundle bundle = new Bundle();
        bundle.putString("url", HOST + "/pay?top_up_id=" + model.getTop_up().getId() + "&pay_type=" + pay_type);
        CommonUtil.gotoActivityWithData(RechargeDetailActivity.this, WebContentActivity.class, bundle, false);
    }


    @Override
    protected void updateView() {
        titleView.setTitle(getString(R.string.recharge_h10));
    }

    public void onHttpResult() {
        hideProgress();
        // 刷新完成
        springView.onFinishFreshAndLoad();
    }

    /**
     * 截取图片存到本地
     */
    public File printScreen(View view, String picName) {
        //图片地址
//        String imgPath = FileUtil.getImageDownloadDir(MyPosterActivity.this) + picName + ".png";
//        String imgPath = Environment.getExternalStorageDirectory() + "/" + picName + ".png";//文件根目录
        String imgPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;//相册
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        File file = null;
        if (bitmap != null) {
            try {
                file = new File(imgPath, picName + ".png");
//                file = new File(imgPath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

                /*Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

                //通知相册更新
                MediaStore.Images.Media.insertImage(RechargeDetailActivity.this.getContentResolver(), bitmap, file.toString(), null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri1 = Uri.fromFile(file);
                intent.setData(uri1);
                RechargeDetailActivity.this.sendBroadcast(intent);*/


                /*//把文件插入到系统图库(内部存储/Pictures)
                try {
                    MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
                // 通知图库更新
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                    mediaScanIntent.setData(uri);
                                    sendBroadcast(mediaScanIntent);
                                }
                            });
                } else {
                    String relationDir = file.getParent();
                    File file1 = new File(relationDir);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file1.getAbsoluteFile())));
                }

                mHandler.obtainMessage(MSG_SUCCESS)// 获取信息
                        .sendToTarget(); //发送信息

                MyLogger.i(">>>>>>" + file);
                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }
}
