package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/8/4.
 */
public class Fragment2Model implements Serializable {
    /**
     * wechat : 84e3dd01b79e558168eea95a2b62654e_1
     * alipay : 84e3dd01b79e558168eea95a2b62654e_2
     * unionpay : 84e3dd01b79e558168eea95a2b62654e_3
     * ewm : 84e3dd01b79e558168eea95a2b62654e_4
     */

    private String wechat;
    private String alipay;
    private String unionpay;
    private String ewm;
    private String cho_price;

    public String getCho_price() {
        return cho_price;
    }

    public void setCho_price(String cho_price) {
        this.cho_price = cho_price;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(String unionpay) {
        this.unionpay = unionpay;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }
}
