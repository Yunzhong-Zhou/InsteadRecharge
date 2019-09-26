package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/6/2.
 */
public class MyRechargeModel implements Serializable {
    /**
     * id : c738f4b14d9ee005559ab51088ba1f67
     * sn : TU1565335263579956
     * behalf_mobile : 18680817626
     * pay_type : 2
     * input_money : 14.32
     * money : 100.23
     * status : 1
     * pay_detail_type : 4
     * created_at : 2019-08-09 15:21:03
     * status_title : 待审核
     */

    private String id;
    private String sn;
    private String behalf_mobile;
    private int pay_type;
    private String input_money;
    private String money;
    private int status;
    private int pay_detail_type;
    private String created_at;
    private String status_title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBehalf_mobile() {
        return behalf_mobile;
    }

    public void setBehalf_mobile(String behalf_mobile) {
        this.behalf_mobile = behalf_mobile;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getInput_money() {
        return input_money;
    }

    public void setInput_money(String input_money) {
        this.input_money = input_money;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPay_detail_type() {
        return pay_detail_type;
    }

    public void setPay_detail_type(int pay_detail_type) {
        this.pay_detail_type = pay_detail_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }
}
