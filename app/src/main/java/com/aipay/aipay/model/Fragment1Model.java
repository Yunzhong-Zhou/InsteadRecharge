package com.aipay.aipay.model;

import java.io.Serializable;

/**
 * Created by zyz on 2019/7/31.
 */
public class Fragment1Model implements Serializable {
    /**
     * member_bank_card_account : 15565545558555
     * member_bank_card_proceeds_name : 阿斯顿马丁
     * member_bank_title : 招商银行
     * withdrawal_service_charge : 5
     * min_withdrawal_money : 10
     * max_withdrawal_money : 10000
     * hk : 6fd457baeffb9d320e8671b581c16af0
     */

    private String member_bank_card_account;
    private String member_bank_card_proceeds_name;
    private String member_bank_title;
    private String withdrawal_service_charge;
    private String min_withdrawal_money;
    private String max_withdrawal_money;
    private String hk;
    private String cho_price;

    public String getCho_price() {
        return cho_price;
    }

    public void setCho_price(String cho_price) {
        this.cho_price = cho_price;
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

    public String getWithdrawal_service_charge() {
        return withdrawal_service_charge;
    }

    public void setWithdrawal_service_charge(String withdrawal_service_charge) {
        this.withdrawal_service_charge = withdrawal_service_charge;
    }

    public String getMin_withdrawal_money() {
        return min_withdrawal_money;
    }

    public void setMin_withdrawal_money(String min_withdrawal_money) {
        this.min_withdrawal_money = min_withdrawal_money;
    }

    public String getMax_withdrawal_money() {
        return max_withdrawal_money;
    }

    public void setMax_withdrawal_money(String max_withdrawal_money) {
        this.max_withdrawal_money = max_withdrawal_money;
    }

    public String getHk() {
        return hk;
    }

    public void setHk(String hk) {
        this.hk = hk;
    }
}
