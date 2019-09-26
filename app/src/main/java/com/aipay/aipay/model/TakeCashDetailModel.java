package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/6/2.
 */
public class TakeCashDetailModel implements Serializable {
    /**
     * id : 8bbba13b83001cf8a4df2223
     * sn : WI1565082096579910
     * behalf_mobile : 18306043086
     * member_bank_card_account : 15565545558555
     * member_bank_card_proceeds_name : 阿斯顿马丁
     * member_bank_title : 招商银行
     * member_bank_address : 海南省三亚市支行
     * service_charge_money : 5
     * input_money : 100.00
     * digital_money : 14.29
     * money : 100
     * status : 1
     * status_rejected_cause :
     * created_at : 2019-08-06 17:01:36
     * status_title : 待审核
     */

    private String id;
    private String sn;
    private String behalf_mobile;
    private String member_bank_card_account;
    private String member_bank_card_proceeds_name;
    private String member_bank_title;
    private String member_bank_address;
    private String service_charge_money;
    private String input_money;
    private String money;
    private int status;
    private String status_rejected_cause;
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

    public String getMember_bank_card_account() {
        return member_bank_card_account;
    }

    public void setMember_bank_card_account(String member_bank_card_account) {
        this.member_bank_card_account = member_bank_card_account;
    }

    public String getMember_bank_card_proceeds_name() {
        return member_bank_card_proceeds_name;
    }

    public void setMember_bank_card_proceeds_name(String member_bank_card_proceeds_name) {
        this.member_bank_card_proceeds_name = member_bank_card_proceeds_name;
    }

    public String getMember_bank_title() {
        return member_bank_title;
    }

    public void setMember_bank_title(String member_bank_title) {
        this.member_bank_title = member_bank_title;
    }

    public String getMember_bank_address() {
        return member_bank_address;
    }

    public void setMember_bank_address(String member_bank_address) {
        this.member_bank_address = member_bank_address;
    }

    public String getService_charge_money() {
        return service_charge_money;
    }

    public void setService_charge_money(String service_charge_money) {
        this.service_charge_money = service_charge_money;
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

    public String getStatus_rejected_cause() {
        return status_rejected_cause;
    }

    public void setStatus_rejected_cause(String status_rejected_cause) {
        this.status_rejected_cause = status_rejected_cause;
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
