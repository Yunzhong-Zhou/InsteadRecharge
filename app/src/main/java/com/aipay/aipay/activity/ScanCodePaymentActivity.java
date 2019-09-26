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
import com.aipay.aipay.utils.MyLogger;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zyz on 2019/3/24.
 * 扫码支付
 */
public class ScanCodePaymentActivity extends BaseActivity {
    String id = "";
    ImageView imageView;
    TextView textView1;
    LinearLayout linearLayout;

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
        setContentView(R.layout.activity_scancodepayment);
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
        linearLayout = findViewByID_My(R.id.linearLayout);
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
        OkHttpClientManager.getAsyn(ScanCodePaymentActivity.this, URLs.RechargeDetail + string, new OkHttpClientManager.ResultCallback<RechargeDetailModel>() {
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

                /*//生成二维码
                Bitmap mBitmap = ZxingUtils.createQRCodeBitmap(response.getEwm_qrcode(), 480, 480);
                imageView.setImageBitmap(mBitmap);*/
                if (!response.getEwm_qrcode().equals(""))
                    Glide.with(ScanCodePaymentActivity.this)
                            .load(OkHttpClientManager.IMGHOST + response.getEwm_qrcode())
                            .centerCrop()
                            .into(imageView);//加载图片
                else
                    imageView.setImageResource(R.mipmap.headimg);

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
            case R.id.textView:
                //保存二维码
                textView.setClickable(false);

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        //这是新的线程，可在这儿处理耗时的操作，更新不了UI界面的操作的
                        file = printScreen(linearLayout, "ScanQRCode" + System.currentTimeMillis());
                    }
                };

                if (mThread == null) {
                    mThread = new Thread(runnable);
                    mThread.start();// 线程启动
                }
                break;

        }
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
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
                MediaStore.Images.Media.insertImage(ScanCodePaymentActivity.this.getContentResolver(), bitmap, file.toString(), null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri1 = Uri.fromFile(file);
                intent.setData(uri1);
                ScanCodePaymentActivity.this.sendBroadcast(intent);*/


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

                MyLogger.i(">>>>>>"+file);
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
