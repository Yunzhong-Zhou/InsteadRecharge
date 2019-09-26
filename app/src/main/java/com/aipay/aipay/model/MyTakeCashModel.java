package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/6/2.
 */
public class MyTakeCashModel implements Serializable {

    /**
     * id : 8bbba13b83001cf8a4df2223
     * sn : WI1565082096579910
     * behalf_mobile : 18306043086
     * input_money : 100.00
     * digital_money : 14.29
     * money : 100
     * status : 1
     * created_at : 2019-08-06 17:01:36
     * status_title : 待审核
     */

    private String id;
    private String sn;
    private String behalf_mobile;
    private String input_money;
    private String money;
    private int status;
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
